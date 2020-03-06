package com.vladsoft.intervals.uat;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Timeline;
import com.vladsoft.intervals.domain.imp.IntervalImpl;
import com.vladsoft.intervals.domain.imp.PointImpl;
import com.vladsoft.intervals.domain.timeline.TimelineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TimelineTest {

	private Interval a, b, c, d, e, f, i1, i2;

	private Timeline timeline;

	@BeforeEach
	public void init() {
//		-5	-2	0	1	5	7	9	11	13	14	15
//					a-------a
//				b-----------b
//								c---c
//						d-------d
//											 e---e
//		f---f
//					i1					i2

		a = new IntervalImpl(new PointImpl(1), new PointImpl(7));
		b = new IntervalImpl(new PointImpl(0), new PointImpl(7));
		c = new IntervalImpl(new PointImpl(9), new PointImpl(11));
		d = new IntervalImpl(new PointImpl(5), new PointImpl(9));
		e = new IntervalImpl(new PointImpl(14), new PointImpl(15));
		f = new IntervalImpl(new PointImpl(-5), new PointImpl(-2));
		i1 = new IntervalImpl(new PointImpl(1), new PointImpl(1));
		i2 = new IntervalImpl(new PointImpl(13), new PointImpl(13));

		timeline = new TimelineImpl();

		timeline.addInterval(a);
		timeline.addInterval(b);
		timeline.addInterval(c);
		timeline.addInterval(d);
		timeline.addInterval(e);
		timeline.addInterval(f);
		timeline.addInterval(i1);
		timeline.addInterval(i2);
	}

	@Test
	public void testGetIntervalsAtPoint() {
		assertThat(timeline.getIntervals(-6), empty());
		assertThat(timeline.getIntervals(-5), contains(f));
		assertThat(timeline.getIntervals(-4), contains(f));
		assertThat(timeline.getIntervals(-3), contains(f));
		assertThat(timeline.getIntervals(-2), contains(f));
		assertThat(timeline.getIntervals(-1), empty());
		assertThat(timeline.getIntervals(0), contains(b));
		assertThat(timeline.getIntervals(1), containsInAnyOrder(a, b, i1));
		assertThat(timeline.getIntervals(2), containsInAnyOrder(a, b));
		assertThat(timeline.getIntervals(3), containsInAnyOrder(a, b));
		assertThat(timeline.getIntervals(4), containsInAnyOrder(a, b));
		assertThat(timeline.getIntervals(5), containsInAnyOrder(a, b, d));
		assertThat(timeline.getIntervals(6), containsInAnyOrder(a, b, d));
		assertThat(timeline.getIntervals(7), containsInAnyOrder(a, b, d));
		assertThat(timeline.getIntervals(8), contains(d));
		assertThat(timeline.getIntervals(9), containsInAnyOrder(c, d));
		assertThat(timeline.getIntervals(10), contains(c));
		assertThat(timeline.getIntervals(11), contains(c));
		assertThat(timeline.getIntervals(12), empty());
		assertThat(timeline.getIntervals(13), contains(i2));
		assertThat(timeline.getIntervals(14), contains(e));
		assertThat(timeline.getIntervals(15), contains(e));
		assertThat(timeline.getIntervals(16), empty());
	}

}
