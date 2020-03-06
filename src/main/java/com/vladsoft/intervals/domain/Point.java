package com.vladsoft.intervals.domain;

public interface Point<T extends Comparable<T>> {

	T getValue();

	Interval<T> getInterval();

	PointType getType();

}
