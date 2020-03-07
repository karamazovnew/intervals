package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.Timeline;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

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
		Link<T> found = links.get(key);
		if (links.get(key) == null) {
			links.put(key, makeLink(point, links.lowerEntry(key).getValue().getIntervals()));
		}
		return key;
	}

	private void traverseInheritances(ConcurrentNavigableMap<T, Link<T>> map, Interval<T> interval) {
		map.forEach((key, link) -> {
			link.addInterval(interval);
		});
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
		if(links.size()==0)
			return Collections.emptyList();
		boolean startAdded=false;
		boolean endAdded=false;
		if (startPoint.compareTo(links.firstKey())<0 || startPoint.compareTo(links.lastKey()) >0) {
			links.put(startPoint, new Link<>(null));
			startAdded = true;
		}
		if (endPoint.compareTo(links.firstKey())<0 || endPoint.compareTo(links.lastKey()) >0) {
			links.put(endPoint, new Link<>(null));
			endAdded = true;
		}
		Collection<Interval<T>> result = links.subMap(links.floorKey(startPoint), endPoint)
				.values().stream().flatMap(l->l.getIntervals().stream()).collect(Collectors.toSet());
		if(startAdded)
			links.remove(startPoint);
		if(endAdded)
			links.remove(endPoint);
		return result;
	}

	@Override
	public int getMaxOverlapping(T startPoint, T endPoint) {
		if(links.size()==0)
			return 0;
		boolean startAdded=false;
		boolean endAdded=false;
		if (startPoint.compareTo(links.firstKey())<0 || startPoint.compareTo(links.lastKey()) >0) {
			links.put(startPoint, new Link<>(null));
			startAdded = true;
		}
		if (endPoint.compareTo(links.firstKey())<0 || endPoint.compareTo(links.lastKey()) >0) {
			links.put(endPoint, new Link<>(null));
			endAdded = true;
		}
		int result = links.subMap(links.floorKey(startPoint), endPoint)
				.values().stream().map(l->l.getIntervals().size()).max(Integer::compareTo).orElse(0);
		if(startAdded)
			links.remove(startPoint);
		if(endAdded)
			links.remove(endPoint);
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
