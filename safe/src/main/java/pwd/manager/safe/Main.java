package pwd.manager.safe;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import pwd.manager.hibernate.model.QuerriesService;
import pwd.manager.hibernate.model.impl.Querries;



public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		
		Model model = new Model();
		
		/*Argon2 argon2 = Argon2Factory.create();
		String hash = argon2.hash(10, 65536, 2, "Hello");
		System.out.println(hash);*/
		
		String AESKey = "alex123"; 
		byte[] byteKey = model.SHA256(AESKey);
				//model.hashSHA(AESKey);
		
		System.out.println(AESKey);

		byte[] encPwd = Encryption.AESencryption(byteKey , "vasile1993");  
		
		String encrypted = Model.toHexString(encPwd); 
		
		String sha2 = Model.toHexString(byteKey)  ;
		System.out.println("hashed with SHA256 : " + sha2);
		

		
		System.out.println(Arrays.equals(Model.toByteArray(encrypted), encPwd));
		
	//	byte[] hexKey = model.hashSHA(AESKey);
		
	//	System.out.println(AESKey);

	//	byte[] encPwd = Encryption.AESencryption(hexKey , "123456");  
		
	//	String encrypted = Encryption.byteArrayToHexString(encPwd); 
		
	//	System.out.println(encPwd);
		
	/*	String decPwd = Decryption.AESdecryption(hexKey, encrypted); 
		System.out.println("dec value : " + decPwd);*/
		
		
		String dbCommand = "update"; 
		ServiceRegistry serviceRegistry = null;
		
		serviceRegistry= model.getServiceRegistry(dbCommand); 
	
		String passDB = null;
		
		SessionFactory factory = null;
	try {
		factory = model.createSessionFactory(serviceRegistry);
		QuerriesService service = new Querries(factory);
		String user = "vasileioionita@gmail.com";
		String pass = encrypted;

		try {
			/*service.addNewAcc(user, pass , "descr" , "hint1");
			String message = "Account successfully made";
			System.out.println(message);*/
			//service.changePassword(user, pass, pass);
		//	String message = null;

			//message = "Pass successfully changed";
			passDB = service.showPass(user); 
			String message = "Pass successfully taken ";

			System.out.println(message);
		} catch (IllegalArgumentException e) {
			String message = e.getMessage();
			System.out.println(message);
		} catch (Exception e){
			String message = e.getMessage();
			System.out.println(message);
		}
	
	} finally {
		if(factory != null && !factory.isClosed()) {
			factory.close();
			}
		}
		
		
	String decPwd = Decryption.AESdecryption(byteKey, passDB); 
	System.out.println("dec value vrom DB : " + decPwd);
	
		
		
	}
	
	
}
