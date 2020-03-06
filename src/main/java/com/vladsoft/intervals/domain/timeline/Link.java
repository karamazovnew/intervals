package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.PointType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Link<T extends Comparable<T>> {

	private Collection<Interval<T>> finished;
	private Collection<Interval<T>> ongoing;

	protected Link(PointType pointType, Interval<T> interval, Collection<Interval<T>> inherited) {
		switch (pointType) {
			case START:
				ongoing = inherited == null ? new ArrayList<>() : new ArrayList<>(inherited);
				ongoing.add(interval);
				break;
			case INSTANT:
				finished = new ArrayList<>(Collections.singletonList(interval));
				ongoing = inherited == null ? null : new ArrayList<>(inherited);
				break;
			case END:
				finished = new ArrayList<>(Collections.singletonList(interval));
				ongoing = inherited == null ? null : inherited.stream()
						.filter(i -> !interval.equals(i)).collect(Collectors.toList());
		}
	}

	protected Collection<Interval<T>> getFinished() {
		return finished;
	}

	protected Collection<Interval<T>> getAllIntervals() {
		return Stream.concat(finished == null ? Stream.empty() : finished.stream(), ongoing == null ? Stream.empty() : ongoing.stream())
				.collect(Collectors.toList());
	}

	protected Collection<Interval<T>> getOngoing() {
		return ongoing;
	}

	protected void addAssociation(PointType pointType, Interval<T> interval) {
		if (!pointType.equals(PointType.START)) {
			if (finished == null) {
				finished = new ArrayList<>();
			}
			finished.add(interval);
		} else {
			if (ongoing == null)
				ongoing = new ArrayList<>();
			ongoing.add(interval);
		}
	}

}
