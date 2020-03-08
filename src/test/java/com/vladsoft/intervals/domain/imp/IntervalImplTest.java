package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.vladsoft.intervals.domain.PointType.END;
import static com.vladsoft.intervals.domain.PointType.START;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntervalImplTest<T extends Comparable<T>> {

	private Interval<T> fixture;

	@Mock
	private T startPoint, endPoint;

	@BeforeEach
	void setUp() {
		when(startPoint.compareTo(endPoint)).thenReturn(-1);
		fixture = new IntervalImpl<>(startPoint, endPoint);
	}

	@Test
	void illegalInterval() {
		when(startPoint.compareTo(endPoint)).thenReturn(1);

		assertThrows(IllegalArgumentException.class, () -> new IntervalImpl<>(startPoint, endPoint));
	}

	@Test
	void illegalInstantInterval() {
		when(startPoint.compareTo(endPoint)).thenReturn(0);

		assertThrows(IllegalArgumentException.class, () -> new IntervalImpl<>(startPoint, endPoint));
	}

	@Test
	void getStartNode() {
		Point<T> intervalStart = fixture.getStartPoint();

		assertThat(intervalStart.getValue(), is(startPoint));
		assertThat(intervalStart.getInterval(), is(fixture));
		assertThat(intervalStart.getType(), is(START));
	}

	@Test
	void getEndNode() {
		Point<T> intervalEnd = fixture.getEndPoint();

		assertThat(intervalEnd.getValue(), is(endPoint));
		assertThat(intervalEnd.getInterval(), is(fixture));
		assertThat(intervalEnd.getType(), is(END));
	}

}
