package pwd.manager.hibernate.model;

 
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class Database {

	
	//The username of the database
	public String userName = "";
	
	//The password of the database
	public String pwd = "";  
	
	public String database=null; 
	
	public String driver = null; 
	
	private String dialect = null; 
	
	private Configuration configuration = null; 
	
	public Database(String username , String password , String db){
		this.userName = username;
		this.pwd = password; 
		this.database = db; 
	//	driver = driver.toLowerCase();
		if(db.contains("mysql"))
		{
			this.driver= "com.mysql.cj.jdbc.Driver";
			this.dialect=" org.hibernate.dialect.MySQLDialect";				
		}
		else{
			this.driver="org.postgresql.Driver";
			this.dialect= "org.hibernate.dialect.PostgreSQLDialect"; 
		}
		
	}
	
	public void setDBProperties() throws SQLException, ClassNotFoundException {
		
		
	//	java.util.Properties properties = new Properties();
	
	

		

	/*	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
		.applySettings(configuration.getProperties()).build();

		SessionFactory sessionFactory = configuration
		.buildSessionFactory(serviceRegistry);*/
		
		
		//Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("hibernate.connection.user", this.userName);
		connectionProps.put("hibernate.connection.password", this.pwd);
		connectionProps.put("hibernate.connection.url" , this.database);
		connectionProps.put("hibernate.connection.driver.class" , this.driver);
		connectionProps.put("hibernate.dialect" , this.dialect);

	
	
		Configuration conf = new Configuration();
		conf.configure("hibernate.cfg.xml").addProperties(connectionProps);;
	
		
		
		this.configuration = conf; 
	
		System.out.println("DB Configuration file has been updated ");
	
	
	}
	
	public ServiceRegistry  getServiceRegistry(String command){
		
	    ServiceRegistry serviceRegistry= null; 
	    if(driver.contains("mysql"))
		{
	    	try {		
    			// ensure the driver has been loaded.
    			Class.forName("com.mysql.cj.jdbc.Driver");
    		} catch(ClassNotFoundException e) {
    			System.err.println("MySQL driver not found.");
    			System.err.println(e.getMessage());
		
    		}
		}
	    
	    else{ 
	    	try {		
	    			// ensure the driver has been loaded.
	    			Class.forName("org.postgresql.Driver");
	    		} catch(ClassNotFoundException e) {
	    			System.err.println("PostgresSQL driver not found.");
	    			System.err.println(e.getMessage());
			
	    		}
	    }
		
		
		// Creating the database tables
		StandardServiceRegistryBuilder ssr = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		ssr = ssr.applySetting("hibernate.hbm2ddl.auto", command);
		
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
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Database app = new Database("postgres", "banokipo" ,
				"jdbc:postgresql://localhost:5432/PwdManager");

		// Connect to the database
		SessionFactory conn = null;
		app.setDBProperties();
		conn = app.createSessionFactory(app.getServiceRegistry("update")); 
      if (conn != null)  System.out.println("boss"); ; 


	}
}
