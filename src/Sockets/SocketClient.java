package Sockets;

import java.io.*;
import java.net.*;

public class SocketClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the Poker Server");

            String initialHand = in.readLine();
            System.out.println(initialHand);

            while (true) {
                String gameState = in.readLine();
                System.out.println(gameState);

                System.out.println("Enter your action (bet/fold/check): ");
                String action = userInput.readLine();
                out.println(action);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
