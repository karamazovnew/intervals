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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IntervalAssociationImplTest<T> {

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
	void intervalAssociation() {
		verify(value).addAssociation(fixture);
	}

	@Test
	void getInterval() {
		assertThat(fixture.getInterval(), is(parent));
	}

	@Test
	void getPoint() {
		assertThat(fixture.getPoint(), is(value));
	}

	@Test
	void getType() {
		assertThat(fixture.getType(), is(pointType));
	}

}
