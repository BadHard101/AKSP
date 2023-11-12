package Prac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiServer {
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    private static List<String> messages = new ArrayList<>();
    private static List<Socket> clients = new ArrayList<>();

    public void startServer(int port, int maxThreads) throws IOException {
        serverSocket = new ServerSocket(port);
        threadPool = Executors.newFixedThreadPool(maxThreads);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);

            threadPool.execute(new MultiServerHandler(clientSocket));
        }
    }

    public void stopServer() throws IOException {
        threadPool.shutdown();
        serverSocket.close();
    }

    private static class MultiServerHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;

        private PrintWriter out;

        public MultiServerHandler(Socket socket) {
            this.clientSocket = socket;
            Timer broadcastTimer = new Timer();
            broadcastTimer.scheduleAtFixedRate(new BroadcastTask(), 0, 5000); // Запускаем задачу каждые 5 секунд
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while (true) {
                    inputLine = in.readLine();

                    if ("quit".equals(inputLine)) {
                        System.out.println("Connection is closed.");
                        break;
                    }
                    if (!inputLine.isBlank()) {
                        messages.add(inputLine);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    in.close();
                    clientSocket.close();
                    clients.remove(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class BroadcastTask extends TimerTask {
        @Override
        public void run() {
            if (!messages.isEmpty()) {
                int i = 0;
                for (String message : messages) {
                    i = i + 1;
                    for (Socket client : clients) {
                        try {
                            PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);
                            clientOut.println("\nMessage #"+ i + " : " + message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                messages.clear();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        int port = 6666;
        int maxThreads = 10;
        MultiServer server = new MultiServer();
        server.startServer(port, maxThreads);
    }
}
