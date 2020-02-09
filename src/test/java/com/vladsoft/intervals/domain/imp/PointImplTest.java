package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PointImplTest<T> {

	private Point fixture;

	private Integer value;

	@Mock
	private IntervalAssociation association;

	@BeforeEach
	void setUp() {
		value = 1;
		fixture = new PointImpl(value);
	}

	@Test
	void testLegalValues() {
		assertDoesNotThrow(() -> {
			new PointImpl("someString");
			new PointImpl(1L);
			new PointImpl(new Date());
			Object validObject = new Comparable<T>() {
				@Override
				public int compareTo(T o) {
					return 0;
				}
			};
			new PointImpl((Comparable<?>) validObject);
		});
	}

	@Test
	void getValue() {
		assertThat(fixture.getValue(), is(value));
	}

	@Test
	void getAssociations() {
		assertThat(fixture.getAssociations(), is(Matchers.empty()));
		assertThrows(UnsupportedOperationException.class, () -> fixture.getAssociations().add(association));
		assertThrows(UnsupportedOperationException.class, () -> fixture.getAssociations().remove(association));
	}

	@Test
	void addAssociation() {
		boolean added = fixture.addAssociation(association);
		assertThat(added, is(true));
		assertThat(fixture.getAssociations(), hasSize(1));
		assertThat(association, is(fixture.getAssociations().stream().findFirst().orElse(null)));
	}

	@Test
	void compareTo() {
		Point bigger = new PointImpl(5);
		Point eq = new PointImpl(1);
		assertThat(fixture.compareTo(bigger), Matchers.lessThan(0));
		assertThat(bigger.compareTo(fixture), Matchers.greaterThan(0));
		assertThat(fixture.compareTo(eq), is(0));
	}

	@Test
	void compareToFail() {
		assertThrows(ClassCastException.class, () -> {
			fixture.compareTo(new PointImpl("foo"));
		}, "Cannot compare incompatible types");
	}

}
