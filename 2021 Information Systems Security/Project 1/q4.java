import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
public class q4 {
    public static byte[] generateIv() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return iv;
    }

	public static void encryptionDecryptionWithAesCbc(SecretKey key) throws Exception
	{
        byte[] iv = generateIv();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		try
		{
			Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec );

			CipherInputStream cipt=new CipherInputStream(new FileInputStream(new File("test.jpg")), cipher);

			FileOutputStream fip=new FileOutputStream(new File("testEnc.jpg"));

			int i;
			while((i=cipt.read())!=-1)
			{
				fip.write(i);

			}
			cipher.init(Cipher.DECRYPT_MODE, key ,ivParameterSpec);

			CipherInputStream ciptt=new CipherInputStream(new FileInputStream(new File("testEnc.jpg")), cipher);

			FileOutputStream fop=new FileOutputStream(new File("testDec.jpg"));

			int j;
			while((j=ciptt.read())!=-1)
			{
				fop.write(j);
			}
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
	}
	public static void encryptionDecryptionWithAesCTR(SecretKey key) throws Exception
	{

		SecureRandom secureRandom = new SecureRandom();
		// First, create the cipher
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
		// Then generate the key. Can be 128, 192 or 256 bit
		byte[] keyByte = new byte[256 / 8];
		secureRandom.nextBytes(keyByte);
		// Now generate a nonce. You can also use an ever-increasing counter, which is even more secure. NEVER REUSE A NONCE!
		byte[] nonce = new byte[96 / 8];
		secureRandom.nextBytes(nonce);
		byte[] iv = new byte[128 / 8];
		System.arraycopy(nonce, 0, iv, 0, nonce.length);

		IvParameterSpec ivSpec = new IvParameterSpec(iv);

		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec );

		CipherInputStream cipt=new CipherInputStream(new FileInputStream(new File("ctr.jpg")), cipher);

		FileOutputStream fip=new FileOutputStream(new File("ctrEnc.jpg"));

		int i;
		while((i=cipt.read())!=-1)
		{
			fip.write(i);

		}
		cipher.init(Cipher.DECRYPT_MODE, key ,ivSpec);

		CipherInputStream ciptt=new CipherInputStream(new FileInputStream(new File("ctrEnc.jpg")), cipher);

		FileOutputStream fop=new FileOutputStream(new File("ctrDec.jpg"));

		int j;
		while((j=ciptt.read())!=-1)
		{
			fop.write(j);
		}
	}

	public void actionCBC(SecretKey key)
	{
		try
		{
			encryptionDecryptionWithAesCbc(key);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("CBC finished.");

	}
	public void actionCTR(SecretKey key){

		try
		{
			encryptionDecryptionWithAesCTR(key);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("CTR finished.");
	}
}