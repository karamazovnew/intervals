package com.vladsoft.intervals.domain;

public interface IntervalAssociation<T> {

	Interval getInterval();

	Point<T> getPoint();

	PointType getType();

}
