package pwd.manager.safe;

import java.security.NoSuchAlgorithmException;
import java.util.Vector;

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
			
					Integer lastID =service.addNewAcc(accname, descr, hint, user);
					
					String IV = Integer.toString(lastID);
					System.out.println("accID : " + IV);
					while(IV.length() < 16 )
					{
						IV = IV + "0"; 
					}
					newpassword = Encryption.AESencryption2(userpass, IV, pass);
					System.out.println("Sefuuuule");
					service.addAccPassword(lastID, newpassword);	
					String message = "Successfully created user";
					System.out.println(Decryption.AESdecryption2(userpass, IV, newpassword));
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
	
	public Vector<String> getAccounts(String username){
		Vector<String> accounts = new Vector<String>();
		
				
				model = new Model();
		 dbCommand = "update"; 
		 
		serviceRegistry= model.getServiceRegistry(dbCommand); 
		SessionFactory factory = null;
		try {
			factory = model.createSessionFactory(serviceRegistry);
			QuerriesService service = new Querries(factory);
			try {
				
				accounts =service.getAllAccounts(username); 
			} catch (Exception e){
				String message = e.getMessage();
				System.out.println(message);
			}
		
		} finally {
			if(factory != null && !factory.isClosed()) {
				factory.close();
				}
			}
		return accounts;
	}
	
    public static void main( String[] args )
    {
       // System.out.println( "Hello World!" );
    }
}
