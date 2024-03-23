package src.main.calculations.landingOpenController;

/**
 * This class is a universal ODE solver (Euler method solver), only used by the controller, which is able to take any f(t_n, y_n) and solve it
 */
public class ControllerODESolver {
    final static double h = 1; // step size

    public static double eulerSolver(double y_n, double func) {
        return y_n + h * func;
    }

}
