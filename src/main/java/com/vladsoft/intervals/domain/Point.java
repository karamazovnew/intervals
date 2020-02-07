package com.vladsoft.intervals.domain;

import java.util.Collection;

public interface Point<T> extends Comparable<Point<T>> {

	T getValue();

	Collection<IntervalAssociation<T>> getAssociations();

	boolean addAssociation(IntervalAssociation<T> association);

}
