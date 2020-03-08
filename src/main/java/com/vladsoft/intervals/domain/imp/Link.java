package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Link<T extends Comparable<T>> {

	private Collection<Interval<T>> intervals;

	//START link
	protected Link(Interval<T> interval, Collection<Interval<T>> inherited) {
		intervals = (inherited == null || inherited.isEmpty()) ? new ArrayList<>() : new ArrayList<>(inherited);
		intervals.add(interval);
	}

	//END link
	protected Link(Collection<Interval<T>> inherited) {
		intervals = (inherited == null || inherited.isEmpty()) ? null : new ArrayList<>(inherited);
	}

	protected Collection<Interval<T>> getIntervals() {
		return intervals != null ? intervals : Collections.emptyList();
	}

	protected void addInterval(Interval<T> interval) {
		if (intervals == null)
			intervals = new ArrayList<>();
		intervals.add(interval);
	}

}
