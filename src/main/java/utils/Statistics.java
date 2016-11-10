package utils;

import java.util.List;

/**
 * Utility class to perform common statistical operations. <br/>
 * Can also be substituted by <a href="http://commons.apache.org/proper/commons-math/">Apache Commons Math</a>.
 */
public class Statistics {

    /**
     * Calculates the <b>mean</b> of a collection of values.
     *
     * @param vals list of {@code double} values.
     * @return single {@code double} value representing the <b>mean</b> of {@code vals}.
     */
    public static double mean(List<Double> vals) {
        if (vals == null || vals.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one value passed");
        }

        return vals.parallelStream()
                .mapToDouble(value -> value)
                .average()
                .getAsDouble();
    }

    /**
     * Calculates the <b>variance</b> of a collection of values.
     *
     * @param vals list of {@code double} values.
     * @return single {@code double} value representing the <b>variance</b> of {@code vals}.
     */
    public static double variance(List<Double> vals) {
        double mu = mean(vals);

        return vals.parallelStream()
                .mapToDouble(value -> (value - mu) * (value - mu))
                .average()
                .getAsDouble();
    }

    /**
     * Calculates the <b>standard deviation</b> of a collection of values.
     *
     * @param vals list of {@code double} values.
     * @return single {@code double} value representing the <b>standard deviation</b> of {@code vals}.
     */
    public static double stdDev(List<Double> vals) {
        return Math.sqrt(variance(vals));
    }

    /**
     * Applies the <b>Z-score method</b> to check whether {@code value} is an outlier for the distribution given by {@code vals}.
     *
     * @param vals  list of double values that are a representative of the historical distribution of values.
     * @param value value to be tested as an outlier for the given distribution.
     * @return {@code boolean} value indicating whether {@code value} is an outlier or not for the given distribution.
     */
    public static boolean isOutlier(List<Double> vals, double value) {
        double mu = mean(vals);
        double sigma = stdDev(vals);
        double zScore = (value - mu) / sigma;
        return zScore < -3 || zScore > 3;
    }

}
