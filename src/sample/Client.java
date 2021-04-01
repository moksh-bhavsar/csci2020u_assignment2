package sample;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket = null;
    private int port;
    private String hostname = null;

    public Client (String hostname, int port) throws IOException {
        this.hostname = hostname;
        this.port = port;
        this.socket = new Socket(hostname,port);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in));
        Client client = new Client("localhost", 8080);
        System.out.print("Enter your command:");
        String request = user_input.readLine();
        System.out.print(request);
        PrintWriter output = new PrintWriter(client.socket.getOutputStream());
        output.print(request);
//        out.flush();
    }
}
