package src.main.calculations.solvers;

import java.io.FileWriter;
import java.io.Writer;

/**
 * This class is responsible for generating the data for the experiment of comparing errors
 * between the actual result of ODE y' = (t + y)^2 - 1 for y(1) and results approximated
 * with different numerical solvers over different step sizes
 */
public class SolversExperiment {

    /**
     * After running, this method generates data for the experiment.
     */
    public static void main(String[] args) {

        double stepSize;

        StringBuilder textToWrite = new StringBuilder("StepSize\tEuler\tHeuns\tRalstons\tRunge-Kutta\n");

        //The range of the step sizes is 1 to 1/(2^24) - (25 different step sizes)
        for (int i = 0; i < 25; i++) {

            stepSize = 1 / (Math.pow(2, i));

            double eulerResult = EulerSolver.experimentResult(stepSize);
            double heunsResult = HeunsSolver.experimentResult(stepSize);
            double ralstonsResult = RalstonsSolver.experimentResult(stepSize);
            double rk4Result = RK4Solver.experimentResult(stepSize);

            textToWrite.append(stepSize).append("\t").append(eulerResult).append("\t")
                    .append(heunsResult).append("\t").append(ralstonsResult).append("\t").append(rk4Result).append("\n");
        }

        System.out.println(textToWrite);

        try {
            Writer dataFile = new FileWriter("ExperimentDifferentStepSizes.txt");
            dataFile.write(textToWrite.toString());
            dataFile.close();
        } catch (Exception e) {
            e.getStackTrace();
        }


    }


}
