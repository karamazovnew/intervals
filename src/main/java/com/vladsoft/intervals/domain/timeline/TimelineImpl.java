package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.Timeline;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
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
			addInstantInterval(startAssociation);
		else {
			Comparable<?> first = addPoint(startAssociation);
			Comparable<?> second = addPoint(endAssociation);
			traverseInheritances(links.subMap(first, false, second, false), startAssociation);
		}
	}

	private void traverseInheritances(ConcurrentNavigableMap<Comparable<?>, Link> map,
									  IntervalAssociation association) {
		map.forEach((key, link) -> {
			link.addInheritance(association);
		});
	}

	@Override
	public Collection<Interval> getIntervals(Comparable<?> point) {
		Map.Entry<Comparable<?>, Link> found = links.floorEntry(point);
		if (found == null || (found.equals(links.lastEntry()) && links.get(point) == null))
			return Collections.emptyList();
		else
			return found.getValue().getAllAssociations().map(a -> a.getInterval()).collect(Collectors.toSet());
	}

	private void addInstantInterval(IntervalAssociation association) {
		Comparable<?> key = association.getPoint().getValue();
		Link found = links.get(key);
		if (found != null) {
			found.addAssociation(association);
		} else {
			Map.Entry<Comparable<?>, Link> previous = links.lowerEntry(key);
			if (previous != null)
				links.put(key, makeLink(Collections.singletonList(association),
						previous.getValue().getInheritance()));
			else
				links.put(key, makeLink(Collections.singletonList(association),
						null));
		}
	}

	private Comparable<?> addPoint(IntervalAssociation point) {
		Comparable<?> key = point.getPoint().getValue();
		Link found = links.get(key);
		if (found != null) {
			found.addAssociation(point);
		} else {
			Map.Entry<Comparable<?>, Link> previous = links.lowerEntry(key);
			if (previous != null)
				links.put(key, makeLink(point, previous.getValue().getInheritance()));
			else
				links.put(key, makeLink(point, null));
		}
		return key;
	}

	private Link makeLink(Collection<IntervalAssociation> associations, Collection<IntervalAssociation> inheritance) {
		return new Link(associations, inheritance);
	}

	private Link makeLink(IntervalAssociation association, Collection<IntervalAssociation> inheritance) {
		return new Link(association, inheritance);
	}

}
