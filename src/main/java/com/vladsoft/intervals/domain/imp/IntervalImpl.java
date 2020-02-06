package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class IntervalImpl<T> implements Interval<T> {

	private IntervalAssociation<T> startPoint;
	private IntervalAssociation<T> endPoint;

	public IntervalImpl(Point<T> startPoint, Point<T> endPoint) {
		//TODO: replace with factory
		this.startPoint = new IntervalAssociationImpl<>(startPoint, PointType.START, this);
		this.endPoint = new IntervalAssociationImpl<>(endPoint, PointType.END, this);
	}

	@Override
	public IntervalAssociation<T> getStartPoint() {
		return startPoint;
	}

	@Override
	public IntervalAssociation<T> getEndPoint() {
		return endPoint;
	}

}
