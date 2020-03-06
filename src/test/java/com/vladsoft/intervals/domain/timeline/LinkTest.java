package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
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
class LinkTest {

	private Link fixture;

	@Mock
	private IntervalAssociation association1, association2, association3;

	@Mock
	private IntervalAssociation inherited1, inherited2, inherited3;

	@Mock
	private Interval interval, otherInterval;

	@Test
	void newInstant() {
		when(association1.getType()).thenReturn(PointType.INSTANT);

		fixture = new Link(association1, null);

		assertThat(fixture.getFinished(), containsInAnyOrder(association1));
		assertThat(fixture.getOngoing(), is(nullValue()));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				contains(association1));
	}

	@Test
	void newInstantWithInheritance() {
		when(association1.getType()).thenReturn(PointType.INSTANT);

		fixture = new Link(association1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getFinished(), containsInAnyOrder(association1));
		assertThat(fixture.getOngoing(), containsInAnyOrder(inherited1, inherited2));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				containsInAnyOrder(association1, inherited1, inherited2));
	}

	@Test
	void newEnd() {
		when(association1.getType()).thenReturn(PointType.END);

		fixture = new Link(association1, null);

		assertThat(fixture.getFinished(), containsInAnyOrder(association1));
		assertThat(fixture.getOngoing(), is(nullValue()));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				contains(association1));
	}

	@Test
	void newEndWithInheritance() {
		when(association1.getType()).thenReturn(PointType.END);
		when(association1.getInterval()).thenReturn(interval);
		when(inherited1.getInterval()).thenReturn(interval);
		when(inherited2.getInterval()).thenReturn(otherInterval);

		fixture = new Link(association1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getFinished(), containsInAnyOrder(association1));
		assertThat(fixture.getOngoing(), contains(inherited2));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				containsInAnyOrder(association1, inherited2));
	}

	@Test
	void newStart() {
		when(association1.getType()).thenReturn(PointType.START);

		fixture = new Link(association1, null);

		assertThat(fixture.getOngoing(), containsInAnyOrder(association1));
		assertThat(fixture.getFinished(), is(nullValue()));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				contains(association1));
	}

	@Test
	void newStartWithInheritance() {
		when(association1.getType()).thenReturn(PointType.START);

		fixture = new Link(association1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getOngoing(), containsInAnyOrder(association1, inherited1, inherited2));
		assertThat(fixture.getFinished(), is(nullValue()));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				containsInAnyOrder(association1, inherited1, inherited2));
	}

	@Test
	void addStartAssociation() {
		when(association1.getType()).thenReturn(PointType.START);
		when(association2.getType()).thenReturn(PointType.START);

		fixture = new Link(association1, null);
		fixture.addAssociation(association2);

		assertThat(fixture.getOngoing(), hasItem(association2));
	}

	@Test
	void addNonStartAssociation() {
		when(association1.getType()).thenReturn(PointType.START);
		when(association2.getType()).thenReturn(PointType.INSTANT);
		when(association3.getType()).thenReturn(PointType.END);

		fixture = new Link(association1, null);
		fixture.addAssociation(association2);
		fixture.addAssociation(association3);

		assertThat(fixture.getFinished(), hasItems(association2, association3));
	}

}
