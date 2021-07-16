import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WebServer {
    //Server starting...
    public void start() throws Exception {
        ArrayList<String> onlineUsers= new ArrayList<>();// Arraylists for online users
        ArrayList<String> onlineUsers2= new ArrayList<>();
        ArrayList<String> postedImages= new ArrayList<>(); // Arraylists for postedImages
        ArrayList<SecretKey> keyArrayList=new ArrayList<>();// Arraylist for keys
        ArrayList<IvParameterSpec> ivParameterSpecList=new ArrayList<>();// Arraylist for iv
        ArrayList<byte[]> ciphered=new ArrayList<>();// Arraylist for ciphered text
        int port=8081;
        ServerSocket serverSocket=new ServerSocket(port);
        System.out.println("Web Server starting up on port "+port+".");
        System.out.println("Server Link: http://localhost:"+port);
        String pageLink="http://localhost:"+port;
        while (true){
            Socket socket=serverSocket.accept();
            Connection connection=new Connection(socket,pageLink,onlineUsers,onlineUsers2,postedImages,keyArrayList,ivParameterSpecList,ciphered);
            connection.runServer();
        }
    }

}
