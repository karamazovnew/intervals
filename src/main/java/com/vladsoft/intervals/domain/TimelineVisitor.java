package com.vladsoft.intervals.domain;

public interface TimelineVisitor<T> {

	void visit(Point<T> point);

}
