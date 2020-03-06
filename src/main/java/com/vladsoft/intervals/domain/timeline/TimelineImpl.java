package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;
import com.vladsoft.intervals.domain.Timeline;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

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
			traverseInheritances(links.subMap(first, false, second, false), startPoint.getInterval());
		}
	}

	private T addPoint(Point<T> point) {
		T key = point.getValue();
		Link<T> found = links.get(key);
		if (found != null) {
			found.addAssociation(point.getType(), point.getInterval());
		} else {
			Map.Entry<T, Link<T>> previous = links.lowerEntry(key);
			if (previous != null)
				links.put(key, makeLink(point, previous.getValue().getOngoing()));
			else
				links.put(key, makeLink(point, null));
		}
		return key;
	}

	private void traverseInheritances(ConcurrentNavigableMap<T, Link<T>> map, Interval<T> interval) {
		map.forEach((key, link) -> {
			link.addAssociation(PointType.START, interval);
		});
	}

	@Override
	public Collection<Interval<T>> getIntervals(T point) {
		Link<T> link = links.get(point);
		if (link != null) {
			return link.getAllIntervals();
		} else {
			Map.Entry<T, Link<T>> entry = links.floorEntry(point);
			if (entry == null || (entry.equals(links.lastEntry())))
				return Collections.emptyList();
			else {
				return entry.getValue().getOngoing();
			}
		}
	}

	private Link<T> makeLink(Point<T> point, Collection<Interval<T>> inheritance) {
		return new Link<>(point.getType(), point.getInterval(), inheritance);
	}

}
