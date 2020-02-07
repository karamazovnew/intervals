package com.vladsoft.intervals.domain.timeline;

public interface LinkVisitor<T> {

	void visit(TimelineLink<T> link);

}
