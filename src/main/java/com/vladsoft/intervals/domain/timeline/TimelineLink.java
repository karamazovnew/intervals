package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.TimelineVisitor;

import java.util.Collection;
import java.util.LinkedList;

public class TimelineLink<T> implements Point<T> {

	private Point<T> point;
	private TimelineLink<T> previous, next;
	private LinkedList<TimelineLink<T>> similars;

	protected TimelineLink(Point<T> point) {
		this.point = point;
		similars = new LinkedList<>();
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
		return point.compareTo((o.getPoint()));
	}

	protected void accept(TimelineVisitor<T> visitor) {
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

	protected LinkedList<TimelineLink<T>> getSimilars(){
		return similars;
	}

}
