package org.ltrails.proc

import kotlin.math.pow
import kotlin.math.sqrt

class StatProcessor {

    fun mean(list: List<Double>): Double = list.sum() / list.size

    /**
     * Find the percentile that corresponds to a particular data
     * value x giving the whole list to which it refers.
     * @param list
     * @param x
     * @return
     */
    fun findPercentile(list: List<Double>, x: Double): Int = list.sorted().indexOf(x) / list.size * 100

    /**
     * Returns true when the z score is either over or under the boundary
     * to be considered extreme
     * @param z
     * @return boolean
     */
    fun isZScoreExtreme(z: Double, boundary: Int = 2): Boolean = z < -boundary || z > boundary

    /**
     * Number of std that a given value x is above or below the mean.
     * @param x target value
     * @param m mean
     * @param std standard deviation
     * @return number of std the value is distant from the mean
     */
    fun zScore(x: Double, m: Double, std: Double): Double = (x - m) / std


    /**
     * Deviation of values from the mean
     * @param list
     * @param isPopulation if
     * @return
     */
    fun std(list: List<Double>, isPopulation: Boolean): Double {
        val mean = mean(list)
        return sqrt(
                list.sumByDouble { a -> (a - mean).pow(2) } / (if (isPopulation) list.size else list.size - 1)
        )
    }
}