package pwd.manager.hibernate.model;


import java.util.Set;

public interface QuerriesService  {

	
	public void addNewAcc(String newusername, String password) throws IllegalArgumentException; 
	
	public void changePassword(String username, String newpassword , String confirm) throws IllegalArgumentException; 
	
	public Boolean checkUser(String username);
	
	public Boolean checkPassword(String username, String password); 
	
	public void deleteUser(String username);
	
	public Integer getAccID(String username);
	
}