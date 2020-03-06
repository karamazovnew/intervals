package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntervalImplTest<T extends Comparable<T>> {

	private Interval<T> fixture;

	@Mock
	private T startPoint, endPoint, firstPoint, secondPoint;

	@BeforeEach
	void setUp() {
		when(startPoint.compareTo(endPoint)).thenReturn(-1);
		fixture = new IntervalImpl<T>(startPoint, endPoint);
	}

	@Test
	void illegalInterval() {
		when(firstPoint.compareTo(secondPoint)).thenReturn(1);

		assertThrows(IllegalArgumentException.class, () -> new IntervalImpl<>(firstPoint, secondPoint));
	}

	@Test
	void getStartNode() {
		Point<T> intervalStart = fixture.getStartPoint();

		assertThat(intervalStart.getValue(), is(startPoint));
		assertThat(intervalStart.getInterval(), is(fixture));
		assertThat(intervalStart.getType(), is(PointType.START));
	}

	@Test
	void getEndNode() {
		Point<T> intervalEnd = fixture.getEndPoint();

		assertThat(intervalEnd.getValue(), is(endPoint));
		assertThat(intervalEnd.getInterval(), is(fixture));
		assertThat(intervalEnd.getType(), is(PointType.END));
	}

	@Test
	void getInstantNodes() {
		when(startPoint.compareTo(endPoint)).thenReturn(0);

		fixture = new IntervalImpl<>(startPoint, endPoint);

		assertThat(fixture.getStartPoint().getType(), is(PointType.INSTANT));
	}

}
