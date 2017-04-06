package pwd.manager.safe;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;

import pwd.manager.hibernate.model.QuerriesService;
import pwd.manager.hibernate.model.impl.Querries;



public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		
		Model model = new Model();
		
		String AESKey = "alex123"; 
		byte[] byteKey = model.hashSHA(AESKey);
		
		System.out.println(AESKey);

		byte[] encPwd = Encryption.AESencryption(byteKey , "123456");  
		
		String encrypted = Encryption.byteArrayToHexString(encPwd); 
		
		
		
	
		
		String dbCommand = "update"; 
		ServiceRegistry serviceRegistry = null;
		
		serviceRegistry= model.getServiceRegistry(dbCommand); 
	
		
		SessionFactory factory = null;
	try {
		factory = model.createSessionFactory(serviceRegistry);
		QuerriesService service = new Querries(factory);
		String user = "alex@gmail.comr";
		String pass = encrypted;
		try {
			service.addNewAcc(user, pass , "descr" , "hint1");
			String message = "Account successfully made";
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
		
		
		
	
		
		/*byte[] hexKey = model.hashSHA(AESKey);
		
		System.out.println(AESKey);

		byte[] encPwd = Encryption.AESencryption(hexKey , "123456");  
		
		String encrypted = Encryption.byteArrayToHexString(encPwd); 
		
		//System.out.println(encPwd);
		
		String decPwd = Decryption.AESdecryption(hexKey, encPwd); 
		System.out.println(decPwd);*/
	}
	
	
}
