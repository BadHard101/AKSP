import java.util.Arrays;
import java.util.Scanner;

public class QuadraticEquationSolver {
    public static double[] solveQuadraticEquation(double a, double b, double c) {
        if (a == 0) {
            if (b == 0) {
                return new double[0];
            } else {
                return new double[] {-c / b};
            }
        } else {
            double discriminant = b * b - 4 * a * c;

            if (discriminant > 0) {
                double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                return new double[] {root1, root2};
            } else if (discriminant == 0) {
                double root = -b / (2 * a);
                return new double[] {root};
            } else {
                return new double[0];
            }
        }
    }
}
