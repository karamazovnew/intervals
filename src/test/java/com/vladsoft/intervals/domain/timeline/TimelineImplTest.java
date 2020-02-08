package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimelineImplTest<T> {

	private TimelineImpl<T> fixture;

	@Mock
	private Point<T> pointA1, pointA2, pointB1, pointB2;

	@Mock
	private T valueA1, valueA2, valueB1, valueB2;

	@Mock
	private Interval<T> intervalA, intervalB;
	@Mock
	private IntervalAssociation<T> startA, endA, startB, endB;

	@BeforeEach
	void setUp() {
		lenient().when(pointA1.getValue()).thenReturn(valueA1);
		lenient().when(pointA2.getValue()).thenReturn(valueA2);
		lenient().when(pointB1.getValue()).thenReturn(valueB1);
		lenient().when(pointB2.getValue()).thenReturn(valueB2);
		lenient().when(intervalA.getStartPoint()).thenReturn(startA);
		lenient().when(intervalA.getEndPoint()).thenReturn(endA);
		lenient().when(intervalB.getStartPoint()).thenReturn(startB);
		lenient().when(intervalB.getEndPoint()).thenReturn(endB);
		lenient().when(startA.getPoint()).thenReturn(pointA1);
		lenient().when(endA.getPoint()).thenReturn(pointA2);
		lenient().when(startB.getPoint()).thenReturn(pointB1);
		lenient().when(endB.getPoint()).thenReturn(pointB2);
		fixture = new TimelineImpl<>();
	}

	@Test
	void addFirstInterval() {
		when(pointA1.compareTo(pointA2)).thenReturn(-1);

		fixture.addInterval(intervalA);

		assertThat(fixture.resetCursor().getValue(), is(valueA1));
		assertThat(fixture.traverse().getValue(), is(valueA2));
	}

	@Test
	void add_A1_A2_B1_B2() {
		when(pointA1.compareTo(pointA2)).thenReturn(-1);
		when(pointA1.compareTo(pointB1)).thenReturn(-1);
		when(pointA2.compareTo(pointB1)).thenReturn(-1);
		when(pointB1.compareTo(pointB2)).thenReturn(-1);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		assertThat(fixture.resetCursor().getValue(), is(valueA1));
		assertThat(fixture.traverse().getValue(), is(valueA2));
		assertThat(fixture.traverse().getValue(), is(valueB1));
		assertThat(fixture.traverse().getValue(), is(valueB2));

	}

	@Test
	void add_B1_B2_A1_A2() {
		when(pointA1.compareTo(pointA2)).thenReturn(-1);
		when(pointA1.compareTo(pointB1)).thenReturn(1);
		when(pointB1.compareTo(pointB2)).thenReturn(-1);
		when(pointA1.compareTo(pointB2)).thenReturn(1);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		assertThat(fixture.resetCursor().getValue(), is(valueB1));
		assertThat(fixture.traverse().getValue(), is(valueB2));
		assertThat(fixture.traverse().getValue(), is(valueA1));
		assertThat(fixture.traverse().getValue(), is(valueA2));

	}

	@Test
	void add_A1_B1_A2_B2() {
		when(pointA1.compareTo(pointA2)).thenReturn(-1);
		when(pointA1.compareTo(pointB1)).thenReturn(-1);
		when(pointA2.compareTo(pointB1)).thenReturn(1);
		when(pointB1.compareTo(pointB2)).thenReturn(-1);
		when(pointA2.compareTo(pointB2)).thenReturn(-1);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		assertThat(fixture.resetCursor().getValue(), is(valueA1));
		assertThat(fixture.traverse().getValue(), is(valueB1));
		assertThat(fixture.traverse().getValue(), is(valueA2));
		assertThat(fixture.traverse().getValue(), is(valueB2));
	}

	@Test
	void add_B1_A1_A2_B2() {
		when(pointA1.compareTo(pointA2)).thenReturn(-1);
		when(pointA1.compareTo(pointB1)).thenReturn(1);
		when(pointB1.compareTo(pointB2)).thenReturn(-1);
		when(pointA1.compareTo(pointB2)).thenReturn(-1);
		when(pointA2.compareTo(pointB2)).thenReturn(-1);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		assertThat(fixture.resetCursor().getValue(), is(valueB1));
		assertThat(fixture.traverse().getValue(), is(valueA1));
		assertThat(fixture.traverse().getValue(), is(valueA2));
		assertThat(fixture.traverse().getValue(), is(valueB2));
	}

	@Test
	void add_B1_A1_B2_A2() {
		when(pointA1.compareTo(pointA2)).thenReturn(-1);
		when(pointA1.compareTo(pointB1)).thenReturn(1);
		when(pointB1.compareTo(pointB2)).thenReturn(-1);
		when(pointA1.compareTo(pointB2)).thenReturn(-1);
		when(pointA2.compareTo(pointB2)).thenReturn(1);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		assertThat(fixture.resetCursor().getValue(), is(valueB1));
		assertThat(fixture.traverse().getValue(), is(valueA1));
		assertThat(fixture.traverse().getValue(), is(valueB2));
		assertThat(fixture.traverse().getValue(), is(valueA2));
	}

}
