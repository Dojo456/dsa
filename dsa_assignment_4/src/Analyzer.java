import java.math.BigInteger;
import java.util.Objects;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

public class Analyzer {

    long trialOne = 1;
    long trialTwo;
    long trialThree;

    long[] trials;
    long[] orgTrials;
    double total = 0;

    private static double[] linear;
    private static double[] constant;
    private static double[] log;
    private static double[] nlog;
    private static double[] squared;
    private static double[] factorial;

    Analyzer(long[] trials, int trialOneSize, int trialTwoSize, int trialThreeSize) {
        this.trials = trials.clone();
        orgTrials = trials.clone();
        findRelative(this.trials);

        trialTwo = trialTwoSize / trialOneSize;
        trialThree = trialThreeSize / trialOneSize;

        for (long current : this.trials) {
            total += current;
        }

        linear = new double[] {trialOne, trialTwo, trialThree};
        findRelative(linear);

        constant = new double[] {1, 1, 1};

        log = new double[] {trialOne, Math.log(trialTwo), Math.log(trialThree)};
        findRelative(log);

        nlog = new double[] {trialOne, trialTwo * Math.log10(trialTwo), trialThree * Math.log10(trialThree)};
        findRelative(nlog);

        squared = new double[] {trialOne, Math.pow(trialTwo, 2), Math.pow(trialThree, 2)};
        findRelative(squared);

        factorial = new double[] {trialOne,
                factorial(new BigInteger(String.valueOf(trialTwo))).doubleValue(),
                factorial(new BigInteger(String.valueOf(trialThree))).doubleValue()};
        findRelative(factorial);
    }

    private static BigInteger factorial(BigInteger number) {
        if (Objects.equals(number, BigInteger.ZERO) || Objects.equals(number, BigInteger.ONE)) {
            return BigInteger.ONE;
        }

        return number.multiply(factorial(number.subtract(BigInteger.ONE)));
    }

    private void findRelative(double[] input) {
        // this and the next method just finds the relative time of each trial with trial1 being the baseline
        input[1] /= input[0];
        input[2] /= input[0];
        input[0] = 1;
    }

    private void findRelative(long[] input) {
        input[1] /= input[0];
        input[2] /= input[0];
        input[0] = 1;
    }

    public String test() {
        // finds the Chi-squared test score of the observed trial time to the expected trial time for each complexity
        ChiSquareTest test = new ChiSquareTest();

        double[] results = new double[] {
                test.chiSquare(linear, trials),
                test.chiSquare(constant, trials),
                test.chiSquare(log, trials),
                test.chiSquare(nlog, trials),
                test.chiSquare(squared, trials),
                test.chiSquare(factorial, trials)
        };


        // largest is actually the index expected time with the lowest Chi-square statistic
        int largest = 0;

        for ( int i = 1; i < results.length; i++ ) {
            if ( results[i] < results[largest] ) largest = i;
        }

        String output = "";

        switch(largest) {
            case 0:
                output = "O(n)";
                break;

            case 1:
                output = "O(1)";
                break;

            case 2:
                output = "O(Log(n))";
                break;

            case 3:
                output = "O(n * Log(n))";
                break;

            case 4:
                output = "O(n^2)";
                break;

            case 5:
                output = "O(n!)";
                break;

            default:
        }


        return output;
    }



}