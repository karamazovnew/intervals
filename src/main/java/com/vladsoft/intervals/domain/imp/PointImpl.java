package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class PointImpl<T extends Comparable<T>> implements Point<T> {

	private T value;
	private Interval<T> interval;
	private PointType type;

	public PointImpl(T value, PointType pointType, Interval<T> parent) {
		this.value = value;
		type = pointType;
		interval = parent;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public Interval<T> getInterval() {
		return interval;
	}

	@Override
	public PointType getType() {
		return type;
	}

}
