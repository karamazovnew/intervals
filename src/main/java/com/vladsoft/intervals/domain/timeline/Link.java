package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;

import java.util.ArrayList;
import java.util.Collection;

public class Link<T extends Comparable<T>> {

	private Collection<Interval<T>> intervals;

	//START link
	protected Link(Interval<T> interval, Collection<Interval<T>> inherited) {
		intervals = inherited == null ? new ArrayList<>() : new ArrayList<>(inherited);
		intervals.add(interval);
	}

	//END link
	protected Link(Collection<Interval<T>> inherited) {
		intervals = inherited == null ? null : new ArrayList<>(inherited);
	}

	protected Collection<Interval<T>> getIntervals() {
		return intervals;
	}

	protected void addInterval(Interval<T> interval) {
		if (intervals == null)
			intervals = new ArrayList<>();
		intervals.add(interval);
	}

}
