package com.vladsoft.intervals.domain.imp;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class IntegerPoint implements Point<Integer> {

	private Integer value;
	private Collection<IntervalAssociation> associations;

	public IntegerPoint(Integer value) {
		this.value = value;
		associations = new HashSet<>();
	}

	@Override
	public Integer getValue() {
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

}
