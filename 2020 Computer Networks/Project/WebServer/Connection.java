import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection extends Thread{
    // Required variables for i/o operations..
    Socket socket;
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    OutputStream outputStream;
    boolean isValid=true;                           // for validation of requests..
    public Connection(Socket socket) throws Exception {         // Connection constuct for initialize variables..

        this.socket=socket;
        InputStreamReader inputStreamReader=new InputStreamReader(this.socket.getInputStream());
        bufferedReader=new BufferedReader(inputStreamReader);
        printWriter=new PrintWriter(this.socket.getOutputStream());
        outputStream=socket.getOutputStream();
    }
    public void run(){                  // Thread start..

        try {
            String requestFrame="";             // Holds the request that comes from client..
            while (bufferedReader.ready() || requestFrame.length()==0){
                requestFrame+=(char)bufferedReader.read();          // reads buffer char by char to get all the request..
            }
                HTTPRequest request = new HTTPRequest(requestFrame);
                if (!request.fileReq.matches("^[0-9]*$") || Integer.parseInt(request.fileReq) > 20000 || Integer.parseInt(request.fileReq) < 100) {// file size and isDigit check..
                    HTTPResponse errorResponse = new HTTPResponse(request);
                    System.out.println(errorResponse.HTTPBadRequestResponse(request));
                    isValid = false;
                }
                if (request.methodName.equals("TRACE") || request.methodName.equals("HEAD") || request.methodName.equals("POST") || request.methodName.equals("PUT") || request.methodName.equals("DELETE")) {
                    HTTPResponse errorResponse = new HTTPResponse(request);
                    System.out.println(errorResponse.HTTPNotImplementedResponse(request));
                    isValid = false;
                }
                if (isValid && request.methodName.equals("GET")) {  // if the request is valid and it is a GET method then the request, the response write to the console and browser.
                    framePrinter(requestFrame);
                    HTTPResponse response = new HTTPResponse(request);
                    streamPrinter(outputStream,response);
                    printWriter.close();
                    bufferedReader.close();
                    socket.close();
                }
        } catch (Exception e) {

        }
    }
    public void framePrinter(String requestFrame){  // prints request
        System.out.println("----Start of Request Frame----\n");
        System.out.print(requestFrame);
        System.out.println("\n----End of Request Frame----\n");
    }
    public void streamPrinter(OutputStream outputStream,HTTPResponse response){ // prints response to the browser..
       try {
           outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
           outputStream.write("\r\n".getBytes());
           outputStream.write(response.response.getBytes(StandardCharsets.UTF_8));
           outputStream.flush();
           outputStream.close();
       }catch (Exception e){

       }
    }
}
