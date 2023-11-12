import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends QuadraticEquationSolver {

    public Server() {
    }

    public static void main(String[] args) {
        try {
            QuadraticEquationSolver obj = new QuadraticEquationSolver();

            QuadraticEquation stub = (QuadraticEquation) UnicastRemoteObject.exportObject(obj, 8082);
            Registry registry = LocateRegistry.getRegistry();

            registry.bind("Equation", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}