package sample;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main extends Application {

    private File clientDir = null;

    public static class ServerHandler extends Thread {
        protected Socket socket       = null;
        protected PrintWriter out     = null;
        protected BufferedReader in   = null;
        protected File sharedDir = new File("F:\\Software_Sys_Dev_Int\\csci2020u_assignment2\\sharedDir");

        // our server's secret code to connect
        //Ps we could read this code at the creation of server so becomes a private chat room for those with the code only
        // based on the server code, you could also store the history of conversations, which could be restored in a future session
        protected String strPasswords = "oursecretchat";

        protected boolean bLoggedIn   = false;
        protected String strUserID    = null;
        protected String strPassword  = null;

        protected Vector messages     = null;

        public ServerHandler(Socket socket, Vector messages) {
            super();
            this.socket = socket;
            this.messages = messages;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("IOEXception while opening a read/write connection");
            }
        }

        public void run() {
            // initialize interaction
            out.println("Connected to File Sharing Server");
            out.println("Current Shared Directory: " + sharedDir.getAbsolutePath());

            boolean endOfSession = false;
            while(!endOfSession) {
                endOfSession = processCommand();
            }
            try {
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        protected boolean processCommand() {
            String message = null;
            try {
                message = in.readLine();
            } catch (IOException e) {
                System.err.println("Error reading command from socket.");
                return true;
            }
            if (message == null) {
                return true;
            }
            // StringTokenizer st = new StringTokenizer(message);
            // String command = st.nextToken();
            // String args = null;
            // if (st.hasMoreTokens()) {
            // 	args = message.substring(command.length()+1, message.length());
            // }
            return processCommand(message);
        }

        protected boolean processCommand(String command) {
            // these are the other possible commands
            if (command.equalsIgnoreCase("LIST")) {
                String[] listofFiles = sharedDir.list();
                String str = String.join(",", listofFiles);
                out.println(str);
                return false;
            }
            // } else if (command.equalsIgnoreCase("GETMSG")) {
            // 	int id = (new Integer(arguments)).intValue();
            // 	if (id < messages.size()) {
            // 		String msg = (String)messages.elementAt(id);
            // 		out.println("200 Message #"+id+": "+msg);
            // 	} else {
            // 		out.println("400 Message Does Not Exist");
            // 	}
            // 	return false;
            // } else if (command.equalsIgnoreCase("ADDMSG")) {
            // 	int id = -1;
            // 	synchronized(this) {
            // 		messages.addElement("["+strUserID+"]: "+arguments);
            // 		id = messages.size()-1;
            // 	}
            // 	out.println("200 Message Sent: "+id);
            // 	return false;
            // } else if (command.equalsIgnoreCase("LOGOUT")) {
            // 	out.println("200 Client Logged Out");
            // 	return true;
            // } else {
            // 	out.println("400 Unrecognized Command: "+command);
            // 	return false;
            // }
            return true;
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));

        BorderPane clientUI = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu upload = new Menu("Upload");
        Menu download = new Menu("Download");
        menuBar.getMenus().addAll(upload,download);

        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        download.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        clientUI.setTop(menuBar);

        Scene client = new Scene(clientUI,800,600);

        primaryStage.setScene(client);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
