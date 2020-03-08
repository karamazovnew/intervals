package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.Timeline;

import java.util.*;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vladsoft.intervals.domain.PointType.START;

public class TimelineImpl<T extends Comparable<T>> implements Timeline<T> {

	private ConcurrentSkipListMap<T, Link<T>> links;
	private int intervalsNumber;

	public TimelineImpl() {
		links = new ConcurrentSkipListMap<>();
		intervalsNumber = 0;
	}

	@Override
	public synchronized void addInterval(Interval<T> interval) {
		T first = addStartPoint(interval.getStartPoint());
		T second = addEndPoint(interval.getEndPoint());
		traverseInheritances(links.subMap(first, false, second, false), interval);
		intervalsNumber++;
	}

	private T addStartPoint(Point<T> point) {
		T key = point.getValue();
		Link<T> found = links.get(key);
		if (found != null) {
			found.addInterval(point.getInterval());
		} else {
			Map.Entry<T, Link<T>> previous = links.lowerEntry(key);
			if (previous != null)
				links.put(key, makeLink(point, previous.getValue().getIntervals()));
			else
				links.put(key, makeLink(point, null));
		}
		return key;
	}

	private T addEndPoint(Point<T> point) {
		T key = point.getValue();
		if (links.get(key) == null) {
			links.put(key, makeLink(point, links.lowerEntry(key).getValue().getIntervals()));
		}
		return key;
	}

	private void traverseInheritances(ConcurrentNavigableMap<T, Link<T>> map, Interval<T> interval) {
		map.forEach((key, link) -> link.addInterval(interval));
	}

	@Override
	public Collection<Interval<T>> getIntervals(T point) {
		Link<T> link = links.get(point);
		if (link != null) {
			return link.getIntervals();
		} else {
			Map.Entry<T, Link<T>> entry = links.floorEntry(point);
			if (entry == null || (entry.equals(links.lastEntry())))
				return Collections.emptyList();
			else {
				return entry.getValue().getIntervals();
			}
		}
	}

	@Override
	public Collection<Interval<T>> getIntervals(T startPoint, T endPoint) {
		if (links.size() == 0)
			return Collections.emptyList();
		T firstKey = links.firstKey();
		T lastKey = links.lastKey();
		if (endPoint.compareTo(firstKey) <= 0)
			return Collections.emptyList();
		if (startPoint.compareTo(lastKey) >= 0)
			return Collections.emptyList();
		if (startPoint.compareTo(firstKey) < 0)
			startPoint = firstKey;
		if (endPoint.compareTo(lastKey) > 0)
			endPoint = lastKey;
		return links.subMap(links.floorKey(startPoint), endPoint).values().stream()
				.flatMap(l -> l.getIntervals().stream()).collect(Collectors.toSet());
	}

	@Override
	public int getMaxOverlaps(T startPoint, T endPoint) {
		if (links.size() == 0 || startPoint.compareTo(links.lastKey()) >= 0)
			return 0;
		AtomicInteger max = new AtomicInteger(0);
		Stream<Map.Entry<T, Link<T>>> stream;
		if (startPoint.compareTo(links.firstKey()) < 0)
			stream = links.entrySet().stream();
		else
			stream = links.tailMap(links.floorKey(startPoint)).entrySet().stream();
		stream.anyMatch(entry -> {
			if (endPoint.compareTo(entry.getKey()) <= 0)
				return true;
			max.set(Math.max(entry.getValue().getIntervals().size(), max.get()));
			return false;
		});
		return max.get();
	}

	@Override
	public Interval<T> getFirstGap(T startPoint, T endPoint) {
		if (links.size() == 0)
			return ImplFactory.makeInterval(startPoint, endPoint);
		T firstKey = links.firstKey();
		T lastKey = links.lastKey();
		T end = endPoint;
		if (endPoint.compareTo(firstKey) <= 0)
			return ImplFactory.makeInterval(startPoint, endPoint);
		if (startPoint.compareTo(lastKey) >= 0)
			return ImplFactory.makeInterval(startPoint, endPoint);
		if (startPoint.compareTo(firstKey) < 0)
			return ImplFactory.makeInterval(startPoint, firstKey);
		if (endPoint.compareTo(lastKey) > 0)
			endPoint = lastKey;
		AtomicReference<T> prev = new AtomicReference<>();
		ConcurrentNavigableMap<T, Link<T>> subMap = links.subMap(links.floorKey(startPoint), true, endPoint, true);
		Map.Entry<T, Link<T>> found = subMap.entrySet().stream().filter((entry) -> {
			if (entry.getValue().getIntervals().isEmpty()) {
				prev.set(entry.getKey());
				return false;
			} else return prev.get() != null;
		}).findFirst().orElse(null);
		if (found != null)
			if (startPoint.compareTo(prev.get()) >= 0)
				return ImplFactory.makeInterval(startPoint, found.getKey());
			else
				return ImplFactory.makeInterval(prev.get(), found.getKey());
		else if (end.compareTo(subMap.floorKey(endPoint)) > 0)
			return ImplFactory.makeInterval(subMap.floorKey(endPoint), end);
		return null;
	}

	@Override
	public List<Interval<T>> getGaps(T startPoint, T endPoint) {
		if (links.size() == 0)
			return Collections.singletonList(ImplFactory.makeInterval(startPoint, endPoint));
		T firstKey = links.firstKey();
		T lastKey = links.lastKey();
		if (endPoint.compareTo(firstKey) <= 0)
			return Collections.singletonList(ImplFactory.makeInterval(startPoint, endPoint));
		if (startPoint.compareTo(lastKey) >= 0)
			return Collections.singletonList(ImplFactory.makeInterval(startPoint, endPoint));
		List<Interval<T>> result = new ArrayList<>();
		ConcurrentNavigableMap<T, Link<T>> subMap;
		if (startPoint.compareTo(firstKey) < 0) {
			result.add(ImplFactory.makeInterval(startPoint, firstKey));
			startPoint = links.firstKey();
		}
		if (endPoint.compareTo(lastKey) > 0)
			subMap = links.tailMap(links.floorKey(startPoint));
		else
			subMap = links.subMap(links.floorKey(startPoint), true, endPoint, true);
		AtomicReference<T> prev = new AtomicReference<>();
		T finalStartPoint = startPoint;
		subMap.forEach((key, value) -> {
			if (value.getIntervals().isEmpty()) {
				prev.set(key);
			} else if (prev.get() != null)
				if (finalStartPoint.compareTo(prev.get()) <= 0)
					result.add(ImplFactory.makeInterval(prev.getAndSet(null), key));
				else {
					result.add(ImplFactory.makeInterval(finalStartPoint, key));
					prev.set(null);
				}
		});
		if (endPoint.compareTo(subMap.lastKey()) > 0)
			result.add(ImplFactory.makeInterval(subMap.lastKey(), endPoint));
		return result;
	}

	@Override
	public int getIntervalsNumber() {
		return intervalsNumber;
	}

	private Link<T> makeLink(Point<T> point, Collection<Interval<T>> inheritance) {
		if (point.getType().equals(START))
			return new Link<>(point.getInterval(), inheritance);
		else
			return new Link<>(inheritance == null ? null : inheritance.stream()
					.filter(i -> i.getEndPoint().getValue().compareTo(point.getValue()) != 0)
					.collect(Collectors.toList()));
	}

}
