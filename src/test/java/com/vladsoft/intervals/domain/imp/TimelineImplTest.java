package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Point;
import com.vladsoft.intervals.domain.Timeline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

		assertThat(fixture.getIntervalsNumber(), is(0));

		fixture.addInterval(intervalA);

		assertThat(fixture.getIntervals(1), empty());
		assertThat(fixture.getIntervals(2), contains(intervalA));
		assertThat(fixture.getIntervals(3), contains(intervalA));
		assertThat(fixture.getIntervals(4), empty());
		assertThat(fixture.getIntervals(5), empty());

		assertThat(fixture.getIntervals(2, 4), contains(intervalA));
		assertThat(fixture.getIntervals(3, 5), contains(intervalA));
		assertThat(fixture.getIntervals(1, 2), empty());
		assertThat(fixture.getIntervals(1, 5), contains(intervalA));

		assertThat(fixture.getIntervalsNumber(), is(1));

		Interval<Integer> gap = fixture.getFirstGap(0, 2);
		assertThat(gap.getStartPoint().getValue(), is(0));
		assertThat(gap.getEndPoint().getValue(), is(2));
		gap = fixture.getFirstGap(0, 9);
		assertThat(gap.getStartPoint().getValue(), is(0));
		assertThat(gap.getEndPoint().getValue(), is(2));
		assertThat(fixture.getFirstGap(3, 4), is(nullValue()));
		gap = fixture.getFirstGap(3, 9);
		assertThat(gap.getStartPoint().getValue(), is(4));
		assertThat(gap.getEndPoint().getValue(), is(9));
		gap = fixture.getFirstGap(4, 9);
		assertThat(gap.getStartPoint().getValue(), is(4));
		assertThat(gap.getEndPoint().getValue(), is(9));
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

		assertThat(fixture.getIntervals(2, 3), contains(intervalB));
		assertThat(fixture.getIntervals(1, 7), containsInAnyOrder(intervalA, intervalB));
		assertThat(fixture.getIntervals(2, 5), containsInAnyOrder(intervalA, intervalB));
		assertThat(fixture.getIntervals(5, 9), contains(intervalB));
		assertThat(fixture.getIntervals(6, 9), empty());

		assertThat(fixture.getMaxOverlaps(1, 7), is(2));
		assertThat(fixture.getMaxOverlaps(5, 6), is(1));

		assertThat(fixture.getIntervalsNumber(), is(2));
	}

	@Test
	void addTwoSeparateIntervals() {
		when(startA.getValue()).thenReturn(1);
		when(endA.getValue()).thenReturn(3);
		when(startA.getType()).thenReturn(START);
		lenient().when(endA.getType()).thenReturn(END);
		when(startB.getValue()).thenReturn(6);
		when(endB.getValue()).thenReturn(9);
		lenient().when(startB.getType()).thenReturn(START);
		lenient().when(endB.getType()).thenReturn(END);

		fixture.addInterval(intervalA);
		fixture.addInterval(intervalB);

		Interval<Integer> gap = fixture.getFirstGap(0, 1);
		assertThat(gap.getStartPoint().getValue(), is(0));
		assertThat(gap.getEndPoint().getValue(), is(1));
		gap = fixture.getFirstGap(0, 9);
		assertThat(gap.getStartPoint().getValue(), is(0));
		assertThat(gap.getEndPoint().getValue(), is(1));
		assertThat(fixture.getFirstGap(1, 3), is(nullValue()));
		assertThat(fixture.getFirstGap(6, 9), is(nullValue()));
		gap = fixture.getFirstGap(9, 10);
		assertThat(gap.getStartPoint().getValue(), is(9));
		assertThat(gap.getEndPoint().getValue(), is(10));
		gap = fixture.getFirstGap(1, 7);
		assertThat(gap.getStartPoint().getValue(), is(3));
		assertThat(gap.getEndPoint().getValue(), is(6));
		gap = fixture.getFirstGap(6, 10);
		assertThat(gap.getStartPoint().getValue(), is(9));
		assertThat(gap.getEndPoint().getValue(), is(10));
		gap = fixture.getFirstGap(4, 9);
		assertThat(gap.getStartPoint().getValue(), is(4));
		assertThat(gap.getEndPoint().getValue(), is(6));

		List<Interval<Integer>> gaps = fixture.getGaps(0, 10);
		assertThat(gaps.size(), is(4));
		assertThat(gaps.get(0).getStartPoint().getValue(), is(0));
		assertThat(gaps.get(0).getEndPoint().getValue(), is(1));
		assertThat(gaps.get(1).getStartPoint().getValue(), is(3));
		assertThat(gaps.get(1).getEndPoint().getValue(), is(6));
		assertThat(gaps.get(2).getStartPoint().getValue(), is(9));
		assertThat(gaps.get(2).getEndPoint().getValue(), is(10));
	}

	@Test
	void getIntervalsBetweenPointsWhenTimelineEmpty() {
		assertThat(fixture.getIntervals(1, 7), is(empty()));
	}

	@Test
	void getMaxIntervalsWhenTimelineEmpty() {
		assertThat(fixture.getMaxOverlaps(1, 7), is(0));
	}

	@Test
	void getGapsWhenTimelineEmpty() {
		Interval<Integer> gap = fixture.getFirstGap(0, 1);
		assertThat(gap.getStartPoint().getValue(), is(0));
		assertThat(gap.getEndPoint().getValue(), is(1));

		List<Interval<Integer>> gaps = fixture.getGaps(0, 1);
		assertThat(gaps.size(), is(1));
		assertThat(gaps.get(0).getStartPoint().getValue(), is(0));
		assertThat(gaps.get(0).getEndPoint().getValue(), is(1));
	}

}
