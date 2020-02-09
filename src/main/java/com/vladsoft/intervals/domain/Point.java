package com.vladsoft.intervals.domain;

import java.util.Collection;

public interface Point extends Comparable<Point> {

	Comparable<?> getValue();

	Collection<IntervalAssociation> getAssociations();

	boolean addAssociation(IntervalAssociation association);

}
