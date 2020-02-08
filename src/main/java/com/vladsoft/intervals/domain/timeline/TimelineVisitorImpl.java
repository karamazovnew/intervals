package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.TimelineVisitor;

public abstract class TimelineVisitorImpl<T> implements TimelineVisitor<T> {

	private TimelineLink<T> visitor;

	public TimelineVisitorImpl(TimelineLink<T> visitor) {
		this.visitor = visitor;
	}

	protected TimelineLink<T> getVisitor() {
		return visitor;
	}

	@Override
	public final void visit(Point<T> visited) {
		visit((TimelineLink<T>) visited);
	}

	protected abstract void visit(TimelineLink<T> visited);

}
