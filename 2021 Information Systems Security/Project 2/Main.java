public class Main {

    public static void main(String[] args) throws Exception {

        // Server initialize
        initServer();

    }
    public static void initServer() throws Exception {
        WebServer server=new WebServer();
        server.start();
    }
}
