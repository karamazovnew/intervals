package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class PointImpl implements Point {

	private Comparable<?> value;
	private Collection<IntervalAssociation> associations;

	public PointImpl(Comparable<?> value) throws IllegalArgumentException {
		this.value = value;
		associations = new HashSet<>();
	}

	@Override
	public Comparable<?> getValue() {
		return value;
	}

	@Override
	public Collection<IntervalAssociation> getAssociations() {
		return Collections.unmodifiableCollection(associations);
	}

	@Override
	public synchronized boolean addAssociation(IntervalAssociation association) {
		return associations.add(association);
	}

	@Override
	public String toString() {
		return "value=" + value;
	}

	@Override
	public int compareTo(Point otherPoint) throws ClassCastException {
		int result = compareValues(otherPoint.getValue());
		return result;
	}

	@SuppressWarnings("unchecked")
	private <T> int compareValues(Comparable<?> o) {
		return ((Comparable<T>) value).compareTo((T) o);
	}

}
