package Prac;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private Scanner scanner;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new Scanner(clientSocket.getInputStream());
        scanner = new Scanner(System.in);
    }

    public void sendMessageLoop() throws IOException {
        String userInput;

        Thread readThread = new Thread(() -> {
            while (true) {
                String response = in.nextLine();
                if (response == null) {
                    break;
                }
                System.out.println(response);
            }
        });
        readThread.start();


        while (true) {
            System.out.print("Enter a message (or 'quit' to exit): ");
            userInput = scanner.nextLine();
            out.println(userInput);
            if ("quit".equals(userInput)) {
                break;
            }
        }
    }


    public void stopConnection() throws IOException {
        scanner.close();
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        String serverIp = "localhost"; // Адрес сервера
        int serverPort = 6666;        // Порт сервера

        Client client = new Client();
        client.startConnection(serverIp, serverPort);
        client.sendMessageLoop();
        client.stopConnection();
    }
}
