package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.*;
import com.vladsoft.intervals.domain.imp.PointImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimelineImplTest {

	private Timeline fixture;

	private Point pointA1, pointA2, pointB1, pointB2;

	@Mock
	private Interval intervalA, intervalB;

	@Mock
	private IntervalAssociation startA, endA, startB, endB;

	@BeforeEach
	void setUp() {
		lenient().when(intervalA.getStartPoint()).thenReturn(startA);
		lenient().when(intervalA.getEndPoint()).thenReturn(endA);
		lenient().when(intervalB.getStartPoint()).thenReturn(startB);
		lenient().when(intervalB.getEndPoint()).thenReturn(endB);
		lenient().when(startA.getInterval()).thenReturn(intervalA);
		lenient().when(endA.getInterval()).thenReturn(intervalA);
		lenient().when(startB.getInterval()).thenReturn(intervalB);
		lenient().when(endB.getInterval()).thenReturn(intervalB);
		fixture = new TimelineImpl();
	}

	@Test
	void addFirstInstantInterval() {
		pointA1 = new PointImpl(1);
		pointA2 = new PointImpl(1);
		when(startA.getPoint()).thenReturn(pointA1);
		when(endA.getPoint()).thenReturn(pointA2);

		fixture.addInterval(intervalA);

		assertThat(fixture.getIntervals(1), contains(intervalA));
	}

	@Test
	void addTwoDifferentInstantIntervals() {
		pointA1 = new PointImpl(0);
		pointA2 = new PointImpl(0);
		pointB1 = new PointImpl(5);
		pointB2 = new PointImpl(5);
		when(startA.getPoint()).thenReturn(pointA1);
		when(endA.getPoint()).thenReturn(pointA2);
		when(startA.getType()).thenReturn(PointType.INSTANT);
		when(startB.getPoint()).thenReturn(pointB1);
		when(endB.getPoint()).thenReturn(pointB2);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		assertThat(fixture.getIntervals(0), contains(intervalA));
		assertThat(fixture.getIntervals(5), contains(intervalB));
	}

	@Test
	void addSimpleInterval() {
		pointA1 = new PointImpl(2);
		pointA2 = new PointImpl(4);
		when(startA.getPoint()).thenReturn(pointA1);
		when(endA.getPoint()).thenReturn(pointA2);
		when(startA.getType()).thenReturn(PointType.START);
		lenient().when(endA.getType()).thenReturn(PointType.END);

		fixture.addInterval(intervalA);

		assertThat(fixture.getIntervals(1), empty());
		assertThat(fixture.getIntervals(2), contains(intervalA));
		assertThat(fixture.getIntervals(3), contains(intervalA));
		assertThat(fixture.getIntervals(4), contains(intervalA));
		assertThat(fixture.getIntervals(5), empty());
	}

	@Test
	void addTwoIntervals() {
		pointA1 = new PointImpl(3);
		pointA2 = new PointImpl(5);
		pointB1 = new PointImpl(2);
		pointB2 = new PointImpl(6);
		when(startA.getPoint()).thenReturn(pointA1);
		when(endA.getPoint()).thenReturn(pointA2);
		when(startA.getType()).thenReturn(PointType.START);
		lenient().when(endA.getType()).thenReturn(PointType.END);
		when(startB.getPoint()).thenReturn(pointB1);
		when(endB.getPoint()).thenReturn(pointB2);
		lenient().when(startB.getType()).thenReturn(PointType.START);
		lenient().when(endB.getType()).thenReturn(PointType.END);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		assertThat(fixture.getIntervals(1), empty());
		assertThat(fixture.getIntervals(2), contains(intervalB));
		assertThat(fixture.getIntervals(3), containsInAnyOrder(intervalA, intervalB));
		assertThat(fixture.getIntervals(4), containsInAnyOrder(intervalA, intervalB));
		assertThat(fixture.getIntervals(5), containsInAnyOrder(intervalA, intervalB));
		assertThat(fixture.getIntervals(6), contains(intervalB));
		assertThat(fixture.getIntervals(7), empty());
	}

	@Test
	void getIntervalsAtPoint() {
		pointA1 = new PointImpl(2);
		pointA2 = new PointImpl(4);
		when(startA.getPoint()).thenReturn(pointA1);
		when(endA.getPoint()).thenReturn(pointA2);
		when(startA.getType()).thenReturn(PointType.START);
		lenient().when(endA.getType()).thenReturn(PointType.END);

		fixture.addInterval(intervalA);

		assertThat(fixture.getIntervals(new PointImpl(3)), is(fixture.getIntervals(3)));
		assertThat(fixture.getIntervals(new PointImpl(4)), contains(intervalA));
		assertThat(fixture.getIntervals(new PointImpl(5)), is(empty()));
	}

	private class TestValue<T extends Comparable<T>> implements Comparable<TestValue<T>> {

		private T value;

		public TestValue(T value) {
			this.value = value;
		}

		public T getValue() {
			return value;
		}

		@Override
		public int compareTo(TestValue<T> o) {
			return value.compareTo(o.getValue());
		}

	}

}
