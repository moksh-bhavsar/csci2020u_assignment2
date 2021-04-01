package sample;

import java.io.*;
import java.net.*;

public class HttpServerHandler implements  Runnable{

    private Socket socket = null;
    private DataInputStream requestInput = null;
    private DataOutputStream responseOutput = null;
    private File file = null;

    public HttpServerHandler(Socket socket, File file) throws IOException {
        this.socket = socket;
        this.file = file;
        requestInput = new DataInputStream(new DataInputStream(socket.getInputStream()));
        responseOutput = new DataOutputStream(socket.getOutputStream());
    }

    public void run() {
        System.out.print("It's running!!\n");
        String line;
        try {
            while((line = requestInput.readUTF()) != null) {
                handleRequest(file, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                requestInput.close();
                responseOutput.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleRequest(File file, String request){
        if ((null != request) && (null != file)) {
            if (request.toUpperCase().equals("DIR")) {
                File[] list = file.listFiles();
                for (File content : list) {
                    System.out.printf("%s, ", content.getName());
                }
            }
        }
    }
}
