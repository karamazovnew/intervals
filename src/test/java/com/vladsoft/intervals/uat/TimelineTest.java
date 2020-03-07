package com.vladsoft.intervals.uat;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Timeline;
import com.vladsoft.intervals.domain.imp.IntervalImpl;
import com.vladsoft.intervals.domain.timeline.TimelineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TimelineTest {

	private Interval<Integer> a, b, c, d, e, f, g;

	private Timeline<Integer> timeline;

	@BeforeEach
	public void init() {
//		-5	-2	0	1	5	7	9	11	13	14	15
//					a-------]
//				b-----------]
//								c---]
//						d-------]
//											 e---]
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
	}

}
