public class HTTPRequest {
    String methodName;      // method name of the request
    String fileReq;         // size of file
    public HTTPRequest(String request) {
        String[] lines = request.split("\n");
        try {
            methodName=lines[0].split(" ")[0];
            fileReq = lines[0].split(" ")[1];
            fileReq = fileReq.substring(1);
        }catch (Exception e){

        }
    }
}
