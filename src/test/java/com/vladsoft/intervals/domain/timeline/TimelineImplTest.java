package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.Timeline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.vladsoft.intervals.domain.PointType.END;
import static com.vladsoft.intervals.domain.PointType.START;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimelineImplTest {

	private Timeline<Integer> fixture;

	@Mock
	private Interval<Integer> intervalA, intervalB;

	@Mock
	private Point<Integer> startA, endA, startB, endB;

	@BeforeEach
	void setUp() {
		lenient().when(intervalA.getStartPoint()).thenReturn(startA);
		lenient().when(intervalA.getEndPoint()).thenReturn(endA);
		lenient().when(intervalB.getStartPoint()).thenReturn(startB);
		lenient().when(intervalB.getEndPoint()).thenReturn(endB);
		lenient().when(startA.getInterval()).thenReturn(intervalA);
		lenient().when(endA.getInterval()).thenReturn(intervalA);
		lenient().when(startB.getInterval()).thenReturn(intervalB);
		lenient().when(endB.getInterval()).thenReturn(intervalB);
		fixture = new TimelineImpl<>();
	}

	@Test
	void addSimpleInterval() {
		when(startA.getValue()).thenReturn(2);
		when(endA.getValue()).thenReturn(4);
		when(startA.getType()).thenReturn(START);
		lenient().when(endA.getType()).thenReturn(END);

		fixture.addInterval(intervalA);

		assertThat(fixture.getIntervals(1), empty());
		assertThat(fixture.getIntervals(2), contains(intervalA));
		assertThat(fixture.getIntervals(3), contains(intervalA));
		assertThat(fixture.getIntervals(4), empty());
		assertThat(fixture.getIntervals(5), empty());
	}

	@Test
	void addTwoIntervals() {
		when(startA.getValue()).thenReturn(3);
		when(endA.getValue()).thenReturn(5);
		when(startA.getType()).thenReturn(START);
		lenient().when(endA.getType()).thenReturn(END);
		when(startB.getValue()).thenReturn(2);
		when(endB.getValue()).thenReturn(6);
		lenient().when(startB.getType()).thenReturn(START);
		lenient().when(endB.getType()).thenReturn(END);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		assertThat(fixture.getIntervals(1), empty());
		assertThat(fixture.getIntervals(2), contains(intervalB));
		assertThat(fixture.getIntervals(3), containsInAnyOrder(intervalA, intervalB));
		assertThat(fixture.getIntervals(4), containsInAnyOrder(intervalA, intervalB));
		assertThat(fixture.getIntervals(5), contains(intervalB));
		assertThat(fixture.getIntervals(6), empty());
		assertThat(fixture.getIntervals(7), empty());
	}

}
