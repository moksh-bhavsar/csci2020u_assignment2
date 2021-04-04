package sample;

import java.io.*;
import java.net.*;

public class HttpServerHandler implements  Runnable{

    private Socket socket = null;
    private BufferedReader fromServer = null;
    private PrintWriter toServer = null;
    private File file = null;

    public HttpServerHandler(Socket socket, File file) throws IOException {
        this.socket = socket;
        this.file = file;
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new PrintWriter(socket.getOutputStream(),true);
    }

    public void run() {
        System.out.print("It's running!!\n");
        toServer.print("Hi there");
//        String line;
//        try {
//            while((line = fromServer.readLine()) != null) {
//                handleRequest(file, line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fromServer.close();
//                toServer.close();
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void handleRequest(File file, String request){
        if ((request.isEmpty()) && (null != file)) {
            if (request.equalsIgnoreCase("DIR")) {
                File[] list = file.listFiles();
                for (File content : list) {
                    System.out.printf("%s, ", content.getName());
                }
            }
        }
    }
}
