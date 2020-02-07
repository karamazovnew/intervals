package com.vladsoft.intervals.domain;

public interface Interval<T> {

	IntervalAssociation<T> getStartPoint();

	IntervalAssociation<T> getEndPoint();

}
