package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.PointType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkTest<T extends Comparable<T>> {

	private Link<T> fixture;

	@Mock
	private Point<T> point1, point2, point3;

	@Mock
	private Point<T> inherited1, inherited2;

	@Mock
	private Interval<T> interval, otherInterval;

	@Test
	void newInstant() {
		when(point1.getType()).thenReturn(PointType.INSTANT);

		fixture = new Link<>(point1, null);

		assertThat(fixture.getFinished(), containsInAnyOrder(point1));
		assertThat(fixture.getOngoing(), is(nullValue()));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				contains(point1));
	}

	@Test
	void newInstantWithInheritance() {
		when(point1.getType()).thenReturn(PointType.INSTANT);

		fixture = new Link<>(point1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getFinished(), containsInAnyOrder(point1));
		assertThat(fixture.getOngoing(), containsInAnyOrder(inherited1, inherited2));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				containsInAnyOrder(point1, inherited1, inherited2));
	}

	@Test
	void newEnd() {
		when(point1.getType()).thenReturn(PointType.END);

		fixture = new Link<>(point1, null);

		assertThat(fixture.getFinished(), containsInAnyOrder(point1));
		assertThat(fixture.getOngoing(), is(nullValue()));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				contains(point1));
	}

	@Test
	void newEndWithInheritance() {
		when(point1.getType()).thenReturn(PointType.END);
		when(point1.getInterval()).thenReturn(interval);
		when(inherited1.getInterval()).thenReturn(interval);
		when(inherited2.getInterval()).thenReturn(otherInterval);

		fixture = new Link<>(point1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getFinished(), containsInAnyOrder(point1));
		assertThat(fixture.getOngoing(), contains(inherited2));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				containsInAnyOrder(point1, inherited2));
	}

	@Test
	void newStart() {
		when(point1.getType()).thenReturn(PointType.START);

		fixture = new Link<>(point1, null);

		assertThat(fixture.getOngoing(), containsInAnyOrder(point1));
		assertThat(fixture.getFinished(), is(nullValue()));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				contains(point1));
	}

	@Test
	void newStartWithInheritance() {
		when(point1.getType()).thenReturn(PointType.START);

		fixture = new Link<>(point1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getOngoing(), containsInAnyOrder(point1, inherited1, inherited2));
		assertThat(fixture.getFinished(), is(nullValue()));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				containsInAnyOrder(point1, inherited1, inherited2));
	}

	@Test
	void addStartAssociation() {
		when(point1.getType()).thenReturn(PointType.START);
		when(point2.getType()).thenReturn(PointType.START);

		fixture = new Link<>(point1, null);
		fixture.addAssociation(point2);

		assertThat(fixture.getOngoing(), hasItem(point2));
	}

	@Test
	void addNonStartAssociation() {
		when(point1.getType()).thenReturn(PointType.START);
		when(point2.getType()).thenReturn(PointType.INSTANT);
		when(point3.getType()).thenReturn(PointType.END);

		fixture = new Link<>(point1, null);
		fixture.addAssociation(point2);
		fixture.addAssociation(point3);

		assertThat(fixture.getFinished(), hasItems(point2, point3));
	}

}
