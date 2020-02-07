package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
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
class TimelineLinkTest<T> {

	private TimelineLink<T> fixture;

	@Mock
	private Point<T> point, otherPoint;

	@Mock
	private TimelineLink<T> previousLink, nextLink;

	@Mock
	private T value;

	@Mock
	private Timeline<T> timeline;

	@Mock
	private IntervalAssociation association, newAssociation;

	@BeforeEach
	void setUp() {
		lenient().when(point.getValue()).thenReturn(value);
		lenient().when(point.getAssociations()).thenReturn(Arrays.asList(association));
		fixture = new TimelineLink<>(point, null, timeline);
	}

	@Test
	void initTest() {
		assertThat(fixture.getPoint(), is(point));
		assertThat(fixture.getTimeline(), is(timeline));
		assertThat(fixture.getNext(), is(nullValue()));
		assertThat(fixture.getPrevious(), is(nullValue()));
	}

	@Test
	void initWithPrevious() {
		when(previousLink.getNext()).thenReturn(nextLink);

		fixture = new TimelineLink<T>(point, previousLink, timeline);

		assertThat(fixture.getPoint(), is(point));
		assertThat(fixture.getTimeline(), is(timeline));
		assertThat(fixture.getPrevious(), is(previousLink));
		verify(previousLink).setNext(fixture);
		assertThat(fixture.getNext(), is(nextLink));
		verify(nextLink).setPrevious(fixture);
	}

	@Test
	void initWithPreviousWhenLast(){
		when(previousLink.getNext()).thenReturn(null);

		fixture = new TimelineLink<T>(point, previousLink, timeline);

		assertThat(fixture.getPoint(), is(point));
		assertThat(fixture.getTimeline(), is(timeline));
		assertThat(fixture.getPrevious(), is(previousLink));
		verify(previousLink).setNext(fixture);
		assertThat(fixture.getNext(), is(nullValue()));
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
	void compareTo() {
		int someInt = new Random().nextInt();
		when(point.compareTo(otherPoint)).thenReturn(someInt);

		assertThat(fixture.compareTo(otherPoint), is(someInt));
	}

}
