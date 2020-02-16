package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.PointType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Link {

	private Collection<IntervalAssociation> associations;
	private Collection<IntervalAssociation> inherited;

	//used only for instant interval points
	protected Link(Collection<IntervalAssociation> associations, Collection<IntervalAssociation> inheritance) {
		this.associations = new ArrayList<>(associations);
		if (inheritance != null)
			inherited = new ArrayList<>(inheritance);
		else
			inherited = new ArrayList<>();
	}

	protected Link(IntervalAssociation association, Collection<IntervalAssociation> inheritance) {
		associations = new ArrayList<>(Collections.singletonList(association));
		if (inheritance == null)
			inherited = new ArrayList<>();
		else if (association.getType().equals(PointType.END))
			inherited = inheritance.stream()
					.filter(i -> !association.getInterval().equals(i.getInterval())).collect(Collectors.toList());
		else
			inherited = new ArrayList<>(inheritance);

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

	protected void addAssociation(IntervalAssociation association) {
		associations.add(association);
	}

	protected void addInheritance(IntervalAssociation inheritance) {
		inherited.add(inheritance);
	}

}
