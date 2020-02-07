package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class IntervalAssociationImpl<T> implements IntervalAssociation<T> {

	private Interval<T> interval;
	private Point<T> point;
	private PointType type;

	public IntervalAssociationImpl(Point<T> value, PointType pointType, Interval<T> parent) {
		point = value;
		type = pointType;
		interval = parent;
		value.addAssociation(this);
	}

	@Override
	public Interval<T> getInterval() {
		return interval;
	}

	@Override
	public Point<T> getPoint() {
		return point;
	}

	@Override
	public PointType getType() {
		return type;
	}

}
