import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuadraticEquationSolverTest {
    private static double a = 0;
    private static double b = 0;
    private static double c = 0;

    @Test
    public void testQuadraticEquationWithTwoRealRoots() {
        a = 1.0;
        b = -3.0;
        c = 2.0;

        double[] answer = QuadraticEquationSolver.solveQuadraticEquation(a, b, c);

        assertArrayEquals(answer, new double[]{2.0, 1.0});
    }

    @Test
    public void testQuadraticEquationWithOneRealRoot() {
        a = 1.0;
        b = -4.0;
        c = 4.0;

        double[] answer = QuadraticEquationSolver.solveQuadraticEquation(a, b, c);

        assertArrayEquals(answer, new double[]{2});
    }

    @Test
    public void testQuadraticEquationWithNoRealRoots() {
        a = 2.0;
        b = 1.0;
        c = 5.0;

        double[] answer = QuadraticEquationSolver.solveQuadraticEquation(a, b, c);

        assertArrayEquals(answer, new double[]{});
    }

    @Test
    public void testQuadraticEquationWithZeroA() {
        a = 0;
        b = 1.0;
        c = 1.0;

        double[] answer = QuadraticEquationSolver.solveQuadraticEquation(a, b, c);

        assertArrayEquals(answer, new double[] {-1});
    }
}
