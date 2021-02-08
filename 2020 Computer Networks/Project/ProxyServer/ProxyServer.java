// Java Proxy Server Implementation
// Abdullah Gülçür - 150116014
// Enes Garip - 150116034

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ProxyServer {
    public static void main(String[] args) {
        int localPort = 8888; // port number of the proxy
        System.out.println("Proxy server is started.\nPort:" + localPort);
        try {
            ServerSocket server = new ServerSocket(localPort);      //creating a server socket
            while (true){
                Socket socket= server.accept();
                new ThreadProxy(socket);
            }
        } catch (IOException e) {
            System.out.println("Server socket Error!!");
        }
    }
}
class ThreadProxy extends Thread{
    String finalRequest;        // holds the the last version of the request
    Socket sClient;         // socket for client
    String response;            // holds the response
    ThreadProxy(Socket sClient){
        this.sClient=sClient;
        this.start();
    }
    @Override
    public void run(){  // Thread starts..
        final byte[] request = new byte[1024];      // holds request from the client
        try {
            // i/o Streams for the client..
            InputStream inFromClient = sClient.getInputStream();
            PrintWriter outToClientWriter=new PrintWriter(sClient.getOutputStream());
            new Thread() {      // new thread
                public void run() {
                    try {
                        while ((inFromClient.read(request)) != -1) {
                            String s=new String(request, StandardCharsets.UTF_8);           // reads the request byte by byte
                            if(s.split("\n")[0].split(" ")[0].equals("GET")){
                                System.out.println("\n-----Request From Client to Proxy Server-----");
                                System.out.println(s.split("\n")[0]);       // request of the client..
                                System.out.println("-----End of Request-----\n");
                                if(s.split("\n")[0].length()>20) {
                                    String requestFromClient = s.split(" ")[1];
                                    String portNumber = requestFromClient.split(":")[2].split("/")[0];
                                    Socket server = new Socket("localhost", Integer.parseInt(portNumber));
                                    final InputStream inFromServer = server.getInputStream();// i/o Streams for the server..
                                    final OutputStream outToServer = server.getOutputStream();// i/o Streams for the server..
                                    String sizeCheck = requestFromClient.split("/")[3];
                                    if (sizeCheck.matches("\\d+")) {
                                        int sizeNumber = Integer.parseInt(sizeCheck);
                                        finalRequest="GET /"+sizeNumber+" HTTP/1.1";
                                        if (sizeNumber > 9999) {            // size restriction...
                                            System.out.println("414 Request-URI Too Long");
                                        }else if (sizeNumber<100){             // size restriction..
                                            System.out.println("Bad Request!!");
                                        }
                                        else {
                                            BufferedReader bf = new BufferedReader(new InputStreamReader(inFromServer));//buffer for server to proxy communication
                                            outToServer.write(finalRequest.getBytes(StandardCharsets.UTF_8));           // writing the final request to the server
                                            outToServer.write("\n".getBytes(StandardCharsets.UTF_8));
                                            outToServer.flush();
                                            System.out.println("-----Response to the Client-----");
                                            while (((response = bf.readLine()) != null)) { //forwarding web server's response to the client
                                                System.out.println(response); //console output
                                                outToClientWriter.println(response);// browser output
                                                outToClientWriter.flush();
                                            }
                                            System.out.println("-----Response to the Client-----Ended.");
                                        }
                                        // closing the sockets
                                        outToServer.close();
                                        sClient.close();
                                        server.close();
                                    } else System.out.println("Bad Request!!");
                                }
                            }
                            else System.out.println("Request Error!!");
                        }
                    }catch (Exception e){

                    }
                }
            }.start();

        }catch (Exception e){

        }
    }
}
