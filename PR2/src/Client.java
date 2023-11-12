import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// https://javarush.com/groups/posts/2283-rmi-praktika-ispoljhzovanija
public class Client {
    private Client(){};

    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry(null);
            QuadraticEquation stub = (QuadraticEquation) registry.lookup("Equation");
            if (args.length != 3) {
                System.out.println("Usage: Client <a> <b> <c>");
                return;
            }
            // Получаем аргументы из командной строки и преобразуем их в целые числа
            double a = Double.parseDouble(args[0]);
            double b = Double.parseDouble(args[1]);
            double c = Double.parseDouble(args[2]);

            stub.solveQuadraticEquation(a, b, c);
            System.err.println("Server ready");
        } catch (Exception e){
            System.err.println("Server exception" + e.toString());
            e.printStackTrace();
        }
    }
}
