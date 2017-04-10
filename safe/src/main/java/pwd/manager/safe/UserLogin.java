package pwd.manager.safe;

import java.security.NoSuchAlgorithmException;

import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;

import pwd.manager.hibernate.model.QuerriesService;
import pwd.manager.hibernate.model.impl.Querries;

/**
 * Hello world!
 *
 */
public class UserLogin 
{
	private Model model = null; 
	private String dbCommand = "update";
	private ServiceRegistry serviceRegistry = null;
	private String currPass= null; 

	
	public void login(String username, String pass) throws NoSuchAlgorithmException{
		
		model = new Model();
		 dbCommand = "update"; 
		 
		serviceRegistry= model.getServiceRegistry(dbCommand); 
		SessionFactory factory = null;
		try {
			factory = model.createSessionFactory(serviceRegistry);
			QuerriesService service = new Querries(factory);
		//	String user = "vasileioionita2@gmail.com";
			String hashPass = Model.toHexString(model.SHA256(pass));
			System.out.println(hashPass);
			try {
					if (service.checkUserPass(username, hashPass))
					{	currPass = pass; 
					String message = "Successfully loged in";  
					System.out.println(message);}
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
			
	}
	
	public void createUser(String username, String pass) throws NoSuchAlgorithmException{
		
		model = new Model();
		 dbCommand = "update"; 
		 
		serviceRegistry= model.getServiceRegistry(dbCommand); 
		SessionFactory factory = null;
		try {
			factory = model.createSessionFactory(serviceRegistry);
			QuerriesService service = new Querries(factory);
		//	String user = "vasileioionita2@gmail.com";
			String hashPass = Model.toHexString(model.SHA256(pass));	
			try {
					service.addNewUser(username, hashPass);
						
					String message = "Successfully created user";
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
			
	}
	
	
public void createAccount(String accname, String pass,String user,String userpass, String descr, String hint) throws NoSuchAlgorithmException{
		
		model = new Model();
		 dbCommand = "update"; 
		 
		serviceRegistry= model.getServiceRegistry(dbCommand); 
		SessionFactory factory = null;
		try {
			factory = model.createSessionFactory(serviceRegistry);
			QuerriesService service = new Querries(factory);
		//	String user = "vasileioionita2@gmail.com";
		//	String hashPass = Model.toHexString(model.SHA256(pass));
			String newpassword = null; 
			
			try {
			//	String userID = service.getUserID(user);
				Integer lastID =service.addNewAcc(accname, descr, hint, user);
					// = service.lastAccID(accname, user); 
					String IV = Integer.toString(lastID);
					while(IV.length() < 16 )
					{
						IV = IV + "0"; 
					}
					newpassword = Encryption.AESencryption2(userpass, IV, pass);
					service.addAccPassword(accname, newpassword);	
					String message = "Successfully created user";
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
			
	}
	
	
	
    public static void main( String[] args )
    {
       // System.out.println( "Hello World!" );
    }
}
