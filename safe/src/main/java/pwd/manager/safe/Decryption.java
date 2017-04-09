package pwd.manager.safe;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Decryption {
	
	public static String AESdecryption(String password, String encrypted) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException {
			
		
	//byte[] enc = 	hexStringToByteArray(encrypted);
		
		  byte[] key=   hexStringToByteArray(password);
   	 //  String s=   byteArrayToHexString(key); 
   	 //  System.out.println("Decryption pwd: " + s);
   	   SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
	        Cipher c = null;
			try {
				c = Cipher.getInstance("AES");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        c.init(Cipher.DECRYPT_MODE, aesKey);
	        byte[] enc = toByteArray(encrypted);
	        byte[] cipherText = c.doFinal(enc);
			   
			   // Ciphertext as hex.
			// String   decryptedValue = byteArrayToHexString(cipherText);
			 //  System.out.println("Cipher text: "+decryptedValue);
			   
			   String originalString = new String(cipherText);
			 //   System.out.println("Original string: " + originalString);

	        return originalString;
	        
	}
	@SuppressWarnings("restriction")
	public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}

	@SuppressWarnings("restriction")
	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}
    private static String byteArrayToHexString(byte[] data) {
	StringBuffer buf = new StringBuffer();
	for (int i = 0; i < data.length; i++) {
	    int halfbyte = (data[i] >>> 4) & 0x0F;
	    int two_halfs = 0;
	    do {
		if ((0 <= halfbyte) && (halfbyte <= 9))
		    buf.append((char) ('0' + halfbyte));
		else
		    buf.append((char) ('a' + (halfbyte - 10)));
		halfbyte = data[i] & 0x0F;
	    } while(two_halfs++ < 1);
	}
	return buf.toString();
    }
 
   private static byte[] hexStringToByteArray(String s) {
	int len = s.length();
	byte[] data = new byte[len / 2];
	for (int i = 0; i < len; i += 2) {
	    data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
				  + Character.digit(s.charAt(i+1), 16));
	}
        return data;
    }

}
