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

public class TimelineImpl<T extends Comparable<T>> implements Timeline<T> {

	private ConcurrentSkipListMap<T, Link<T>> links;

	public TimelineImpl() {
		links = new ConcurrentSkipListMap<>();
	}

	@Override
	public void addInterval(Interval<T> interval) {
		Point<T> startPoint = interval.getStartPoint();
		Point<T> endPoint = interval.getEndPoint();
		if (startPoint.getValue().compareTo(endPoint.getValue()) == 0)
			addPoint(startPoint);
		else {
			T first = addPoint(startPoint);
			T second = addPoint(endPoint);
			traverseInheritances(links.subMap(first, false, second, false), startPoint);
		}
	}

	private T addPoint(Point<T> point) {
		T key = point.getValue();
		Link<T> found = links.get(key);
		if (found != null) {
			found.addAssociation(point);
		} else {
			Map.Entry<T, Link<T>> previous = links.lowerEntry(key);
			if (previous != null)
				links.put(key, makeLink(point, previous.getValue().getOngoing()));
			else
				links.put(key, makeLink(point, null));
		}
		return key;
	}

	private void traverseInheritances(ConcurrentNavigableMap<T, Link<T>> map, Point<T> association) {
		map.forEach((key, link) -> {
			link.addAssociation(association);
		});
	}

	@Override
	public Collection<Interval<T>> getIntervals(T point) {
		Link<T> link = links.get(point);
		if (link != null) {
			return link.getAllAssociations().map(Point::getInterval).collect(Collectors.toList());
		} else {
			Map.Entry<T, Link<T>> entry = links.floorEntry(point);
			if (entry == null || (entry.equals(links.lastEntry())))
				return Collections.emptyList();
			else {
				return entry.getValue().getOngoing().stream().map(Point::getInterval).collect(Collectors.toList());
			}
		}
	}

	@Override
	public Collection<Interval<T>> getIntervals(Point<T> point) {
		return getIntervals(point.getValue());
	}

	private Link<T> makeLink(Point<T> point, Collection<Point<T>> inheritance) {
		return new Link<>(point, inheritance);
	}

}
