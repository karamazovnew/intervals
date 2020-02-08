package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.Timeline;

public class TimelineImpl<T> implements Timeline<T> {

	private TimelineLink<T> head;
	private TimelineLink<T> cursor;

	public TimelineImpl() {
	}

	public Point<T> resetCursor() {
		cursor = head;
		return getCursor();
	}

	public Point<T> getCursor() {
		return cursor == null ? null : cursor.getPoint();
	}

	public Point<T> traverse() {
		if (cursor != null) {
			cursor = cursor.getNext();
			return getCursor();
		}
		return null;
	}

	@Override
	public void addInterval(Interval<T> interval) {
		TimelineLink<T> startPoint = makeLink(interval.getStartPoint().getPoint());
		TimelineLink<T> endPoint = makeLink(interval.getEndPoint().getPoint());
		if (head == null) {
			head = startPoint;
		} else {
			if (cursor == null)
				cursor = head;
			else if (cursor.compareTo(startPoint) > 0) {
				cursor = head;
			}
			while (!addPoint(startPoint, cursor))
				cursor = cursor.getNext();
		}
		cursor = startPoint;
		while (!addPoint(endPoint, cursor))
			cursor = cursor.getNext();
	}

	private boolean addPoint(TimelineLink<T> point, TimelineLink<T> current) {
		int comparison = current.compareTo(point);
		if (comparison > 0) {
			if (current == head)
				head = point;
			point.accept(insertBefore(current));
			return true;
		}
		if (comparison < 0 && current.getNext() == null) {
			point.accept(insertAfter(current));
			return true;
		}
		if (comparison == 0) {
			point = current;
			//TODO: handle equality
			return true;
		}
		return false;
	}

	private InsertBefore<T> insertBefore(TimelineLink<T> current) {
		return new InsertBefore<>(current);
	}

	private InsertAfter<T> insertAfter(TimelineLink<T> current) {
		return new InsertAfter<>(current);
	}

	private TimelineLink<T> makeLink(Point<T> point) {
		return new TimelineLink<>(point);
	}

}
