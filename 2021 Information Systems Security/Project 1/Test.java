import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;

public class Test {

	static PrivateKey privateKey; // holds the private key of RSA key pair
    static PublicKey publicKey;// holds the public key of RSA key pair
    public static void main(String[] args) throws Exception {
    	//First generate a public/private key pair
        SecretKey SymmetricKey_128 = createSymmetricAESKey(128);
        SecretKey SymmetricKey_256 = createSymmetricAESKey(256);
//        System.out.println("The SymmetricKey_128 Key is :"+ DatatypeConverter.printHexBinary(SymmetricKey_128.getEncoded()));
//        System.out.println("The SymmetricKey_256 Key is :"+ DatatypeConverter.printHexBinary(SymmetricKey_256.getEncoded()));

        encryptAndDecrypt(SymmetricKey_128 ,"SymmetricKey_128");
        encryptAndDecrypt(SymmetricKey_256,"SymmetricKey_256");

        messageDigest();

        question4(SymmetricKey_128,SymmetricKey_256);

    }
    // ***************************** QUESTION-1 *****************************************
    // KEY PAIR GENERATOR FUNCTION
    // RSA key pair generation with the key size of 2048 and the function returns the key pair.
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        return pair;
    }
    // ***************************** QUESTION-2 *****************************************
    // SYMMETRIC AES KEY GENERATOR FUNCTION
    // AES key generation for Question-2
    public static SecretKey createSymmetricAESKey(int bit)throws Exception
    {
        SecureRandom securerandom = new SecureRandom();
        KeyGenerator keygenerator  = KeyGenerator.getInstance("AES");
        keygenerator.init(bit, securerandom);
        SecretKey key = keygenerator.generateKey();
        return key;
    }
    // ***************************** QUESTION-2 *****************************************
    // RSA ENCRYPTION FUNCTION
    // Function Parameters:
    // String plaintext: plaintext that will be encrypted
    // PublicKey publicKey: public key for encryption
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes());

        return Base64.getEncoder().encodeToString(cipherText);
    }
    // ***************************** QUESTION-2 *****************************************
    // RSA DECRYPTION FUNCTION
    // Function Parameters:
    // String cipherText: cipher text that will be decrypted
    // PrivateKey privateKey: private key for decryption
    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decryptCipher.doFinal(bytes));
    }
    // ***************************** QUESTION-2 *****************************************
    // ENCRYPTION & DECRYPTION FUNCTION
    // Function parameters:
    // SecretKey mes: Symmetric key
    // String name: name variable that keeps the name of secret key eg 128 bit, 256 bit
    public static void encryptAndDecrypt(SecretKey mes , String name) throws Exception{
        System.out.println("----------------- "+name.substring(13)+" bit Symmetric Key ---------------------");
        System.out.println("Key "+name+":\n" +DatatypeConverter.printHexBinary(mes.getEncoded())+"\n");
        KeyPair pair = generateKeyPair();
        //Our secret message
        String message = DatatypeConverter.printHexBinary(mes.getEncoded());

        //Encrypt the message
        String cipherText = encrypt(message, pair.getPublic());
        System.out.println("CipherText "+ name+":\n" + cipherText+"\n");
        //Now decrypt it
        String decryptedMessage = decrypt(cipherText, pair.getPrivate());

        System.out.println("Decrypted Message "+name+":\n"+decryptedMessage+"\n");

        System.out.println("Is plaintext and decrypted message equal? \n" +decryptedMessage.equals(message)+"\n");
        privateKey=pair.getPrivate();
        publicKey=pair.getPublic();
    }
    // ***************************** QUESTION-3 *****************************************
    // In that part, there is a file called message.txt and it contains 5 paragraphs of lorem ipsum
    //
    // First of all, we create a message digest object instance of SHA-256 algorithm.
    // After that, message hash holds the file byte by byte. Then we create a cipher instance to encrypt the file
    // with RSA. As a result of that, the digital signature saved to a file.
    // After all the operations above, we decrypt the file with the public key.
    // Lastly, we control the file with the decrypted message. If it is correct, prints true to the console. Otherwise, prints false.
    public static void messageDigest() throws Exception {
        System.out.println("------------------------- Question-3 -------------------------");
        byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageHash = md.digest(messageBytes);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] digitalSignature = cipher.doFinal(messageHash);
        Files.write(Paths.get("digital_signature_1"), digitalSignature);

        byte[] encryptedMessageHash = Files.readAllBytes(Paths.get("digital_signature_1"));
        Cipher cipher2 = Cipher.getInstance("RSA");
        cipher2.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessageHash = cipher2.doFinal(encryptedMessageHash);

        byte[] messageBytes2 = Files.readAllBytes(Paths.get("message.txt"));

        MessageDigest md2 = MessageDigest.getInstance("SHA-256");
        byte[] newMessageHash = md2.digest(messageBytes2);

        boolean isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);
        System.out.println("Is decrypted message and the file content equal?\n"+isCorrect+"\n");
        // m
        System.out.println("File content\n"+new String(messageBytes, StandardCharsets.UTF_8)+"\n");
        // h(m)
        System.out.println("\nH(m)\n"+DatatypeConverter.printHexBinary(messageHash));
        // digital signature
        System.out.println("\nDigital Signature\n"+DatatypeConverter.printHexBinary(digitalSignature)+"\n");

    }
    // ***************************** QUESTION-4 *****************************************
    // In that question, we create a new java file and import it to the project.
    // After the import, we create an instance and we print some information about the process.
    // Encrypted file is an image file.
    public static void question4(SecretKey key1,SecretKey key2){
        q4 q4 = new q4();
        long EncryptionStartTime;
        long EncryptionEndTime;
        long totalTime;
        System.out.println("------------------------- Question-4 -------------------------");


        // 128 bit CBC
        System.out.println("\n-- Key Size: 128 bit / Mode: CBC --");
        EncryptionStartTime = System.nanoTime();
        q4.actionCBC(key1);
        EncryptionEndTime   = System.nanoTime();
        totalTime = EncryptionEndTime - EncryptionStartTime;
        System.out.println("Total time elapsed in nano seconds: "+totalTime);


        // 256 bit CBC
        System.out.println("\n-- Key Size: 256 bit / Mode: CBC --");
        EncryptionStartTime = System.nanoTime();
        q4.actionCBC(key2);
        EncryptionEndTime   = System.nanoTime();
        totalTime = EncryptionEndTime - EncryptionStartTime;
        System.out.println("Total time elapsed in nano seconds: "+totalTime);


        // 256 bit CTR
        System.out.println("\n-- Key Size: 256 bit / Mode: CTR --");
        EncryptionStartTime = System.nanoTime();
        q4.actionCTR(key2);
        EncryptionEndTime   = System.nanoTime();
        totalTime = EncryptionEndTime - EncryptionStartTime;
        System.out.println("Total time elapsed in nano seconds: "+totalTime);
    }
}