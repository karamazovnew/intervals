package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.PointType;
import org.junit.jupiter.api.BeforeEach;
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
	private Interval interval;

	@BeforeEach
	void setUp() {
		fixture = new Link(Arrays.asList(association1, association2),
				Arrays.asList(inherited1, inherited2));
	}

	@Test
	void getAssociations() {
		assertThat(fixture.getAssociations(),
				containsInAnyOrder(association1, association2));
	}

	@Test
	void getInheritanceIgnoreEnds() {
		when(association1.getType()).thenReturn(PointType.START);
		when(association2.getType()).thenReturn(PointType.END);

		assertThat(fixture.getInheritance(),
				containsInAnyOrder(association1, inherited1, inherited2));
	}

	@Test
	void getInheritanceIgnoreInstants() {
		when(association1.getType()).thenReturn(PointType.INSTANT);
		when(association2.getType()).thenReturn(PointType.INSTANT);

		assertThat(fixture.getInheritance(),
				containsInAnyOrder(inherited1, inherited2));
	}

	@Test
	void getAllAssociations() {
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				containsInAnyOrder(association1, association2, inherited1, inherited2));
	}

	@Test
	void addAssociation() {
		fixture.addAssociation(association3);

		assertThat(fixture.getAssociations(), hasItem(association3));
	}

	@Test
	void addInheritance() {
		when(association1.getType()).thenReturn(PointType.START); //value not important, prevents NPE during test
		when(association2.getType()).thenReturn(PointType.START); //value not important, prevents NPE during test

		fixture.addInheritance(inherited3);

		assertThat(fixture.getInheritance(), hasItem(inherited3));
	}

	@Test
	void endPointFiltersKnownInterval() {
		when(association1.getType()).thenReturn(PointType.END);
		when(association1.getInterval()).thenReturn(interval);
		when(inherited2.getInterval()).thenReturn(interval);

		fixture = new Link(association1, Arrays.asList(inherited1, inherited2));

		assertThat(fixture.getInheritance(), contains(inherited1));
		assertThat(fixture.getAllAssociations().collect(Collectors.toList()),
				containsInAnyOrder(association1, inherited1));
	}

}