package com.vladsoft.intervals.domain;

import com.vladsoft.intervals.domain.imp.IntervalAssociationImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class IntervalAssociationImplTest {

	private IntervalAssociation fixture;

	@Mock
	private Interval parent;

	@Mock
	private Point value;

	private PointType pointType;

	@BeforeEach
	void init() {
		pointType = PointType.START;
		fixture = new IntervalAssociationImpl(value, pointType, parent);
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
