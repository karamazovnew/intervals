package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class IntervalImpl<T> implements Interval<T> {

	private IntervalAssociation startPoint;
	private IntervalAssociation endPoint;

	public IntervalImpl(Point<T> startPoint, Point<T> endPoint) {
		if(startPoint.compareTo(endPoint) > 0)
			throw new IllegalArgumentException("EndPoint must be after StartPoint");
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
