package com.vladsoft.intervals.domain;

import java.util.Collection;

public interface Timeline<T extends Comparable<T>> {

	void addInterval(Interval<T> interval);

	Collection<Interval<T>> getIntervals(T point);

	Collection<Interval<T>> getIntervals(T startPoint, T endPoint);

	Interval<T> getFirstGap(T startPoint, T endPoint);

	int getMaxOverlaps(T startPoint, T endPoint);

	int getIntervalsNumber();

}
