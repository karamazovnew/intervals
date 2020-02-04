package com.vladsoft.intervals.domain;

import com.vladsoft.intervals.domain.imp.IntervalImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class IntervalImplTest {

	//TODO: mock a factory instead of testing IntervalAssociation behavior
	private Interval fixture;

	@Mock
	private Point startPoint, endPoint;

	@BeforeEach
	void setUp() {
		fixture = new IntervalImpl(startPoint, endPoint);
	}

	@Test
	void getStartNode() {
		IntervalAssociation intervalStart = fixture.getStartPoint();
		assertSame(startPoint, intervalStart.getPoint());
		assertSame(fixture, intervalStart.getInterval());
		assertSame(PointType.START, intervalStart.getType());
	}

	@Test
	void getEndNode() {
		IntervalAssociation intervalEnd = fixture.getEndPoint();
		assertSame(endPoint, intervalEnd.getPoint());
		assertSame(fixture, intervalEnd.getInterval());
		assertSame(PointType.END, intervalEnd.getType());
	}

}
