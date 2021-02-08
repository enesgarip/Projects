// Java HTTP Web Server Implementation
// Abdullah Gülçür - 150116014
// Enes Garip - 150116034

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    ServerSocket server_socket;
    static int portNumber;

    public static void main(String[] args) throws Exception {
        if (args.length != 1 || !args[0].matches("^[0-9]*$")) {

            System.out.println("Argument Error!!!");
        }
        else {
            portNumber=Integer.parseInt(args[0]);
            System.out.println("Port Number: " + portNumber);
            WebServer webServer=new WebServer();
            webServer.runServer();
        }
    }

    public void runServer() throws Exception {

        System.out.println("Web Server has started now.");
        server_socket = new ServerSocket(portNumber); // New server socket. The port is the argument of the program.
        processHTTPRequest();
    }

    public void processHTTPRequest() {
        // For multithreading there is an infinite loop that creates sockets for each client..
        while(true) {
            System.out.println("Connection established on port: " + portNumber);
            Socket socket = null;
            try {
                socket = server_socket.accept();    // Accept the requests of the clients..
            } catch (IOException e) {
                e.printStackTrace();
            }
            Connection connection = null;
            try {
                connection = new Connection(socket); // Connection object created. In Connection class, the server begins to work with request and responses.
            } catch (Exception e) {
                e.printStackTrace();
            }
            connection.start(); // The connection class extends Threads so the multithreading process begins..
        }
    }
}
