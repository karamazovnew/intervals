package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.Timeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class TimelineImpl implements Timeline {

	private ConcurrentSkipListMap<Comparable<?>, Link> links;

	public TimelineImpl() {
		links = new ConcurrentSkipListMap<>();
	}

	@Override
	public void addInterval(Interval interval) {
		IntervalAssociation startAssociation = interval.getStartPoint();
		IntervalAssociation endAssociation = interval.getEndPoint();
		Point startPoint = startAssociation.getPoint();
		Point endPoint = endAssociation.getPoint();
		if (startPoint.compareTo(endPoint) == 0)
			addInstant(startAssociation, endAssociation);
	}

	@Override
	public Collection<Interval> getIntervals(Comparable<?> point) {
		return links.get(point).getAllAssociations().map(a -> a.getInterval()).collect(Collectors.toSet());
	}

	//for test only
	protected Collection<IntervalAssociation> getAssociations(Point point) {
		return links.get(point.getValue()).getAllAssociations().collect(Collectors.toList());
	}

	private void addInstant(IntervalAssociation startPoint, IntervalAssociation endPoint) {
		Comparable<?> key = startPoint.getPoint().getValue();
		Link found = links.get(key);
		if (found == null) {
			Map.Entry<Comparable<?>, Link> inherited = links.lowerEntry(key);
			links.put(key, makeLink(Arrays.asList(startPoint, endPoint)
					, inherited == null ? null : inherited.getValue()));
		} else {
			found.addAssociations(Arrays.asList(startPoint, endPoint));
		}
	}

	private Link makeLink(Collection<IntervalAssociation> associations, Link inherited) {
		if (inherited == null)
			return new Link(new ArrayList<>(associations), new ArrayList<>());
		else
			return new Link(new ArrayList<>(associations), inherited.getInheritance());
	}

}
