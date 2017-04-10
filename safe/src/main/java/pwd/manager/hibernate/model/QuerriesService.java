package pwd.manager.hibernate.model;


import java.util.Set;
import java.util.Vector;

public interface QuerriesService  {

	
	public Integer addNewAcc(String newacc, String desc ,  String hint, String user) throws IllegalArgumentException; 
	
	public void addNewUser(String newusername, String password) throws IllegalArgumentException ;
	
	public void changeUserPassword(String username, String newpassword, String confirm) throws IllegalArgumentException;
	
	public void addAccPassword(Integer accountID, String newpassword) ;
	
	public void changeAccPassword(String username, String newpassword , String confirm) throws IllegalArgumentException; 
	
	public Boolean checkAccount(String username);
	
	public Boolean checkUser(String username); 
	
	public Boolean checkHint(String username, String password); 
	
	public void deleteAccount(String username);
	
	public void deleteUser(String username); 
	
	public Integer getAccID(String username);
	public Vector<String> getAllAccounts(String username); 	
	public String showPass(String account); 
	public Boolean checkUserPass(String username, String pass); 
	
}