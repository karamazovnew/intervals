package com.vladsoft.intervals;

import com.vladsoft.intervals.domain.Interval;
import com.vladsoft.intervals.domain.imp.IntervalImpl;

import java.util.*;

public class IntervalGenerator {

	List<Interval<Integer>> intervals;
	Map<Integer, List<Integer>> splits;

	Random startGenerator, endGenerator;

	public IntervalGenerator(int nrOfIntervals, long seed, int valuesSpread, int maxLengthPercentage, int threads) {
		intervals = new ArrayList<>(nrOfIntervals);
		startGenerator = new Random(seed);
		endGenerator = new Random(seed + 10);
		for (int i = 0; i < nrOfIntervals; i++) {
			int start = startGenerator.nextInt(valuesSpread);
			int end = start + 1 + endGenerator.nextInt((valuesSpread * maxLengthPercentage) / 100);
			end = end < valuesSpread ? end : valuesSpread;
			intervals.add(new IntervalImpl<>(start, end));
		}
		int chunkSize = nrOfIntervals / threads;
		splits = new HashMap<>();
		int cursor = 0;
		for (int t = 0; t < threads - 1; t++) {
			splits.put(t, Arrays.asList(cursor, cursor + chunkSize - 1));
			cursor += chunkSize;
		}
		splits.put(threads - 1, Arrays.asList(cursor, nrOfIntervals - 1));
	}

	public List<Interval<Integer>> getIntervals() {
		return intervals;
	}

	public List<Interval<Integer>> getSplit(int nr) {
		return intervals.subList(splits.get(nr).get(0), splits.get(nr).get(1));

	}

}
