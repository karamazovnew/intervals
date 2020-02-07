package com.vladsoft.intervals.domain;

public interface IntervalAssociation<T> {

	Interval<T> getInterval();

	Point<T> getPoint();

	PointType getType();

}
