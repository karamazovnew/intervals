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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IntervalAssociationImplTest<T> {

	private IntervalAssociation<T> fixture;

	@Mock
	private Interval<T> parent;

	@Mock
	private Point<T> value;

	private PointType pointType;

	@BeforeEach
	void init() {
		pointType = PointType.START;
		fixture = new IntervalAssociationImpl<>(value, pointType, parent);
	}

	@Test
	void intervalAssociation() {
		verify(value).addAssociation(fixture);
	}

	@Test
	void getInterval() {
		assertSame(parent, fixture.getInterval());
	}

	@Test
	void getPoint() {
		assertSame(value, fixture.getPoint());
	}

	@Test
	void getType() {
		assertSame(pointType, fixture.getType());
	}

}
