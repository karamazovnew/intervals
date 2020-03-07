package com.vladsoft.intervals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntervalsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void runStressTest() {
		StressTester test = new StressTester();
		test.insertIntervals();
		test.getIntervals();
		test.getMaxIntervals();
	}

}
