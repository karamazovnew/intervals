package com.vladsoft.intervals.domain.timeline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InsertBeforeTest<T> {

	private LinkVisitor<T> fixture;

	@Mock
	private TimelineLink<T> visited, visitor, beforeVisitor;

	@BeforeEach
	void setUp() {
		fixture = new InsertBefore(visitor);
	}

	@Test
	void visit() {
		when(visitor.getPrevious()).thenReturn(beforeVisitor);

		fixture.visit(visited);

		verify(visitor).setPrevious(visited);
		verify(visited).setNext(visitor);
		verify(visited).setPrevious(beforeVisitor);
		verify(beforeVisitor).setNext(visited);
	}

	@Test
	void visitFirst() {
		when(visitor.getPrevious()).thenReturn(null);

		fixture.visit(visited);

		verify(visitor).setPrevious(visited);
		verify(visited).setNext(visitor);
		verify(visited).setPrevious(null);
	}

}
