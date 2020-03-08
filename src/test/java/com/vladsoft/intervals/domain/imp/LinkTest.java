package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class LinkTest<T extends Comparable<T>> {

	private Link<T> fixture;

	@Mock
	private Interval<T> interval1, interval2;

	@Mock
	private Interval<T> inherited1, inherited2;

	@Test
	void newEnd() {
		fixture = new Link<>( null);

		assertThat(fixture.getIntervals(), is(empty()));
	}

	@Test
	void newEndWithInheritance() {
		fixture = new Link<>(Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getIntervals(), containsInAnyOrder(inherited1, inherited2));
	}

	@Test
	void newStart() {
		fixture = new Link<>(interval1, null);

		assertThat(fixture.getIntervals(), containsInAnyOrder(interval1));
	}

	@Test
	void newStartWithInheritance() {
		fixture = new Link<>(interval1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getIntervals(), containsInAnyOrder(interval1, inherited1, inherited2));
	}

	@Test
	void addInterval() {
		fixture = new Link<>(interval1, null);
		fixture.addInterval(interval2);

		assertThat(fixture.getIntervals(), hasItem(interval2));
	}

}
