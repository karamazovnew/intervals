package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.TimelineVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InsertAfterTest<T> {

	private TimelineVisitor<T> fixture;

	@Mock
	private TimelineLink<T> visited, visitor, afterVisitor;

	@BeforeEach
	void setUp() {
		fixture = new InsertAfter<>(visitor);
	}

	@Test
	void visitorIsNotLastLink() {
		when(visitor.getNext()).thenReturn(afterVisitor);

		fixture.visit(visited);

		verify(visitor).setNext(visited);
		verify(visited).setPrevious(visitor);
		verify(visited).setNext(afterVisitor);
		verify(afterVisitor).setPrevious(visited);
	}

	@Test
	void visitorIsLastLink() {
		when(visitor.getNext()).thenReturn(null);

		fixture.visit(visited);

		verify(visitor).setNext(visited);
		verify(visited).setPrevious(visitor);
		verify(visited).setNext(null);
	}

}
