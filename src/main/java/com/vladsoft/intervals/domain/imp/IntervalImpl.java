package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class IntervalImpl implements Interval {

	private IntervalAssociation startPoint;
	private IntervalAssociation endPoint;

	public IntervalImpl(Point startPoint, Point endPoint) throws IllegalArgumentException, ClassCastException {
		int compare = startPoint.compareTo(endPoint);
		if (compare > 0)
			throw new IllegalArgumentException("EndPoint must be after StartPoint");
		else if (compare == 0) {
			this.startPoint = new IntervalAssociationImpl(startPoint, PointType.INSTANT, this);
			this.endPoint = new IntervalAssociationImpl(endPoint, PointType.INSTANT, this);
		} else {
			this.startPoint = new IntervalAssociationImpl(startPoint, PointType.START, this);
			this.endPoint = new IntervalAssociationImpl(endPoint, PointType.END, this);
		}
	}

	@Override
	public IntervalAssociation getStartPoint() {
		return startPoint;
	}

	@Override
	public IntervalAssociation getEndPoint() {
		return endPoint;
	}

	@Override
	public String toString() {
		return "interval [" + startPoint.getPoint().getValue() + "/" +
				endPoint.getPoint().getValue() + "]";
	}

}
