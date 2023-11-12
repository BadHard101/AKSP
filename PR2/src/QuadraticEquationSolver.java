import java.rmi.RemoteException;

public class QuadraticEquationSolver implements QuadraticEquation {
    /**
     * Этот метод вычисляет корни квадратного уравнения, заданного своими коэффициентами.
     * Он обрабатывает случаи наличия двух действительных корней,
     * одного действительного корня или отсутствия действительных корней.
     */
    public double[] solveQuadraticEquation(double a, double b, double c) throws RemoteException {
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
