package com.vladsoft.intervals;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.Timeline;
import com.vladsoft.intervals.domain.timeline.TimelineImpl;

public class StressTester {

	private static final int nrOfIntervals = 1000000;
	private static final long seed = 4323;
	private static final int valuesSpread = 80000;
	private static final int maxLengthPercentage = 50;
	private static final int logAtIntervals = 1000;
	private static final int limitOverlaps = 500;

	private long time;
	private Timeline<Integer> timeline;
	private IntervalGenerator generator;

	public StressTester() {
		timeline = new TimelineImpl<>();
		generator = new IntervalGenerator(nrOfIntervals, seed, valuesSpread, maxLengthPercentage, 1);
	}

	public void insertIntervals() {
		time = System.currentTimeMillis();
		long t = time;
		boolean valid;
		int size = 0;
		for (int i = 0; i < nrOfIntervals; i++) {
			valid = true;
			Interval<Integer> interval = generator.getIntervals().get(i);
			if (limitOverlaps > 0 &&
					timeline.getMaxOverlaps(interval.getStartPoint().getValue(),
							interval.getEndPoint().getValue()) >= limitOverlaps)
				valid = false;
			if (valid) {
				timeline.addInterval(interval);
				size = timeline.getIntervalsNumber();
				if (logAtIntervals > 0 && size % logAtIntervals == 0) {
					System.out.format("[%d-%d] %dms avg:%fms%n",
							size - logAtIntervals + 1,
							size,
							System.currentTimeMillis() - t,
							(double) (System.currentTimeMillis() - t) / logAtIntervals);
					t = System.currentTimeMillis();
				}
			}
		}
		System.out.println("\n======\nTotal Time: " + (double) (System.currentTimeMillis() - time) / 1000 + "sec");
	}

	public void getIntervals() {
		time = System.currentTimeMillis();
		int intervalsNumber = timeline.getIntervals(-1, valuesSpread + 1).size();
		System.out.println("Found " + intervalsNumber + " intervals in " + (System.currentTimeMillis() - time) + "ms");
	}

	public void getMaxIntervals() {
		time = System.currentTimeMillis();
		int intervalsNumber = timeline.getMaxOverlaps(-1, valuesSpread + 1);
		System.out.println("Found " + intervalsNumber + " maximum overlapping intervals in " + (System.currentTimeMillis() - time) + "ms");
	}

}
