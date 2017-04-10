package pwd.manager.safe;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;   



public class Encryption {

	private static String salt = "MfyP2017"; 
	
	public static String AESencryption(String password , String plainText){
		
			
		 byte[] cipherText = null; 
		 	String printablecipherText = null ;  
		 
		       try {
			 
		    	   
		  
		 		  byte[] key=   hexStringToByteArray(password);

		    	  
		    	   String s=   byteArrayToHexString(key); 
		    	   SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
		    	   
		    	
		    	   System.out.println(s);
			   //Create the Key Object
			 //  Key aesKey = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");
			 
			   //Initiate the cipher object for encryption  
		    	//   "ECB" is the default Cipher Mode and "PKCS5Padding" the default 
			   Cipher encAEScipher = Cipher.getInstance("AES");
			   encAEScipher.init(Cipher.ENCRYPT_MODE, aesKey);
			   
			   // Encrypt the plain text
			   cipherText = encAEScipher.doFinal(plainText.getBytes());
			   
			   // Ciphertext as hex.
		      printablecipherText = byteArrayToHexString(cipherText);
			   
		      //Byte as Base64
		     // String base64 = base64Encode(cipherText);
		      
			   System.out.println("Cipher text: "+printablecipherText);
			  // System.out.println("Cipher text as base64 : "+ base64);
			   
			 byte[] backtobyte = hexStringToByteArray(printablecipherText);
			  if(Arrays.equals(cipherText, backtobyte)) System.out.println("yaaay12345");
			   
			 /*  String originalString = new String(cipherText);
			    System.out.println("Original text: " + originalString);*/

		       } catch (Exception e){
			   System.out.println("doh");
		       } 
		   

		
		
		return printablecipherText; 
	}
	

	public static String AESencryption2(String password , String IV,  String plainText) throws NoSuchAlgorithmException, InvalidKeySpecException{
		
		String printablecipherText = null;
		byte [] cipherText = null; 
		 try {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes("UTF-8"), 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		
		 IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
         System.out.println("Encrypt" + password  + "" + IV); 

		 Cipher encAEScipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		 encAEScipher.init(Cipher.ENCRYPT_MODE, secret, iv);

		/* Cipher encAEScipher = Cipher.getInstance("AES");
		   encAEScipher.init(Cipher.ENCRYPT_MODE, secret);*/
		   
		   // Encrypt the plain text
		   cipherText = encAEScipher.doFinal(plainText.getBytes());
		   
		   // Ciphertext as hex.
	      printablecipherText = byteArrayToHexString(cipherText);
		   
	      //Byte as Base64
	     // String base64 = base64Encode(cipherText);
	      
		   System.out.println("Cipher text: "+printablecipherText);
		  // System.out.println("Cipher text as base64 : "+ base64);
		   
		 byte[] backtobyte = hexStringToByteArray(printablecipherText);
		  if(Arrays.equals(cipherText, backtobyte)) System.out.println("yaaay12345");
		
		 } catch (Exception e){
			   System.out.println(e);
		       } 
		   
		
		return printablecipherText ; 
		
	
		
	}
	
	
	

    public static String byteArrayToHexString(byte[] data) {
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
 
   public static byte[] hexStringToByteArray(String s) {
	int len = s.length();
	byte[] data = new byte[len / 2];
	for (int i = 0; i < len; i += 2) {
	    data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
				  + Character.digit(s.charAt(i+1), 16));
	}
        return data;
    }
   
	@SuppressWarnings("restriction")
	public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}

	@SuppressWarnings("restriction")
	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}

   @SuppressWarnings({ "restriction", "unused" })
private static String base64Encode(byte[] bytes)
   {
       return new BASE64Encoder().encode(bytes);
   }

   @SuppressWarnings({ "restriction", "unused" })
private static byte[] base64Decode(String s) throws IOException
   {
       return new BASE64Decoder().decodeBuffer(s);
   }

}
