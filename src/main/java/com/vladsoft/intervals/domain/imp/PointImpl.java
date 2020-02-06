package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class PointImpl<T extends Comparable<T>> implements Point<T> {

	private T value;
	private Collection<IntervalAssociation> associations;

	public PointImpl(T value) {
		this.value = value;
		associations = new HashSet<>();
	}

	@Override
	public T getValue() {
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
	public int compareTo(Point<T> o) {
		return value.compareTo(o.getValue());
	}

}
