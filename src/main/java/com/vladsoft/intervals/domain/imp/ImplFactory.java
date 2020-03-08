package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class ImplFactory {

	public static <T extends Comparable<T>> Interval<T> makeInterval(T startPoint, T endPoint) {
		return new IntervalImpl<>(startPoint, endPoint);
	}

	public static <T extends Comparable<T>> Point<T> makePoint(T value, PointType pointType, Interval<T> parent) {
		return new PointImpl<>(value, pointType, parent);
	}

}
