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
		} else
			addPoint(startPoint, head);
		addPoint(endPoint, startPoint);
	}

	private void addPoint(TimelineLink<T> point, TimelineLink<T> current) {
		int comparison = current.compareTo(point);
		if (comparison > 0) {
			if(current == head)
				head = point;
			point.accept(insertBefore(current));
			return;
		}
		if (comparison < 0 && current.getNext() == null) {
			point.accept(insertAfter(current));
			return;
		}
		if (comparison == 0)
			//TODO: handle equality
			return;
		addPoint(point, current.getNext());
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
