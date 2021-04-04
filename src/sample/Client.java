package sample;

import java.io.*;
import java.net.*;

public class Client{
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter networkOut = null;
    private BufferedReader networkIn = null;

    //we can read this from the user too
    public  static String SERVER_ADDRESS = "localhost";
    public  static int    SERVER_PORT = 16789;

    public Client() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: "+SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("IOEXception while connecting to server: "+SERVER_ADDRESS);
        }
        if (socket == null) {
            System.err.println("socket is null");
        }
        try {
            networkOut = new PrintWriter(socket.getOutputStream(), true);
            networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("IOEXception while opening a read/write connection");
        }

        in = new BufferedReader(new InputStreamReader(System.in));

        // force the user to type in a username and password
        boolean ok = login();

        if (!ok) {
            System.exit(0);
        }

        ok = true;
        ok = processUserInput();


        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Logcin function
    protected boolean login() {
        String message = null;

        try {
            message = networkIn.readLine(); //Connected to File Sharing Server
            System.out.println(message);
            message = networkIn.readLine(); //Current shared directory
            System.out.println(message);
        } catch (IOException e) {
            System.err.println("Error reading initial greeting from socket.");
        }

        return true;
    }

    // Display Menu  of actions
    // Alternatively you can always be in "reading mode" whatever is typed gets send to the server/other clients without they having to "List all messages"
    // -- This would work 100x better and easier if you make at least the client a JavaFX application, the user can type in a textbox, when pressed <enter> you send the message
    // --- Every time the server gets a message they send to all the other clients who get their UI refreshed with the most recent messages, etc.
    protected boolean processUserInput() {
        String input = null;

        // print the menu
        System.out.println("Commands: ");
        System.out.println("1 -DIR");
        System.out.println("2 - UPLOAD");
        System.out.println("3 - DOWNLOAD");
        System.out.print("Command> ");

        try {
            input = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (input.equals("1")) {
            listAllFiles();
        } else if (input.equals("2")) {
            uploadFile();
        } else if (input.equals("3")) {
            return false;
        }
        return true;
    }

    // menu option 2
    public void uploadFile() {
        String message = null;
        String input = null;

        System.out.print("Please write absolute path of file: ");
        try {
            input = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        networkOut.println("PATH "+input);

        // read and ignore response
        try {
            message = networkIn.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from socket.");
        }
    }

    // menu option 1
    public void listAllFiles() {

        networkOut.println("LIST");

        try {
            getFiles();
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public void getFiles() throws IOException{
        String listofFile = networkIn.readLine();
        System.out.println(listofFile);
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}