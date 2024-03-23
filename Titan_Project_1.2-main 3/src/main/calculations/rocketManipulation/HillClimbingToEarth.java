package src.main.calculations.rocketManipulation;

import src.main.calculations.Data;
import src.main.calculations.SolarSystem;
import src.main.calculations.solvers.EulerSolver;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is responsible for calculating the required velocity to reach Earth using a hill climbing algorithm
 * <p>
 * DISCLAIMER: DUE TO THE USE OF MULTI-THREADING THE PRINT STATEMENTS MAY NOT BE IN ORDER. IT CALCULATES MULTIPLE TRAJECTORIES AT ONES, THE PRINT STATEMENTS MAY SHOW OTHERWISE.
 */
public class HillClimbingToEarth {

    static double[] bestSolution = {-2136310.577392578, -152364.73083496094, 171161.65161132812};
    static double stepSize = 10;

    static double error = Double.POSITIVE_INFINITY;
    static boolean foundSolution = false;
    static final int ThreadPoolSize = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        hillClimb();
    }

    /**
     * This method implements the hill climbing algorithm
     */
    public static void hillClimb() {

        simulate(bestSolution);

        while (!foundSolution) {

            double[] currentBest = Arrays.copyOf(bestSolution, bestSolution.length);
            double currentError = error;

            ExecutorService executor = Executors.newFixedThreadPool(ThreadPoolSize);

            for (int i = 0; i < 6; i++) {
                final int index = i;
                final double[] currentSolution = Arrays.copyOf(currentBest, currentBest.length);
                executor.execute(() -> {
                    double[] localSolutionToTest = getNewSolutionToTest(index, currentSolution);
                    simulate(localSolutionToTest);
                    System.out.println("Testing: " + Arrays.toString(localSolutionToTest));
                });
            }

            executor.shutdown();

            // Wait for all tasks to complete
            while (!executor.isTerminated()) {
                Thread.yield();
            }

            if (error == currentError) {
                stepSize = stepSize / 2;
                System.out.println("New step size: " + stepSize);
            }
        }

        System.out.println("Solution found! : " + Arrays.toString(bestSolution) + " with error: " + error);
    }

    /**
     * This method simulates the trajectory of the rocket with the given solution set.
     * The solution set is the array of forces on the rocket that is currently tested, with a potential to be the new best iteration.
     *
     * @param solutionToTest The array of forces on the rocket that is currently tested
     */
    public static void simulate(double[] solutionToTest) {
        SolarSystem system = new SolarSystem();
        RouteControl control = new RouteControl(system);

        control.setIncreaseFire(solutionToTest);

        while ((system.getTimeEngine().getTimeDate()[5] < 2025) || ((system.getTimeEngine().getTimeDate()[4] < 4))) {
            control.checkControl();
            EulerSolver.updatePositions(system);
        }

        double newError = system.calculateDistanceEarthRocket();
        if (newError < error) {
            System.out.println("New best solution found: " + Arrays.toString(solutionToTest) + " with error " + newError);
            error = newError;
            bestSolution = solutionToTest;
        }

        if (newError < Data.getRadius(Data.indEarth)) {
            foundSolution = true;
        }
    }

    /**
     * This method computes a new solution set to test.
     *
     * @param i           The index of the solution set that is being tested
     * @param currentBest The current best solution set
     * @return The new solution set to test
     */
    private static double[] getNewSolutionToTest(int i, double[] currentBest) {

        double[] newSolution = Arrays.copyOf(currentBest, currentBest.length);

        switch (i) {
            case 0 -> newSolution[0] += stepSize;
            case 1 -> newSolution[0] -= stepSize;
            case 2 -> newSolution[1] += stepSize;
            case 3 -> newSolution[1] -= stepSize;
            case 4 -> newSolution[2] += stepSize;
            case 5 -> newSolution[2] -= stepSize;
        }

        return newSolution;
    }

}
