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

@ExtendWith(MockitoExtension.class)
class PointImplTest<T extends Comparable<T>> {

	private Point<T> fixture;

	@Mock
	private Interval<T> parent;

	@Mock
	private T value;

	private PointType pointType;

	@BeforeEach
	void init() {
		pointType = PointType.START;
		fixture = new PointImpl<T>(value, pointType, parent);
	}

	@Test
	void getInterval() {
		assertThat(fixture.getInterval(), is(parent));
	}

	@Test
	void getValue() {
		assertThat(fixture.getValue(), is(value));
	}

	@Test
	void getType() {
		assertThat(fixture.getType(), is(pointType));
	}

}
