package com.vladsoft.intervals.domain;

public interface Interval<T> {

	IntervalAssociation getStartPoint();

	IntervalAssociation getEndPoint();

}
