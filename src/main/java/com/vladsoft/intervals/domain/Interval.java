package com.vladsoft.intervals.domain;

public interface Interval<T extends Comparable<T>> {

	Point<T> getStartPoint();

	Point<T> getEndPoint();

}
