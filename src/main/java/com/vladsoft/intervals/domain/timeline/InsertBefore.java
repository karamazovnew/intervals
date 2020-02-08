package com.vladsoft.intervals.domain.timeline;

public final class InsertBefore<T> extends TimelineVisitorImpl<T> {

	public InsertBefore(TimelineLink<T> visitor) {
		super(visitor);
	}

	@Override
	protected void visit(TimelineLink<T> visited) {
		TimelineLink<T> beforeVisitor = getVisitor().getPrevious();
		getVisitor().setPrevious(visited);
		visited.setNext(getVisitor());
		visited.setPrevious(beforeVisitor);
		if (beforeVisitor != null) {
			beforeVisitor.setNext(visited);
		}
	}

}
