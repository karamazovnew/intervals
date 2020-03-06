package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.vladsoft.intervals.domain.PointType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class LinkTest<T extends Comparable<T>> {

	private Link<T> fixture;

	@Mock
	private Interval<T> interval1, interval2, interval3;

	@Mock
	private Interval<T> inherited1, inherited2;

	@Test
	void newInstant() {
		fixture = new Link<>(INSTANT, interval1, null);

		assertThat(fixture.getFinished(), containsInAnyOrder(interval1));
		assertThat(fixture.getOngoing(), is(nullValue()));
		assertThat(fixture.getAllIntervals(), contains(interval1));
	}

	@Test
	void newInstantWithInheritance() {
		fixture = new Link<>(INSTANT, interval1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getFinished(), containsInAnyOrder(interval1));
		assertThat(fixture.getOngoing(), containsInAnyOrder(inherited1, inherited2));
		assertThat(fixture.getAllIntervals(), containsInAnyOrder(interval1, inherited1, inherited2));
	}

	@Test
	void newEnd() {
		fixture = new Link<>(END, interval1, null);

		assertThat(fixture.getFinished(), containsInAnyOrder(interval1));
		assertThat(fixture.getOngoing(), is(nullValue()));
		assertThat(fixture.getAllIntervals(), contains(interval1));
	}

	@Test
	void newEndWithInheritance() {
		fixture = new Link<>(END, interval1, Arrays.asList(interval1, inherited2));

		assertThat(fixture.getFinished(), containsInAnyOrder(interval1));
		assertThat(fixture.getOngoing(), contains(inherited2));
		assertThat(fixture.getAllIntervals(), containsInAnyOrder(interval1, inherited2));
	}

	@Test
	void newStart() {
		fixture = new Link<>(START, interval1, null);

		assertThat(fixture.getOngoing(), containsInAnyOrder(interval1));
		assertThat(fixture.getFinished(), is(nullValue()));
		assertThat(fixture.getAllIntervals(), contains(interval1));
	}

	@Test
	void newStartWithInheritance() {
		fixture = new Link<>(START, interval1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getOngoing(), containsInAnyOrder(interval1, inherited1, inherited2));
		assertThat(fixture.getFinished(), is(nullValue()));
		assertThat(fixture.getAllIntervals(), containsInAnyOrder(interval1, inherited1, inherited2));
	}

	@Test
	void addStartAssociation() {
		fixture = new Link<>(START, interval1, null);
		fixture.addAssociation(START, interval2);

		assertThat(fixture.getOngoing(), hasItem(interval2));
	}

	@Test
	void addNonStartAssociation() {
		fixture = new Link<>(START, interval1, null);
		fixture.addAssociation(INSTANT, interval2);
		fixture.addAssociation(END, interval3);

		assertThat(fixture.getFinished(), hasItems(interval2, interval3));
	}

}
