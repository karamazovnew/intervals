package com.vladsoft.intervals.domain.timeline;

public class InsertAfter<T> implements LinkVisitor<T> {

	private TimelineLink<T> visitor;

	public InsertAfter(TimelineLink<T> visitor) {
		this.visitor = visitor;
	}

	@Override
	public void visit(TimelineLink<T> visited) {
		TimelineLink<T> afterVisitor = visitor.getNext();
		visitor.setNext(visited);
		visited.setPrevious(visitor);
		visited.setNext(afterVisitor);
		if (afterVisitor != null) {
			afterVisitor.setPrevious(visited);
		}
	}

}
