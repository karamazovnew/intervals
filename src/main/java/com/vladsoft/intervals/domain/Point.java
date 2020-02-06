package com.vladsoft.intervals.domain;

import java.util.Collection;

public interface Point<T> extends Comparable<Point<T>> {

	T getValue();

	Collection<IntervalAssociation> getAssociations();

	boolean addAssociation(IntervalAssociation association);

}
