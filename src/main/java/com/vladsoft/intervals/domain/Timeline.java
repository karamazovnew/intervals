package com.vladsoft.intervals.domain;

import java.util.Collection;

public interface Timeline<T extends Comparable<T>> {

	void addInterval(Interval<T> interval);

	Collection<Interval<T>> getIntervals(T value);

	Collection<Interval<T>> getIntervals(T start, T end);

}
