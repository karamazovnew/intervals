package com.vladsoft.intervals.domain.timeline;

public class InsertBefore<T> implements LinkVisitor<T> {

	private TimelineLink<T> visitor;

	public InsertBefore(TimelineLink<T> visitor) {
		this.visitor = visitor;
	}

	@Override
	public void visit(TimelineLink<T> visited) {
		TimelineLink<T> beforeVisitor = visitor.getPrevious();
		visitor.setPrevious(visited);
		visited.setNext(visitor);
		visited.setPrevious(beforeVisitor);
		if (beforeVisitor != null) {
			beforeVisitor.setNext(visited);
		}
	}

}
