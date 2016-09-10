/****************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:  java PercolationStats 200 100
 *  Dependencies: Percolation.java algs4.jar stdlib.jar
 *
 *  Percolation class for Monte Carlo simulation.
 *
 ****************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int sizeOfSide;
    private int numberOfTrials;
    private double[] results;

    /**
     *  Perform independent experiments on square grid
     *  @param n      size of the grid side
     *  @param trials number of experiments to perform
     */

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        sizeOfSide = n;
        numberOfTrials = trials;
        results = new double[trials];
        runTrials();
    }

    private void runTrials() {
        for (int i = 0; i < numberOfTrials; i++) {
            Percolation perc = new Percolation(sizeOfSide);
            double openSites = 0;

            while (!perc.percolates()) {
                int x = StdRandom.uniform(1, sizeOfSide + 1);
                int y = StdRandom.uniform(1, sizeOfSide + 1);
                if (!perc.isOpen(y, x)) {
                    perc.open(y, x);
                    openSites++;
                }
            }
            results[i] = openSites / (sizeOfSide * sizeOfSide);
        }
    }

    /**
     * Sample mean of percolation threshold
     * @return double  mean
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * Sample standard deviation of percolation threshold
     * @return double  stddev
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * Low  endpoint of 95% confidence interval
     * @return double  low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(numberOfTrials);
    }

    /**
     * High  endpoint of 95% confidence interval
     * @return double  high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(numberOfTrials);
    }

    /**
     * Test client for simulation. Takes two command-line arguments
     * n and trials, performs trials independent computational experiments
     * on an n-by-n grid, and prints the mean, standard deviation, and
     * the 95% confidence interval for the percolation threshold.
     * @param args  n:      size of the grid side
     *              trials: number of experiments
     */
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(200, 1000);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("interval = " + ps.confidenceLo() + " " + ps.confidenceHi());
    }
}
