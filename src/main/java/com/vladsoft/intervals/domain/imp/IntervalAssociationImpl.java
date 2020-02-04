package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class IntervalAssociationImpl implements IntervalAssociation {

	private Interval interval;
	private Point point;
	private PointType type;

	public IntervalAssociationImpl(Point value, PointType pointType, Interval parent) {
		point = value;
		type = pointType;
		interval = parent;
		value.addAssociation(this);
	}

	@Override
	public Interval getInterval() {
		return interval;
	}

	@Override
	public Point getPoint() {
		return point;
	}

	@Override
	public PointType getType() {
		return type;
	}

}
