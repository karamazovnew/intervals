package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;

import java.util.Collection;

public class TimelineLink<T> implements Point<T> {

	private Point<T> point;
	private TimelineLink<T> previous, next;

	protected TimelineLink(Point<T> point) {
		this.point = point;
	}

	@Override
	public T getValue() {
		return point.getValue();
	}

	@Override
	public Collection<IntervalAssociation<T>> getAssociations() {
		return point.getAssociations();
	}

	@Override
	public boolean addAssociation(IntervalAssociation<T> association) {
		return point.addAssociation(association);
	}

	@Override
	public int compareTo(Point<T> o) {
		return point.compareTo(o);
	}

	public int compareTo(TimelineLink<T> o) {
		TimelineLink<T> actual = (TimelineLink<T>) o;
		return point.compareTo((actual.getPoint()));
	}

	protected void accept(LinkVisitor<T> visitor) {
		visitor.visit(this);
	}

	protected Point<T> getPoint() {
		return point;
	}

	protected TimelineLink<T> getPrevious() {
		return previous;
	}

	protected void setPrevious(TimelineLink<T> previous) {
		this.previous = previous;
	}

	protected TimelineLink<T> getNext() {
		return next;
	}

	protected void setNext(TimelineLink<T> next) {
		this.next = next;
	}

}
