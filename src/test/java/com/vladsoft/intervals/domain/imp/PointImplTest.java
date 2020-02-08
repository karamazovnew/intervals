package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointImplTest<T extends Comparable<T>> {

	private Point<T> fixture;

	@Mock
	private T value, otherValue;

	@Mock
	private IntervalAssociation<T> association;

	@BeforeEach
	void setUp() {
		fixture = new PointImpl<>(value);
	}

	@Test
	void getValue() {
		assertSame(value, fixture.getValue());
	}

	@Test
	void getAssociations() {
		assertTrue(fixture.getAssociations().isEmpty());
		assertThrows(UnsupportedOperationException.class, () -> fixture.getAssociations().add(association));
		assertThrows(UnsupportedOperationException.class, () -> fixture.getAssociations().remove(association));
	}

	@Test
	void addAssociation() {
		boolean added = fixture.addAssociation(association);
		assertTrue(added);
		assertEquals(1, fixture.getAssociations().size());
		assertSame(association, fixture.getAssociations().stream().findFirst().orElse(null));
	}

	@Test
	void compareTo() {
		int someInt = new Random().nextInt();
		when(value.compareTo(otherValue)).thenReturn(someInt);
		PointImpl<T> other = new PointImpl<>(otherValue);
		assertEquals(someInt, fixture.compareTo(other));
	}

}
