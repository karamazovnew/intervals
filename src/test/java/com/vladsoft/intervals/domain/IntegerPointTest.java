package com.vladsoft.intervals.domain;

import com.vladsoft.intervals.domain.imp.IntegerPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IntegerPointTest {

	private Point fixture;
	private Integer value;

	@Mock
	private IntervalAssociation association;

	@BeforeEach
	void setUp() {
		value = Integer.MIN_VALUE;
		fixture = new IntegerPoint(value);
	}

	@Test
	void getValue() {
		assertSame(value, fixture.getValue());
	}

	@Test
	void getAssociations() {
		assertTrue(fixture.getAssociations().isEmpty());
		assertThrows(UnsupportedOperationException.class, () -> {
			fixture.getAssociations().add(association);
		});
		assertThrows(UnsupportedOperationException.class, () -> {
			fixture.getAssociations().remove(association);
		});
	}

	@Test
	void addAssociation() {
		boolean added = fixture.addAssociation(association);
		assertTrue(added);
		assertEquals(1, fixture.getAssociations().size());
		assertSame(association, fixture.getAssociations().stream().findFirst().orElse(null));
	}

}
