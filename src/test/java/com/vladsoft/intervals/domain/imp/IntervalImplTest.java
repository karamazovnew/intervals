package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class IntervalImplTest<T> {

	//TODO: mock a factory instead of testing IntervalAssociation behavior
	private Interval<T> fixture;

	@Mock
	private Point<T> startPoint, endPoint;

	@BeforeEach
	void setUp() {
		fixture = new IntervalImpl<>(startPoint, endPoint);
	}

	@Test
	void getStartNode() {
		IntervalAssociation<T> intervalStart = fixture.getStartPoint();
		assertSame(startPoint, intervalStart.getPoint());
		assertSame(fixture, intervalStart.getInterval());
		assertSame(PointType.START, intervalStart.getType());
	}

	@Test
	void getEndNode() {
		IntervalAssociation<T> intervalEnd = fixture.getEndPoint();
		assertSame(endPoint, intervalEnd.getPoint());
		assertSame(fixture, intervalEnd.getInterval());
		assertSame(PointType.END, intervalEnd.getType());
	}

}
