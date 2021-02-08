//import java.io.*;
//import java.net.http.HttpResponse;
public class HTTPResponse { // response class for create the respnse
    HTTPRequest request;
    String response;
    String alphabet="abcdefghijklmnopqrstuvwxyz";           // alphabet for creating the html document..
    String CRLF = " \r\n";
    String htmlTags="<html><head><title>title</title></head><body>";
    String htmlClosingTags="</body></html>"+CRLF;
    String consoleResponse;                 // for print response to the console..
    public HTTPResponse(HTTPRequest request) {
        this.request = request;
        try {
                response =htmlTags;
                int size=Integer.parseInt(request.fileReq);
                for (int i=0;i<size-62;i++){
                    response+=alphabet.charAt((int)(Math.random()*(26)));
                }// in that part, total count of chars in html tags is 59 so for example 1000 is requested size ...
            // 1000 - 59 = 941. The for loop iterates 941 times and creates 941 random chars for html file. As a result..
            // of that operation we have a html file that is exactly 1000 bytes..
                response+=htmlClosingTags;
            if (size>=100 && size<=20000) {             // if size is between 100 and 20000 so the console output is created.
                consoleResponse = "Server: Multi-Threaded Web Server" + CRLF;
                consoleResponse = consoleResponse + "Content-Type: text/html" + CRLF;
                consoleResponse = consoleResponse + "Connection: keep-alive" + CRLF;
                consoleResponse = consoleResponse + "Content-Length: " + response.length() + CRLF;
                System.out.print(consoleResponse);
                System.out.println("----Contents of the request----");
                System.out.println(response);
                System.out.println("\n----Contents of the requested file---- Ended.");
            }
        }catch (Exception e){           // exception for response..
            System.out.println("Response Error!!!");
        }
    }
    public String HTTPBadRequestResponse(HTTPRequest request){ // response for invalid requests..
        this.request=request;
        response="400 Bad Request"+CRLF;
        return  response;
    }
    public String HTTPNotImplementedResponse(HTTPRequest request){      // response for not implemented requests.
        this.request=request;
        response="501 Not Implemented"+CRLF;
        return response;
    }
}
