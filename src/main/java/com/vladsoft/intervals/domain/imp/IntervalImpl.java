package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;

public class IntervalImpl<T extends Comparable<T>> implements Interval<T> {

	private Point<T> startPoint;
	private Point<T> endPoint;

	public IntervalImpl(T startPoint, T endPoint) throws IllegalArgumentException {
		if (startPoint.compareTo(endPoint) >= 0)
			throw new IllegalArgumentException("EndPoint must be after StartPoint");
		else {
			this.startPoint = ImplFactory.makePoint(startPoint, PointType.START, this);
			this.endPoint = ImplFactory.makePoint(endPoint, PointType.END, this);
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
