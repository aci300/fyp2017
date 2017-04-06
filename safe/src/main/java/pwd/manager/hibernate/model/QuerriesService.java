package pwd.manager.hibernate.model;


import java.util.Set;

public interface QuerriesService  {

	
	public void addNewAcc(String newacc, String password, String desc ,  String hint) throws IllegalArgumentException; 
	
	public void changePassword(String username, String newpassword , String confirm) throws IllegalArgumentException; 
	
	public Boolean checkAccount(String username);
	
	public Boolean checkHint(String username, String password); 
	
	public void deleteAccount(String username);
	
	public Integer getAccID(String username);
	
}