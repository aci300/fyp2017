package pwd.manager.safe;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		
		
		Model model = new Model();
		
		/*Argon2 argon2 = Argon2Factory.create();
		String hash = argon2.hash(10, 65536, 2, "Hello");
		System.out.println(hash);*/
		
		String AESKey = "alex123"; 
		byte[] byteKey = model.SHA256(AESKey);
				//model.hashSHA(AESKey);
		
		System.out.println(AESKey);
		String SHA256Pass = Model.toHexString(byteKey); 
		
		String encPwd = Encryption.AESencryption(SHA256Pass , "vasile1231993");  
		
		System.out.println("hashed with SHA256 : " + SHA256Pass);
		
		String dbCommand = "create"; 
		ServiceRegistry serviceRegistry = null;
		
		//serviceRegistry= model.getServiceRegistry(dbCommand); 
	
		String passDB = null;
		
		SessionFactory factory = null;
	try {
		factory = model.createSessionFactory(serviceRegistry);
		//QuerriesService service = new Querries(factory);
		/*String user = "vasileioionita2@gmail.com";
		String pass = encPwd;*/

		try {
			/*service.addNewAcc(user, pass , "descr2" , "hint2");
			String message = "Account successfully made";
			System.out.println(message);
			//service.changePassword(user, pass, pass);
		//	String message = null;

			//message = "Pass successfully changed";
			passDB = service.showPass(user); 
			 message = "Pass successfully taken ";

			System.out.println(message);*/
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
		
	String enccPwd = Encryption.AESencryption2("cata", "8000000000000000", "123");	
	//String decPwd = Decryption.AESdecryption2("cata", "8000000000000000", "fe276480204a35f28aedc1d53181b9ac");
	System.out.println("dec value vrom DB : " + enccPwd);
	
		
		
	}
	
	
}
