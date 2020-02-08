package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.TimelineVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimelineImplLinkTest<T> {

	private TimelineLink<T> fixture;

	@Mock
	private Point<T> point, otherPoint;

	@Mock
	private TimelineLink<T> otherLink;

	@Mock
	private T value;

	@Mock
	private IntervalAssociation<T> association, newAssociation;

	@Mock
	private TimelineVisitor<T> visitor;

	@BeforeEach
	void setUp() {
		lenient().when(point.getValue()).thenReturn(value);
		lenient().when(point.getAssociations()).thenReturn(Arrays.asList(association));
		fixture = new TimelineLink<>(point);
	}

	@Test
	void initTest() {
		assertThat(fixture.getPoint(), is(point));
		assertThat(fixture.getNext(), is(nullValue()));
		assertThat(fixture.getPrevious(), is(nullValue()));
	}

	@Test
	void getValue() {
		assertThat(fixture.getValue(), is(value));
	}

	@Test
	void getAssociations() {
		assertThat(fixture.getAssociations(), contains(association));
	}

	@Test
	void addAssociation() {
		fixture.addAssociation(newAssociation);

		verify(point).addAssociation(newAssociation);
	}

	@Test
	void compareToLink() {
		int someInt = new Random().nextInt();
		when(otherLink.getPoint()).thenReturn(otherPoint);
		when(point.compareTo(otherPoint)).thenReturn(someInt);

		assertThat(fixture.compareTo(otherLink), is(someInt));
	}

	@Test
	void compareToPoint() {
		int someInt = new Random().nextInt();
		when(point.compareTo(otherPoint)).thenReturn(someInt);

		assertThat(fixture.compareTo(otherPoint), is(someInt));
	}

	@Test
	void accept() {
		fixture.accept(visitor);

		verify(visitor).visit(fixture);
	}

}
