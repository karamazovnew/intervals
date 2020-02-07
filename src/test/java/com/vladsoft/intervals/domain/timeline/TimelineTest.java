package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimelineTest<T> {

	private Timeline<T> fixture;

	private TimelineLink<T> head;

	@Mock
	private Point<T> point1, point2, point3;

	@BeforeEach
	void setUp() {
		fixture = new Timeline<>();
		head = (TimelineLink<T>) fixture.addPoint(point1);
	}

	@Test
	void addHead() {
		assertThat(fixture.getHead(), is(head));
		assertThat(fixture.getCurrent(), is(head));
	}

	@Test
	void addPointsInOrder() {
		when(point1.compareTo(point2)).thenReturn(-1);
		when(point1.compareTo(point3)).thenReturn(-1);
		when(point2.compareTo(point3)).thenReturn(-1);

		TimelineLink<T> secondAdded = (TimelineLink<T>) fixture.addPoint(point2);

		assertThat(head.getNext(), is(secondAdded));
		assertThat(secondAdded.getPrevious(), is(head));
		assertThat(secondAdded.getNext(), is(nullValue()));

		TimelineLink<T> thirdAdded = (TimelineLink<T>) fixture.addPoint(point3);

		assertThat(head.getNext(), is(secondAdded));
		assertThat(secondAdded.getPrevious(), is(head));
		assertThat(secondAdded.getNext(), is(thirdAdded));
		assertThat(thirdAdded.getPrevious(), is(secondAdded));
		assertThat(thirdAdded.getNext(), is(nullValue()));
	}

	@Test
	void addPointsInRandomOrder() {
		when(point1.compareTo(point2)).thenReturn(-1);
		when(point1.compareTo(point3)).thenReturn(-1);
		when(point2.compareTo(point3)).thenReturn(1);

		TimelineLink<T> secondAdded = (TimelineLink<T>) fixture.addPoint(point2);
		TimelineLink<T> thirdAdded = (TimelineLink<T>) fixture.addPoint(point3);

		assertThat(head.getNext(), is(thirdAdded));
		assertThat(thirdAdded.getPrevious(), is(head));
		assertThat(thirdAdded.getNext(), is(secondAdded));
		assertThat(secondAdded.getPrevious(), is(thirdAdded));
		assertThat(secondAdded.getNext(), is(nullValue()));
	}

}
