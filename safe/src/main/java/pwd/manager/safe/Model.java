package pwd.manager.safe;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;


/**
 * Handles SQL queries
 * @author
 *
 */
public class Model {

	private String username;
    private String password; 
	private String driver;
	private String dialect;

    private static String salt = "salt"; 

    /**
     * Constructor
     */
    public Model(){
    	username = "";
    	password = "";
    }
    
    /** 
     * @param username sets the username for the database
     */
    public void setUsername(String username){
    	this.username = username;
    }
    
    /**
     * @param password sets the password for the database
     */
    public void setPassword(String password){
    	this.password = password;
    }
    
    
    /**
     * @return username for database
     */
    public String getUsername(){
    	return username;
    }
    
    /**
     * @return password for database
     */
    public String getPassword(){
    	return password;
    }
    

 
	public ServiceRegistry  getServiceRegistry(String userName , String password, String url,  String command){
		
		ServiceRegistry serviceRegistry = null;

		if (url.contains("mysql")) {
			driver = "com.mysql.cj.jdbc.Driver";
			dialect = " org.hibernate.dialect.MySQLDialect";
		} else {
			driver = "org.postgresql.Driver";
			dialect = "org.hibernate.dialect.PostgreSQLDialect";
		}

		if (driver.contains("mysql")) {
			try {
				// ensure the driver has been loaded.
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("MySQL driver not found.");
				System.err.println(e.getMessage());

			}
		}

		else {
			try {
				// ensure the driver has been loaded.
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("PostgresSQL driver not found.");
				System.err.println(e.getMessage());

			}
		}
		
		// Creating the database tables
		StandardServiceRegistryBuilder ssr = new StandardServiceRegistryBuilder().configure();
		ssr.applySetting("hibernate.connection.user", userName);
		ssr.applySetting("hibernate.connection.password", password);
		ssr.applySetting("hibernate.connection.url", url);
		ssr.applySetting("hibernate.connection.driver.class", driver);
		ssr.applySetting("hibernate.connection.dialect", dialect);
		ssr = ssr.applySetting("hibernate.hbm2ddl.auto", command);
		// System.out.println(ssr.getSettings());
		
		serviceRegistry = ssr.build();
		
	
	    return serviceRegistry; 
		
	}

	public SessionFactory createSessionFactory(ServiceRegistry serviceRegistry)
	{
		SessionFactory factory = null;
		
		try {
			// create a session factory
			factory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		} catch(HibernateException e) {
			StandardServiceRegistryBuilder.destroy(serviceRegistry);
			System.err.println("couldn't connect to the database.");
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}
		
		
		return factory; 
		
	}

	/*converting string  to sha1 - encryption for the password*/
	public  byte[] hashSHA(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

	    MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	    crypt.reset();
	    crypt.update(password.getBytes("UTF-8"));
       byte[] key =  crypt.digest(); 
       key = Arrays.copyOf(key, 16); // use only first 128 bit
	    return key;
	}
	
	public byte[] SHA256(String password) throws NoSuchAlgorithmException {
        // Salt all you want here.
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] digest = sha256.digest(password.getBytes());
        return digest;
	}
	
	
	 public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
		
		    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),salt.getBytes(),80000,256);
		    SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		    byte[] hashedPassword = key.generateSecret(spec).getEncoded();
		    return String.format("%x", new BigInteger(hashedPassword));
		  }
	 
	 
	public static String bytesToHex(byte[] bytes) {
		
		char[] hexArray = "0123456789ABCDEF".toCharArray();

	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	


	
}
