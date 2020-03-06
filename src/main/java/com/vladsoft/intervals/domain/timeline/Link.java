package com.vladsoft.intervals.domain.timeline;

import com.vladsoft.intervals.domain.IntervalAssociation;
import com.vladsoft.intervals.domain.PointType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Link {

	private Collection<IntervalAssociation> finished;
	private Collection<IntervalAssociation> ongoing;

	protected Link(IntervalAssociation association, Collection<IntervalAssociation> inherited) {
		PointType type = association.getType();
		switch (type) {
			case START:
				ongoing = inherited == null ? new ArrayList<>() : new ArrayList<>(inherited);
				ongoing.add(association);
				break;
			case INSTANT:
				finished = new ArrayList<>(Collections.singletonList(association));
				ongoing = inherited == null ? null : new ArrayList<>(inherited);
				break;
			case END:
				finished = new ArrayList<>(Collections.singletonList(association));
				ongoing = inherited == null ? null : inherited.stream()
						.filter(i -> !association.getInterval().equals(i.getInterval())).collect(Collectors.toList());
		}
	}

	protected Collection<IntervalAssociation> getFinished() {
		return finished;
	}

	protected Stream<IntervalAssociation> getAllAssociations() {
		return Stream.concat(finished == null ? Stream.empty() : finished.stream(), ongoing == null ? Stream.empty() : ongoing.stream());
	}

	protected Collection<IntervalAssociation> getOngoing() {
		return ongoing;
	}

	protected void addAssociation(IntervalAssociation association) {
		if (!association.getType().equals(PointType.START)) {
			if (finished == null) {
				finished = new ArrayList<>();
			}
			finished.add(association);
		} else {
			if (ongoing == null)
				ongoing = new ArrayList<>();
			ongoing.add(association);
		}
	}

}
