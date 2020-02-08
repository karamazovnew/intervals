package com.vladsoft.intervals.domain;

public interface Timeline<T> {

	void addInterval(Interval<T> interval);

}
