package com.vladsoft.intervals.uat;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Timeline;
import com.vladsoft.intervals.domain.imp.IntervalImpl;
import com.vladsoft.intervals.domain.imp.TimelineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TimelineTest {

	private Interval<Integer> a, b, c, d, e, f, g;

	private Timeline<Integer> timeline;

	@BeforeEach
	public void init() {
//		-5	-2	0	1	5	7	9	11	14	15
//					a-------]
//				b-----------]
//								c---]
//						d-------]
//										 e---]
//		f---]
		//		g-------]

		a = new IntervalImpl<>(1, 7);
		b = new IntervalImpl<>(0, 7);
		c = new IntervalImpl<>(9, 11);
		d = new IntervalImpl<>(5, 9);
		e = new IntervalImpl<>(14, 15);
		f = new IntervalImpl<>(-5, -2);
		g = new IntervalImpl<>(0, 5);

		timeline = new TimelineImpl<>();

		timeline.addInterval(a);
		timeline.addInterval(b);
		timeline.addInterval(c);
		timeline.addInterval(d);
		timeline.addInterval(e);
		timeline.addInterval(f);
		timeline.addInterval(g);
	}

	@Test
	public void testGetIntervalsAtPoint() {
		assertThat(timeline.getIntervals(-6), empty());
		assertThat(timeline.getIntervals(-5), contains(f));
		assertThat(timeline.getIntervals(-4), contains(f));
		assertThat(timeline.getIntervals(-3), contains(f));
		assertThat(timeline.getIntervals(-2), empty());
		assertThat(timeline.getIntervals(-1), empty());
		assertThat(timeline.getIntervals(0), containsInAnyOrder(b, g));
		assertThat(timeline.getIntervals(1), containsInAnyOrder(a, b, g));
		assertThat(timeline.getIntervals(2), containsInAnyOrder(a, b, g));
		assertThat(timeline.getIntervals(3), containsInAnyOrder(a, b, g));
		assertThat(timeline.getIntervals(4), containsInAnyOrder(a, b, g));
		assertThat(timeline.getIntervals(5), containsInAnyOrder(a, b, d));
		assertThat(timeline.getIntervals(6), containsInAnyOrder(a, b, d));
		assertThat(timeline.getIntervals(7), contains(d));
		assertThat(timeline.getIntervals(8), contains(d));
		assertThat(timeline.getIntervals(9), contains(c));
		assertThat(timeline.getIntervals(10), contains(c));
		assertThat(timeline.getIntervals(11), empty());
		assertThat(timeline.getIntervals(12), empty());
		assertThat(timeline.getIntervals(13), empty());
		assertThat(timeline.getIntervals(14), contains(e));
		assertThat(timeline.getIntervals(15), empty());
		assertThat(timeline.getIntervals(16), empty());

		assertThat(timeline.getIntervals(-6, 16), containsInAnyOrder(a, b, c, d, e, f, g));
		assertThat(timeline.getIntervals(2, 8), containsInAnyOrder(a, b, d, g));
		assertThat(timeline.getIntervals(11, 14), empty());
	}

	@Test
	public void testGetMaxOverlaps() {
		assertThat(timeline.getMaxOverlaps(-6, 16), is(3));
		assertThat(timeline.getMaxOverlaps(0, 1), is(2));
		assertThat(timeline.getMaxOverlaps(13, 15), is(1));
		assertThat(timeline.getMaxOverlaps(14, 15), is(1));
		assertThat(timeline.getMaxOverlaps(13, 16), is(1));
	}

	@Test
	public void testGetIntervalsNumber() {
		assertThat(timeline.getIntervalsNumber(), is(7));
	}

	@Test
	public void testGetFirstGap() {
		Interval<Integer> gap = timeline.getFirstGap(-10, 20);
		assertThat(gap.getStartPoint().getValue(), is(-10));
		assertThat(gap.getEndPoint().getValue(), is(-5));
		gap = timeline.getFirstGap(-5, 15);
		assertThat(gap.getStartPoint().getValue(), is(-2));
		assertThat(gap.getEndPoint().getValue(), is(0));
		gap = timeline.getFirstGap(1, 15);
		assertThat(gap.getStartPoint().getValue(), is(11));
		assertThat(gap.getEndPoint().getValue(), is(14));
		gap = timeline.getFirstGap(12, 20);
		assertThat(gap.getStartPoint().getValue(), is(12));
		assertThat(gap.getEndPoint().getValue(), is(14));
		gap = timeline.getFirstGap(14, 20);
		assertThat(gap.getStartPoint().getValue(), is(15));
		assertThat(gap.getEndPoint().getValue(), is(20));
	}

	@Test
	public void testGetGaps() {
		List<Interval<Integer>> gaps = timeline.getGaps(-10, 20);
		assertThat(gaps.size(), is(4));
		assertThat(gaps.get(0).getStartPoint().getValue(), is(-10));
		assertThat(gaps.get(0).getEndPoint().getValue(), is(-5));
		assertThat(gaps.get(1).getStartPoint().getValue(), is(-2));
		assertThat(gaps.get(1).getEndPoint().getValue(), is(-0));
		assertThat(gaps.get(2).getStartPoint().getValue(), is(11));
		assertThat(gaps.get(2).getEndPoint().getValue(), is(14));
		assertThat(gaps.get(3).getStartPoint().getValue(), is(15));
		assertThat(gaps.get(3).getEndPoint().getValue(), is(20));

		gaps = timeline.getGaps(-5, 1);
		assertThat(gaps.size(), is(1));
		assertThat(gaps.get(0).getStartPoint().getValue(), is(-2));
		assertThat(gaps.get(0).getEndPoint().getValue(), is(0));

		gaps = timeline.getGaps(-1, 14);
		assertThat(gaps.size(), is(2));
		assertThat(gaps.get(0).getStartPoint().getValue(), is(-1));
		assertThat(gaps.get(0).getEndPoint().getValue(), is(-0));
		assertThat(gaps.get(1).getStartPoint().getValue(), is(11));
		assertThat(gaps.get(1).getEndPoint().getValue(), is(14));

		gaps = timeline.getGaps(10, 16);
		assertThat(gaps.size(), is(2));
		assertThat(gaps.get(0).getStartPoint().getValue(), is(11));
		assertThat(gaps.get(0).getEndPoint().getValue(), is(14));
		assertThat(gaps.get(1).getStartPoint().getValue(), is(15));
		assertThat(gaps.get(1).getEndPoint().getValue(), is(16));
	}

}
