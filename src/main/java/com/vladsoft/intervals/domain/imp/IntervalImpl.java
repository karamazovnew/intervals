package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class IntervalImpl implements Interval {

	private IntervalAssociation startPoint;
	private IntervalAssociation endPoint;

	public IntervalImpl(Point startPoint, Point endPoint) {
		//TODO: replace with factory
		this.startPoint = new IntervalAssociationImpl(startPoint, PointType.START, this);
		this.endPoint = new IntervalAssociationImpl(endPoint, PointType.END, this);
	}

	@Override
	public IntervalAssociation getStartPoint() {
		return startPoint;
	}

	@Override
	public IntervalAssociation getEndPoint() {
		return endPoint;
	}

}
