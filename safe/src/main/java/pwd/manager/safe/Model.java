package pwd.manager.safe;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

//import org.apache.commons.codec.binary.Hex;


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
    private String title;
    private String description; 
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
    
    public void setTitle(String title){
    	this.title = title;
    }
    

    public void setDescription(String description){
    	this.description = description;
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
    
    /**
     * @return username for database
     */
    public String getTitle(){
    	return title;
    }
    
    /**
     * @return password for database
     */
    public String getDescription(){
    	return description;
    }
    
 
	public ServiceRegistry  getServiceRegistry(String command){
		
	    ServiceRegistry serviceRegistry= null; 
	
		try {
			// ensure the driver has been loaded.
			Class.forName("org.postgresql.Driver");
		} catch(ClassNotFoundException e) {
			System.err.println("driver not found.");
			System.err.println(e.getMessage());
			
		}
		
		
		// Creating the database tables
		StandardServiceRegistryBuilder ssr = new StandardServiceRegistryBuilder().configure();
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
	
	@SuppressWarnings("restriction")
	public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}

	@SuppressWarnings("restriction")
	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}
	
}
