package pwd.manager.safe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;


/**
 * This is a test class that is no longer in use
 * @author
 *
 */
public class DBtest {
		
		//The username of the database
		private final String userName = "postgres";
		
		//The password of the database
		private final String pwd = "banokipo";    
		
		/**
		 * A blank method that probably shouldn't exist
		 */
		public DBtest(){
			
		}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnectionSetup() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.pwd);

		String database= "jdbc:postgresql://localhost:5432/PwdManager";
		Class.forName("org.postgresql.Driver");
		 
		conn = DriverManager.getConnection(database, connectionProps);
		
		System.out.println("Connected to the database");
		
		// Creating the database tables
				StandardServiceRegistryBuilder ssr = new StandardServiceRegistryBuilder().configure();
				ssr = ssr.applySetting("hibernate.hbm2ddl.auto", "create");
				// System.out.println(ssr.getSettings());
				ServiceRegistry serviceRegistry= null; 	
				serviceRegistry = ssr.build();
		return conn;
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		//Scanner in = new Scanner(System.in);
		DBtest app = new DBtest();

		// Connect to the database
		Connection conn = app.getConnectionSetup();
		
		//app.dropcreateTables(conn);
      if (conn != null)  System.out.println("boss"); ; 
      System.out.println("test");


	}
	
}
