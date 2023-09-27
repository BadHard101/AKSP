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

        assertTrue(answer.equals(new double[] {}));
    }

    @Test
    public void testQuadraticEquationWithOneRealRoot() {
        a = 1.0;
        b = -4.0;
        c = 4.0;

        String output = QuadraticEquationSolver.solveQuadraticEquation(a, b, c);

        assertTrue(output.contains("Уравнение имеет один корень:"));
        assertTrue(output.contains("Корень: 2.0"));
    }

    @Test
    public void testQuadraticEquationWithNoRealRoots() {
        a = 2.0;
        b = 1.0;
        c = 5.0;

        String output = QuadraticEquationSolver.solveQuadraticEquation(a, b, c);

        assertTrue(output.contains("Уравнение не имеет действительных корней."));
    }

    @Test
    public void testQuadraticEquationWithZeroA() {
        a = 0;
        b = 1.0;
        c = 1.0;

        String output = QuadraticEquationSolver.solveQuadraticEquation(a, b, c);

        assertTrue(output.contains("Уравнение не имеет действительных корней."));
    }
}
