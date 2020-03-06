package com.vladsoft.intervals.domain;

import java.util.Collection;

public interface Timeline {

	void addInterval(Interval interval);

	Collection<Interval> getIntervals(Comparable<?> value);

	Collection<Interval> getIntervals(Point point);

}
