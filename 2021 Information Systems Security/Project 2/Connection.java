import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection {
    Socket socket;
    String pageLink;
    PublicKey serverPublicKey;
    PrivateKey serverPrivateKey;
//    String serverPublicKeyString;
//    String serverPrivateKeyString;
    String signedString;
    ArrayList<String> onlineUsers;
    ArrayList<String> onlineUsers2;
    ArrayList<String> postedImages;
    ArrayList<SecretKey> keyArrayList;
    ArrayList<IvParameterSpec> ivParameterSpecList;
    ArrayList<byte[]> ciphered;
    String dir = System.getProperty("user.dir");

    public Connection(Socket socket, String pageLink, ArrayList<String> onlineUsers,ArrayList<String> onlineUsers2,ArrayList<String> postedImages,ArrayList<SecretKey> keyArrayList,ArrayList<IvParameterSpec> ivParameterSpecList
    ,ArrayList<byte[]> ciphered){
       this.socket=socket;
       this.pageLink=pageLink;
       this.onlineUsers=onlineUsers;
       this.onlineUsers2=onlineUsers2;
       this.postedImages=postedImages;
       this.keyArrayList=keyArrayList;
       this.ivParameterSpecList=ivParameterSpecList;
       this.ciphered=ciphered;
    }
    public void runServer() throws Exception {
        // neccessary file creations
        File registeredUsers=new File("registered_users.txt");
        registeredUsers.createNewFile();
        File serverKeys=new File("server_keys.txt");
        serverKeys.createNewFile();
        File certificates=new File("certificates.txt");
        certificates.createNewFile();
        File logFile=new File("log.txt");
        FileOutputStream fos = new FileOutputStream(logFile, true);


        KeyPair serverKeyPair=generateKeyPair();
        serverPublicKey=serverKeyPair.getPublic();
        serverPrivateKey=serverKeyPair.getPrivate();
        FileOutputStream serverFos=new FileOutputStream("server_keys.txt");
        serverFos.write(Base64.getEncoder().encodeToString(serverPublicKey.getEncoded()).getBytes());
        serverFos.write("\n".getBytes());
        serverFos.write(Base64.getEncoder().encodeToString(serverPrivateKey.getEncoded()).getBytes());
//        serverPublicKeyString=Base64.getEncoder().encodeToString(serverPublicKey.getEncoded());
//        serverPrivateKeyString=Base64.getEncoder().encodeToString(serverPrivateKey.getEncoded());
//        System.out.println("---- "+serverPublicKeyString+"\n----- "+serverPrivateKeyString);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        String request=in.readLine();
        System.out.println("request -> "+request);
        fos.write("request->".getBytes());
        fos.write(request.getBytes());
        fos.write("\n".getBytes());
        fos.flush();
        // Splitting the request to handle pages such as register page, upload page
        if(!request.contains("favicon")){
            if (request.split("\s")[1].length()==1 || request.split("\s")[1].contains("home")  ){
                home(out,socket);
            }
            else if(request.split("\s")[1].contains("reg")){
                registerPage(out,socket);
            }

            else if (request.split("\s")[1].contains("add") ){
                if(!request.contains("favicon")) {
                    String name;
                    Pattern pattern = Pattern.compile("fname=[0-9a-zA-Z]+", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(request);
                    matcher.find();
                    name = matcher.group();
                    name = name.substring(6);
                    fileProcesses(registeredUsers,name,out,socket,certificates);
                    fos.write(name.getBytes());
                    fos.write(" registered to the system.".getBytes());
                    fos.write("\n".getBytes());
                    fos.flush();
                }
            }
            else if (request.split("\s")[1].contains("online_session")){
                if(request.split("\s")[1].length()>30){
                    String name;
                    Pattern pattern = Pattern.compile("fname=[0-9a-zA-Z]+", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(request);
                    matcher.find();
                    name = matcher.group();
                    name = name.substring(6);
                    Pattern pattern2 = Pattern.compile("password=([^\\s]+)", Pattern.CASE_INSENSITIVE);
                    Matcher matcher2 = pattern2.matcher(request);
                    matcher2.find();
                    String password= matcher2.group();
                    password=password.substring(9);
                    if(fileSearch(registeredUsers,name,password)){
                        onlineUsers2.add(name);
                        if (!onlineUsers.contains(name)){
                            onlineUsers.add(name);
                            dir =dir+"\\" + name + "\\";
                            File newDir = new File(dir);
                            if ( !newDir.exists()) {
                                newDir.mkdir();
                            }

                        }
                        fos.write(name.getBytes());
                        fos.write(" logged into the system.".getBytes());
                        fos.write("\n".getBytes());
                        fos.flush();
                        userPage(out,socket,name);
                    }
                    else errorPage2(out,socket);
                }
                else errorPage4(out,socket);

            }
            else if(request.split("\s")[1].contains("login")){
                loginPage(out,socket);
            }
            else if (request.split("\s")[1].contains("upload")){
                uploadPage(out,socket);
            }
            else if (request.split("\s")[1].contains("download")){
                downloadPage(out,socket,postedImages);
            }
            else if (request.split("\s")[1].contains("FileServlet")){
                String fileName;
                String fileDir=dir;
                Pattern pattern = Pattern.compile("fileName=([^\\s]+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(request);
                matcher.find();
                fileName = matcher.group();
                fileName = fileName.substring(9);
                fileDir=fileDir+"\\images\\"+fileName;
                postedImages.add(fileName+" "+onlineUsers2.get(onlineUsers2.size()-1));
                for (String e:postedImages){
                    System.out.println("POSTED: "+e);
                }
                fos.write(onlineUsers2.get(onlineUsers2.size()-1).getBytes());
                fos.write(" uploaded ".getBytes());
                fos.write(fileName.getBytes());
                fos.write(" to the system".getBytes());
                fos.write("\n".getBytes());
                fos.flush();
                postImage(out,socket,fileDir,fileName, onlineUsers2.get(onlineUsers2.size()-1));

            }

            else if (request.split("\s")[1].contains("inst")){
                String name;
                Pattern pattern = Pattern.compile("fname=[0-9a-zA-Z]+", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(request);
                matcher.find();
                name = matcher.group();
                name = name.substring(6);
                Pattern pattern2 = Pattern.compile("file=([^\\s]+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher2 = pattern2.matcher(request);
                matcher2.find();
                String fileName= matcher2.group();
                fileName=fileName.substring(5);
                fos.write(name.getBytes());
                fos.write(" downloaded ".getBytes());
                fos.write(fileName.getBytes());
                fos.write(" from the system".getBytes());
                fos.write("\n".getBytes());
                fos.flush();
                downloadImage(out,socket,name,fileName);
            }
            else errorPage(out,socket);
        }

    }
    // download page html
    private void downloadImage(PrintWriter out, Socket socket,String name,String fileName) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        String imageDirectory=dir+"\\"+name+"\\"+fileName;
        decryptImage(imageDirectory, fileName);

        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Image info downloaded from the server "+name+"</H1>");
        out.println("<p>Downloaded Image Directory:"+dir+"\\"+name+"</p>");
        out.println("<p>Downloaded Image Name:"+fileName+"</p>");
        out.flush();
        socket.close();
    }
    // decryption of an Image
    private void decryptImage(String imageDirectory, String fileName) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        Cipher decryptCipher = Cipher.getInstance("RSA");
//        decryptCipher.init(Cipher.DECRYPT_MODE, serverPrivateKey);
//        byte[] decryptedAesKey=decryptCipher.doFinal(ciphered.get(0));

        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, keyArrayList.get(0) ,ivParameterSpecList.get(0));

        CipherInputStream ciptt=new CipherInputStream(new FileInputStream(imageDirectory), cipher);

        FileOutputStream fop=new FileOutputStream(dir+"\\"+fileName);

        int j;
        while((j=ciptt.read())!=-1){
            fop.write(j);
        }
        keyArrayList.remove(0);
        ivParameterSpecList.remove(0);
        ciphered.remove(0);
    }
    // download page image
    private void downloadPage(PrintWriter out, Socket socket, ArrayList<String> postedImages) throws IOException {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Download Page</H1>");
        out.println("""
                <form action="/inst">
                  <label for="fname">Name:</label>
                  <input type="text" id="fname" name="fname" <br>
                  <label for="password">File:</label>
                  <input type="text" id="file" name="file" <br>
                  <input type="submit" value="Submit">
                </form>\s""");
        out.println("<br><h4>Downloadable Files</h4>");
        for (String s: postedImages) {
            out.println("<p>"+s+"</p>");
//            out.println("<br>");

        }
        out.flush();
        socket.close();
    }
    // image post part - creates info file that contains filename, digital signature etc of an uploaded image
    private void postImage(PrintWriter out, Socket socket, String fileDir,String fileName,String name) throws Exception {
        byte[] digitalSignature;
        IvParameterSpec iv;

        File file=new File(dir+"\\"+name+"\\"+fileName.substring(0,fileName.length()-4)+"-UPLOAD.txt");
        file.createNewFile();

//        System.out.println(dir+"\\"+name+"\\"+fileName.substring(0,fileName.length()-4)+".txt");
        File source=new File(fileDir);
        File dest=new File(dir+"\\"+name+"\\"+fileName);

        if (!Files.exists(dest.toPath())){
            Files.copy(source.toPath(),dest.toPath());
        }

        SecretKey secretKey=createAESKey(128);
        iv=encryptAESCBC(secretKey,dest.getPath());

        keyArrayList.add(secretKey);
        ivParameterSpecList.add(iv);

        byte[] messageBytes = Files.readAllBytes(Paths.get(fileDir));
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageHash = md.digest(messageBytes);
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey userPublicKey = null;
        PrivateKey userPrivateKey;

        FileOutputStream fos=new FileOutputStream(file.getPath()+"");


        FileInputStream in=new FileInputStream(dir+"\\registered_users.txt");
        Scanner scanner=new Scanner(in);
        while (scanner.hasNextLine()){
            String line=scanner.nextLine();
            if (name.equals(line.split("\s")[0])){
                byte[] publicKeyBytes = Base64.getDecoder().decode(line.split("\s")[2]);
                byte[] privateKeyBytes = Base64.getDecoder().decode(line.split("\s")[3]);

                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                userPrivateKey = keyFactory.generatePrivate(privateKeySpec);

                EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                userPublicKey = keyFactory.generatePublic(publicKeySpec);
                cipher.init(Cipher.ENCRYPT_MODE,userPrivateKey);

                digitalSignature = cipher.doFinal(messageHash);

                Cipher c = Cipher.getInstance("RSA");
                c.init(Cipher.ENCRYPT_MODE, serverPublicKey);
                byte[] cipherText = c.doFinal(secretKey.getEncoded());
                ciphered.add(cipherText);


                fos.write("filename:".getBytes());
                fos.write(fileName.getBytes());
                fos.write("\n".getBytes());
                fos.write("image:".getBytes());
                fos.write(dest.toString().getBytes());
                fos.write("\n".getBytes());
                fos.write("digitalsignature:".getBytes());
                fos.write(digitalSignature);
                fos.write("\n".getBytes());
                fos.write("aeskey:".getBytes());
                fos.write(Base64.getEncoder().encodeToString(cipherText).getBytes());
                fos.write("\n".getBytes());
                fos.write("iv:".getBytes());
                fos.write(iv.getIV());

                fos.flush();
                fos.close();
                break;
            }
        }
        notification(dir,name,fileName);

        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Image uploaded to the server "+name+"</H1>");
        out.println("<p>Uploaded Image Directory:"+dir+"\\"+name+"</p>");
        out.println("<p>Uploaded Image Name:"+fileName+"</p>");
        out.flush();
        socket.close();
    }
    // Notification file part
    private void notification(String dir, String name, String fileName) throws IOException {
        String dir2;
        for (String s:onlineUsers){
            if (!s.equals(name)){

                dir2=dir+"\\"+s;
                File file=new File(dir2+"\\notifications.txt");
                file.createNewFile();

                FileOutputStream fos=new FileOutputStream(file,true);
                fos.write("image:".getBytes());
                fos.write(fileName.getBytes());
                fos.write("\s".getBytes());
                fos.write("owner:".getBytes());
                fos.write(name.getBytes());
                fos.write("\n".getBytes());
                fos.flush();
                fos.close();
            }
        }
    }

    // aes key generation
    private SecretKey createAESKey(int bit)throws Exception{
        SecureRandom securerandom = new SecureRandom();
        KeyGenerator keygenerator  = KeyGenerator.getInstance("AES");
        keygenerator.init(bit, securerandom);
        return keygenerator.generateKey();
    }
    // Encrypting image with AES CBC
    public IvParameterSpec encryptAESCBC(SecretKey key, String fileDir) throws Exception{
        byte[] iv = generateIv();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        try
        {
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec );

            CipherInputStream cipherInputStream=new CipherInputStream(new FileInputStream(fileDir), cipher);

            FileOutputStream fileOutputStream=new FileOutputStream(fileDir);

            int i;
            while((i=cipherInputStream.read())!=-1){
                fileOutputStream.write(i);
            }
//            cipher.init(Cipher.DECRYPT_MODE, key ,ivParameterSpec);
//
//            CipherInputStream ciptt=new CipherInputStream(new FileInputStream(fileDir), cipher);
//
//            FileOutputStream fop=new FileOutputStream(fileDir);
//
//            int j;
//            while((j=ciptt.read())!=-1)
//            {
//                fop.write(j);
//            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ivParameterSpec;
    }
    // generating IV
    private  byte[] generateIv() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return iv;
    }
    // upload page html
    private void uploadPage(PrintWriter out, Socket socket) throws IOException {

        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Upload page</H1>");
        out.println("""

                <form action="FileServlet">
                Select File to Upload:<input type="file" name="fileName">
                <br>
                <input type="submit" value="Submit">
                </form>
                """);
        out.flush();
        socket.close();
    }
    // registered user check
    private void fileProcesses(File registeredUsers, String name, PrintWriter out, Socket socket,File certificates) throws Exception {
        FileInputStream in=new FileInputStream(registeredUsers);
        Scanner scanner=new Scanner(in);
        boolean check=false;
        while (scanner.hasNextLine()){

            if (name.equals(scanner.nextLine().split("\s")[0])){
                out.println("HTTP/1.0 200 OK");
                out.println("Content-Type: text/html");
                // this blank line signals the end of the headers
                out.println("");
                // Send the HTML page
                out.println("<H1>Error!! That user has already registered!!</H1>");
                out.println("<H2>Please return to the homepage</H2>");
                out.flush();
                socket.close();
                check=true;
                break;
            }
        }
        if (!check){
            registerCheck(out,socket,name,certificates,registeredUsers);
        }

        scanner.close();

    }
    // file search for username and password
    private boolean fileSearch(File file,String name,String password) throws FileNotFoundException {
        FileInputStream inputStream=new FileInputStream(file);

        Scanner scanner=new Scanner(inputStream);
        while (scanner.hasNextLine()){
            String line=scanner.nextLine().trim();
            String a=line.split("\s")[0];
            String b=line.split("\s")[1];
            if (name.equals(a)&&password.equals(b)){

                scanner.close();
                return true;
            }
        }
        scanner.close();
        return false;
    }
    // home page html
    private void home(PrintWriter out, Socket socket) throws IOException {
        // Send the response
        // Send the headers
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Welcome to the system!</H1>");
        out.println("<a href="+"\""+pageLink+"\\register"+"\""+">Register</a><br>");
        out.println("<a href="+"\""+pageLink+"\\login"+"\""+">Login</a><br>");
        out.flush();
        socket.close();
    }
    // register page html
    private void registerPage(PrintWriter out, Socket socket) throws IOException {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Register Page</H1>");
        out.println("<H3>Only letters and numbers will be accepted!!</H3>");
        out.println("""
                <form action="/add_user">
                  <label for="fname">Name:</label>
                  <input type="text" id="fname" name="fname" <br>
                  <input type="submit" value="Submit">
                </form>\s""");
        out.flush();
        socket.close();
    }
    // login page html
    private void loginPage(PrintWriter out, Socket socket) throws IOException {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Login Page</H1>");
        out.println("""
                <form action="/online_session">
                  <label for="fname">Name:</label>
                  <input type="text" id="fname" name="fname" <br>
                  <label for="password">Password:</label>
                  <input type="text" id="password" name="password" <br>
                  <input type="submit" value="Submit">
                </form>\s""");
        out.flush();
        socket.close();
    }
    // user page html and notifications part
    private void userPage(PrintWriter out, Socket socket, String name) throws IOException {
        File file=new File(dir+"\\"+name+"\\notifications.txt");
        boolean fileCheck=file.exists();
        Scanner scanner;


        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Welcome "+name+"!</H1>");
        out.println("<a href="+"\""+pageLink+"\\upload"+"\""+">Upload</a><br>");
        out.println("<a href="+"\""+pageLink+"\\download"+"\""+">Download</a><br>");
        out.println("<br><br><br>");
        out.println("Notifications");
        out.println("<br>");

        if (fileCheck) {
            scanner=new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                out.println(line);
                out.println("<br>");
            }
        }
        else out.println("No notifications");
        out.flush();
        socket.close();
    }
    // checking the user registered or not
    private void registerCheck(PrintWriter out, Socket socket, String name, File certificateFile, File registeredUsers) throws Exception {

        FileOutputStream fos = new FileOutputStream(registeredUsers, true);
        KeyPair keyPair=keyPair();
        String password=generateRandomPassword();
        PrivateKey privateKey=keyPair.getPrivate();
        PublicKey publicKey=keyPair.getPublic();
        String certificate=sign(name,privateKey);
        signedString=certificate;
        boolean check=verify(name,signedString,publicKey);

        if (check) {
            fos.write(name.getBytes());
            fos.write("\s".getBytes());
            fos.write(password.getBytes());
            fos.write("\s".getBytes());
            fos.write(Base64.getEncoder().encodeToString(publicKey.getEncoded()).getBytes());
            fos.write("\s".getBytes());
            fos.write(Base64.getEncoder().encodeToString(privateKey.getEncoded()).getBytes());
            fos.write("\n".getBytes());
            fos.close();

            FileOutputStream fos2 = new FileOutputStream(certificateFile, true);
            fos2.write(name.getBytes());
            fos2.write("\s".getBytes());
            fos2.write(password.getBytes());
            fos2.write("\s".getBytes());
            fos2.write(certificate.getBytes());
            fos2.write("\n".getBytes());
            fos2.close();

            out.println("HTTP/1.0 200 OK");
            out.println("Content-Type: text/html");
            // this blank line signals the end of the headers
            out.println("");
            // Send the HTML page
            out.println("<H1>Have fun in the system "+name+"!</H1>");
            out.println("<p>Your public key: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"</p>");
            out.println("<br><p>Your certificate is: "+certificate+"</p>");

            out.println("<br><H2>Your password: "+password+"</H2>");
            out.println("<br><H2>Certificate check: "+check+"</H2>");

            out.println("<br><H2>Use that password for logging into the system!!!</H2>");
            out.println("<H2>Return to the homepage to login</H2>");
            out.flush();
            socket.close();
        }
        else errorPage3(out,socket);

    }
    // error page for invalid requests
    private void errorPage(PrintWriter out, Socket socket) throws IOException {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Error!! Page not found!</H1>");
        out.println("<H2>Please return to the homepage</H2>");
        out.flush();
        socket.close();
    }
    // error page for valid user or not
    private void errorPage2(PrintWriter out, Socket socket) throws IOException {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>incorrect username or password</H1>");
        out.println("<H2>Please return to the homepage</H2>");
        out.flush();
        socket.close();
    }
    // error page for invalid certificate
    private void errorPage3(PrintWriter out, Socket socket) throws IOException {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Certificate Error</H1>");
        out.println("<H2>Please return to the homepage</H2>");
        out.flush();
        socket.close();
    }
    // error page for restricted actions
    private void errorPage4(PrintWriter out, Socket socket) throws IOException {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Restricted Area</H1>");
        out.println("<H2>Please return to the homepage</H2>");
        out.flush();
        socket.close();
    }
    // signing the certificate
    public String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes());

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }
    // verifying that the registered user certificate is valid or not
    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes());

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }


    private KeyPair keyPair() throws NoSuchAlgorithmException {
        return generateKeyPair();
    }
    // Generating key pair
    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator=KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        return generator.generateKeyPair();
    }

    // Generating random password for users to login the system
    private String generateRandomPassword(){
        final String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++){
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

}
