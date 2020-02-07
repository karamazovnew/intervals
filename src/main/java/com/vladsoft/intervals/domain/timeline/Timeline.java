package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Point;

public class Timeline<T> {

	private TimelineLink<T> head, current;

	public Timeline() {
	}

	public TimelineLink<T> getHead() {
		return head;
	}

	public TimelineLink<T> getCurrent() {
		return current;
	}

	public Point<T> addPoint(Point<T> point) {
		if (head == null) {
			head = new TimelineLink<>(point, null, this);
			current = head;
			return head;
		} else {
			return new TimelineLink<>(point, getClosest(head, point), this);
		}
	}

	private TimelineLink<T> getClosest(TimelineLink<T> from, Point<T> point) {
		int comparison = from.getPoint().compareTo(point);
		if (comparison < 0 && from.getNext() != null) {
			TimelineLink<T> closer = getClosest(from.getNext(), point);
			return closer != null ? closer : from;
		} else if (comparison > 0) {
			return null;
		}
		return from;
		//TODO: what if equal?
		//TODO: what if point is smaller than from?
	}

}
