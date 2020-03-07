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

	public TimelineImpl() {
		links = new ConcurrentSkipListMap<>();
	}

	@Override
	public synchronized void addInterval(Interval<T> interval) {
		T first = addStartPoint(interval.getStartPoint());
		T second = addEndPoint(interval.getEndPoint());
		traverseInheritances(links.subMap(first, false, second, false), interval);
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

	private Link<T> makeLink(Point<T> point, Collection<Interval<T>> inheritance) {
		if (point.getType().equals(START))
			return new Link<>(point.getInterval(), inheritance);
		else
			return new Link<>(inheritance == null ? null : inheritance.stream()
					.filter(i -> i.getEndPoint().getValue().compareTo(point.getValue()) != 0)
					.collect(Collectors.toList()));
	}

}
