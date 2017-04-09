package pwd.manager.hibernate.model;


import java.util.Set;

public interface QuerriesService  {

	
	public void addNewAcc(String newacc, String desc ,  String hint, Integer userID) throws IllegalArgumentException; 
	
	public void addNewUser(String newusername, String password) throws IllegalArgumentException ;
	
	public void changeUserPassword(String username, String newpassword, String confirm) throws IllegalArgumentException;
	
	public void addAccPassword(String accountname, String newpassword) ;
	
	public void changeAccPassword(String username, String newpassword , String confirm) throws IllegalArgumentException; 
	
	public Boolean checkAccount(String username);
	
	public Boolean checkUser(String username); 
	
	public Boolean checkHint(String username, String password); 
	
	public void deleteAccount(String username);
	
	public void deleteUser(String username); 
	
	public Integer getAccID(String username);
	
	public Integer lastAccID(String account, String username);
	
	public String showPass(String account); 
	
}