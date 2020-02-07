package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;

import java.util.Collection;

public class TimelineLink<T> implements Point<T> {

	private Timeline<T> timeline;

	private Point<T> point;
	private TimelineLink<T> previous, next;

	protected TimelineLink(Point<T> point, TimelineLink<T> previous, Timeline<T> timeline) {
		this.point = point;
		this.timeline = timeline;
		//link is placed after head
		if (previous != null) {
			this.previous = previous;
			next = previous.getNext();
			previous.setNext(this);
			if (next != null)
				next.setPrevious(this);
		}
		//TODO: link is the new head;
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

	protected Timeline<T> getTimeline() {
		return timeline;
	}

	protected Point<T> getPoint() {
		return point;
	}

	protected void accept(LinkVisitor<T> visitor){
		visitor.visit(this);
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
