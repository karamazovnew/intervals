package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.PointType;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Link {

	private Collection<IntervalAssociation> associations;
	private Collection<IntervalAssociation> inherited;
	private Comparable<?> invalidatedBy;


	protected Link(Collection<IntervalAssociation> associations, Collection<IntervalAssociation> inherited) {
		this.associations = new HashSet<>(associations);
		this.inherited = new HashSet<>(inherited);
		this.invalidatedBy = null;
	}

	protected Collection<IntervalAssociation> getAssociations() {
		return associations;
	}

	protected Stream<IntervalAssociation> getAllAssociations() {
		return Stream.concat(associations.stream(), inherited.stream());
	}

	protected Collection<IntervalAssociation> getInheritance() {
		return Stream.concat(associations.stream().filter(a -> a.getType().equals(PointType.START)),
				inherited.stream())
				.collect(Collectors.toList());
	}

	protected void addAssociations(Collection<IntervalAssociation> associations) {
		this.associations.addAll(associations);
	}

	public Comparable<?> getInvalidatedBy() {
		return invalidatedBy;
	}

	public void setInvalidatedBy(Comparable<?> invalidatedBy) {
		this.invalidatedBy = invalidatedBy;
	}

}
