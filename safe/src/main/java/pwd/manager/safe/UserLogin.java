package pwd.manager.safe;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Vector;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;

import pwd.manager.hibernate.model.Database;
import pwd.manager.hibernate.model.QuerriesService;
import pwd.manager.hibernate.model.impl.Querries;

/**
 * Hello world!
 *
 */
public class UserLogin {
	private Model model = null;
//	private Database db = null; 
	private String dbCommand = "create";
	private String userName = "postgres"; 
	private String password = "banokipo"; 
	private String url = "jdbc:postgresql://localhost:5432/PwdManager"; 
	private ServiceRegistry serviceRegistry = null;
	private String currPass = null;
	private String currUser = null;
	private Vector<String> accounts = new Vector<String>();

/*	public UserLogin(Database newdb){
		this.db = newdb; 
	}*/
	public void setDBSettings(String user , String pass , String url)
	{
		this.userName = user; 
		this.password = pass; 
		this.url = url ; 
	}
	
	public void login(String username, String pass) throws NoSuchAlgorithmException, InvalidKeySpecException {

		System.out.println(this.userName + this.password + this.url);
		model = new Model();
		dbCommand = "update";
		

		serviceRegistry = model.getServiceRegistry(this.userName , this.password, this.url,  dbCommand);
		SessionFactory factory = null;
		try {
			factory = model.createSessionFactory(serviceRegistry);
			QuerriesService service = new Querries(factory);
			// String user = "vasileioionita2@gmail.com";
			String hashPass = Model.hashPassword(pass);
			System.out.println(hashPass);
			try {
				if (service.checkUserPass(username, hashPass)) {
					currPass = pass;
					currUser = username;
					String message = "Successfully loged in";
					System.out.println(message);
				} else
					throw new IllegalArgumentException("Incorrect password!");
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);

			} catch (Exception e) {
				String message = e.getMessage();
				System.out.println(message);
			}

		} finally {
			if (factory != null && !factory.isClosed()) {
				factory.close();
			}
		}

	}

	public void createUser(String username, String pass) throws NoSuchAlgorithmException, InvalidKeySpecException {

		model = new Model();
		dbCommand = "update";

		serviceRegistry = model.getServiceRegistry(userName , password, url,  dbCommand);

		//serviceRegistry = model.getServiceRegistry(dbCommand);
		SessionFactory factory = null;
		try {
			factory = model.createSessionFactory(serviceRegistry);
			QuerriesService service = new Querries(factory);
			// String user = "vasileioionita2@gmail.com";
			String hashPass = Model.hashPassword(pass);
			try {
				service.addNewUser(username, hashPass);

				String message = "Successfully created user";
				System.out.println(message);
			} catch (IllegalArgumentException e) {
				String message = e.getMessage();
				System.out.println(message);
			} catch (Exception e) {
				String message = e.getMessage();
				System.out.println(message);
			}

		} finally {
			if (factory != null && !factory.isClosed()) {
				factory.close();
			}
		}

	}

	public void createAccount(String accname, String pass, String user, String userpass, String descr, String hint)
			throws NoSuchAlgorithmException {

		model = new Model();
		dbCommand = "update";

		serviceRegistry = model.getServiceRegistry(userName , password, url,  dbCommand);

		//serviceRegistry = model.getServiceRegistry(dbCommand);
		SessionFactory factory = null;
		try {
			factory = model.createSessionFactory(serviceRegistry);
			QuerriesService service = new Querries(factory);
			// String user = "vasileioionita2@gmail.com";
			// String hashPass = Model.toHexString(model.SHA256(pass));
			String newpassword = null;

			try {

				Integer lastID = service.addNewAcc(accname, descr, hint, user);

				String IV = Integer.toString(lastID);
				System.out.println("accID : " + IV);
				while (IV.length() < 16) {
					IV = IV + "0";
				}
				// String hashPass = Model.hashPassword(userpass+IV);
				newpassword = Encryption.AESencryption2(userpass, IV, pass);
				System.out.println("Sefuuuule");
				service.addAccPassword(lastID, newpassword);
				String message = "Successfully created user";
				System.out.println(Decryption.AESdecryption2(userpass, IV, newpassword));
				System.out.println(message);
			} catch (IllegalArgumentException e) {
				String message = e.getMessage();
				System.out.println(message);
			} catch (Exception e) {
				String message = e.getMessage();
				System.out.println(message);
			}

		} finally {
			if (factory != null && !factory.isClosed()) {
				factory.close();
			}
		}

	}

	public String displayDecryptedPassbyIndex(int index) {

		String decPwd = null;
		String account = null;

		account = accounts.get(index);

		decPwd = showAccPass(account, currUser);

		return decPwd;

	}

	public Vector<String> getAccounts(String username) {

		model = new Model();
		dbCommand = "update";

		serviceRegistry = model.getServiceRegistry(userName , password, url,  dbCommand);

		//serviceRegistry = model.getServiceRegistry(dbCommand);
		SessionFactory factory = null;
		try {
			factory = model.createSessionFactory(serviceRegistry);
			QuerriesService service = new Querries(factory);
			try {

				accounts = service.getAllAccounts(username);
			} catch (Exception e) {
				String message = e.getMessage();
				System.out.println(message);
			}

		} finally {
			if (factory != null && !factory.isClosed()) {
				factory.close();
			}
		}
		for (int i = 0; i < accounts.size(); i++) {
			System.out.println("account: " + accounts.get(i));
		}
		return accounts;
	}

	public String showAccPass(String account, String user) {
		String decrPass = null;
		model = new Model();
		dbCommand = "update";

		serviceRegistry = model.getServiceRegistry(userName , password, url,  dbCommand);

		//serviceRegistry = model.getServiceRegistry(dbCommand);
		SessionFactory factory = null;
		try {
			factory = model.createSessionFactory(serviceRegistry);
			QuerriesService service = new Querries(factory);
			String newpassword = null;

			try {
				String IV = Integer.toString(service.getAccID(user, account));
				System.out.println("accID : " + IV);
				while (IV.length() < 16) {
					IV = IV + "0";
				}

				newpassword = service.getEncUserPass(account, user);
				decrPass = Decryption.AESdecryption2(currPass, IV, newpassword);

			} catch (IllegalArgumentException e) {
				String message = e.getMessage();
				System.out.println(message);
			} catch (Exception e) {
				String message = e.getMessage();
				System.out.println(message);
			}

		} finally {
			if (factory != null && !factory.isClosed()) {
				factory.close();
			}
		}

		return decrPass;

	}

}
