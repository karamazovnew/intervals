package com.vladsoft.intervals.domain.timeline;

public final class InsertAfter<T> extends TimelineVisitorImpl<T> {

	public InsertAfter(TimelineLink<T> visitor) {
		super(visitor);
	}

	@Override
	protected void visit(TimelineLink<T> visited) {
		TimelineLink<T> afterVisitor = getVisitor().getNext();
		getVisitor().setNext(visited);
		visited.setPrevious(getVisitor());
		visited.setNext(afterVisitor);
		if (afterVisitor != null) {
			afterVisitor.setPrevious(visited);
		}
	}

}
