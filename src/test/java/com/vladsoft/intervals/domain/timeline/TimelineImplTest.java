package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;
import com.vladsoft.intervals.domain.imp.PointImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimelineImplTest {

	//implementation only needed for getAssociations method
	private TimelineImpl fixture;

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

		assertThat(fixture.getIntervals(1), Matchers.contains(intervalA));
		assertThat(fixture.getAssociations(pointA1), containsInAnyOrder(startA, endA));
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
		when(endA.getType()).thenReturn(PointType.INSTANT);
		when(startB.getPoint()).thenReturn(pointB1);
		when(endB.getPoint()).thenReturn(pointB2);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		assertThat(fixture.getIntervals(0), Matchers.contains(intervalA));
		assertThat(fixture.getAssociations(pointA1), containsInAnyOrder(startA, endA));
		assertThat(fixture.getIntervals(5), Matchers.contains(intervalB));
		assertThat(fixture.getAssociations(pointB1), containsInAnyOrder(startB, endB));
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
