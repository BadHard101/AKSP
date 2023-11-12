package Program2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * Серверная программа, которая принимает запросы от клиентов на обработку строк с заглавных букв.
  * Когда клиент подключается, то запускается новый поток 
* для его обработки. Получение клиентских данных, их 	  * использование и отправка ответа - все это делается в
* потоке, что обеспечивает гораздо большую пропускную 
*способность, поскольку одновременно может 
* обрабатываться больше клиентов.
 */
public class CapitalizeServer {

    /**
     * Запускается сервер. Когда клиент подключается, 
* сервер создает новый поток для обслуживания и 
* немедленно возвращается к прослушиванию. 
* Приложение ограничивает количество потоков через 
* пул потоков (в противном случае миллионы клиентов
* могут привести к исчерпанию ресурсов сервера из-за
* выделения слишком большого количества потоков).
     */
    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(59898)) {
            System.out.println("Сервер запущен...");
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Capitalizer(listener.accept()));
            }
        }
    }

    private static class Capitalizer implements Runnable {
        private Socket socket;

        Capitalizer(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Подключение: " + socket);
            try {
                var in = new Scanner(socket.getInputStream());
                var out = new PrintWriter(socket.getOutputStream(), true);
                while (in.hasNextLine()) {
                    out.println(in.nextLine().toUpperCase());
                }
            } catch (Exception e) {
                System.out.println("Ошибка:" + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Closed: " + socket);
            }
        }
    }
}  
