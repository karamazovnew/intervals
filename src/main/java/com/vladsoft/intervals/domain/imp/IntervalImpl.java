package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class IntervalImpl<T extends Comparable<T>> implements Interval<T> {

	private Point<T> startPoint;
	private Point<T> endPoint;

	public IntervalImpl(T startPoint, T endPoint) throws IllegalArgumentException {
		int compare = startPoint.compareTo(endPoint);
		if (compare > 0)
			throw new IllegalArgumentException("EndPoint must be after StartPoint");
		else if (compare == 0) {
			this.startPoint = new PointImpl<>(startPoint, PointType.INSTANT, this);
			this.endPoint = new PointImpl<>(endPoint, PointType.INSTANT, this);
		} else {
			this.startPoint = new PointImpl<>(startPoint, PointType.START, this);
			this.endPoint = new PointImpl<>(endPoint, PointType.END, this);
		}
	}

	@Override
	public Point<T> getStartPoint() {
		return startPoint;
	}

	@Override
	public Point<T> getEndPoint() {
		return endPoint;
	}

	@Override
	public String toString() {
		return "interval [" + startPoint.getValue() + "/" +
				endPoint.getValue() + "]";
	}

}
