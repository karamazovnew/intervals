package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Link<T extends Comparable<T>> {

	private Collection<Point<T>> finished;
	private Collection<Point<T>> ongoing;

	protected Link(Point<T> point, Collection<Point<T>> inherited) {
		PointType type = point.getType();
		switch (type) {
			case START:
				ongoing = inherited == null ? new ArrayList<>() : new ArrayList<>(inherited);
				ongoing.add(point);
				break;
			case INSTANT:
				finished = new ArrayList<>(Collections.singletonList(point));
				ongoing = inherited == null ? null : new ArrayList<>(inherited);
				break;
			case END:
				finished = new ArrayList<>(Collections.singletonList(point));
				ongoing = inherited == null ? null : inherited.stream()
						.filter(i -> !point.getInterval().equals(i.getInterval())).collect(Collectors.toList());
		}
	}

	protected Collection<Point<T>> getFinished() {
		return finished;
	}

	protected Stream<Point<T>> getAllAssociations() {
		return Stream.concat(finished == null ? Stream.empty() : finished.stream(), ongoing == null ? Stream.empty() : ongoing.stream());
	}

	protected Collection<Point<T>> getOngoing() {
		return ongoing;
	}

	protected void addAssociation(Point<T> association) {
		if (!association.getType().equals(PointType.START)) {
			if (finished == null) {
				finished = new ArrayList<>();
			}
			finished.add(association);
		} else {
			if (ongoing == null)
				ongoing = new ArrayList<>();
			ongoing.add(association);
		}
	}

}
