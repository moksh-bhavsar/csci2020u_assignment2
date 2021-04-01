package sample;

import java.io.*;
import java.net.*;

public class Server extends Thread{
    private ServerSocket serverSocket = null;
    private final int port;
    private static final File sharedDir = new File("F:\\Software_Sys_Dev_Int\\csci2020u_assignment2\\sharedDir");

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.port = port;
    }

    public void handleRequests(File file) throws IOException {
        System.out.printf("Listening to port: %d\n", this.port);

        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Connected to %s:%d\n", clientSocket.getLocalAddress().toString(),clientSocket.getPort());
            InputStreamReader inStream = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader in = new BufferedReader(inStream);
            String request = in.readLine();
            System.out.println("Command: " + request);
            HttpServerHandler handler = new HttpServerHandler(clientSocket, file);
            Thread handlerThread = new Thread(handler);
            handlerThread.start();
            clientSocket.close();
        }
    }

    public static void main(String[] args) {
        int port = 8080;

        try{
            Server server = new Server(port);
            server.handleRequests(sharedDir);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
