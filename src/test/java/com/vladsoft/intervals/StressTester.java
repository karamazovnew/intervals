package com.vladsoft.intervals;

import com.vladsoft.intervals.domain.Timeline;
import com.vladsoft.intervals.domain.timeline.TimelineImpl;

public class StressTester {

	private static final int nrOfIntervals = 2000000;
	private static final long seed = 4323;
	private static final int valuesSpread = 3650;
	private static final int maxLengthPercentage = 5;
	private static final int logAtIntervals = 10000;

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
		for (int i = 0; i < nrOfIntervals; i++) {
			timeline.addInterval(generator.getIntervals().get(i));
			if (logAtIntervals > 0 && (i + 1) % logAtIntervals == 0) {
				System.out.format("[%d-%d] %dms avg:%fms%n",
						i - logAtIntervals + 2,
						i + 1,
						System.currentTimeMillis() - t,
						(double) (System.currentTimeMillis() - t) / logAtIntervals);
				t = System.currentTimeMillis();
			}
		}
		System.out.println("\n======\nTotal Time :" + (double) (System.currentTimeMillis() - time) / 1000 + "sec");
	}

	public void getIntervals() {
		time = System.currentTimeMillis();
		int intervalsNumber = timeline.getIntervals(-1, valuesSpread + 1).size();
		System.out.println("Found " + intervalsNumber + " intervals in " + (System.currentTimeMillis() - time) + "ms");

	}

}
