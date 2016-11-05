package utils;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class StatisticsTest {

    private List<Double> vals;

    @Before
    public void setUp() throws Exception {
        vals = ImmutableList.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
    }

    @Test
    public void testMean() {
        assertEquals(3.5, Statistics.mean(vals), 1e-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMeanNull() {
        Statistics.mean(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMeanEmptyList() {
        Statistics.mean(Collections.emptyList());
    }

    @Test
    public void testVariance() {
        assertEquals(2.9166666666667, Statistics.variance(vals), 1e-6);
    }

    @Test
    public void testStdDev() {
        assertEquals(1.707825127669692, Statistics.stdDev(vals), 1e-6);
    }

    @Test
    public void testIsOutlier() {
        assertTrue(Statistics.isOutlier(vals, 9.0));
        assertFalse(Statistics.isOutlier(vals, 8.0));
    }

}