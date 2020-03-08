
#### Run com.vladsoft.intervals.IntervalsApplicationTests:
- Test can be controlled through the StressTester class final attributes.
- Performance is greatly impacted by these settings, especially maxLengthPercentage.
- To see more gaps, reduce number of intervals and their length, or reduce limitOverlaps to 1.
- Setting limitOverlaps to 0 will insert all intervals into the timeline. Recommend setting the number of Intervals to max of 100,000 and lower the maxLengthPercentage.

##### Scenario example: Scheduler for the next 10 years, with events scheduled at hourly intervals

	private static final int nrOfIntervals = 1000000;
	private static final int valuesSpread = 87600; // 10 years * 265days * 24 hours
	private static final int maxLengthPercentage = 5; // 6 months maximum event length
	private static final int limitOverlaps = 500; //have a maximum of 500 overlapping events

On my system this is a result
>`Total Time: 44.109sec`
>
>`Found 30075 intervals in 572ms` 
><br>The rest up to one million were breaking the overlap limit
>
>`Found 500 maximum overlapping intervals in 8ms`
><br>Confirmation that limit of overlaps was respected
>
>`Found first gap interval [87600/87610] in 17ms`
><br>There are basically no gaps, that interval is at the end of the timeline
>
>`Found 2 gaps in 16ms`
><br>I've bracketed the search around the timeline (which starts at 0) so it shows an extra [-10,0] interval
>
>`Gaps: [interval [-10/0], interval [87600/87610]]`

#### Run com.vladsoft.intervals.uat.TimelineTest:
- A unit test using a simple timeline example.
- A visual "map" of the timeline is shown as comment.

### **Planned:**
- PointType is useless and will be removed.
- Deletion of intervals from the timeline: will require a counter of how many intervals end in a link. When removing an interval, the counter will decrement. When it reaches zero, if there are no intervals left, the link will be deleted.
- Improve cosmetics of algorithms in the TimelineImpl class. 
