package pwd.manager.hibernate.model;

import java.util.Set;


public interface User {

	
	public void setUsername(String user); 
	
	public String getUsername(); 
	
	public void setPassword(String password); 
	
	public String getPassword(); 

		
	public Integer getId(); 
	
	public void setId(Integer id); 
	
	public void setAccounts(Set<Account> accounts);
	
	public Set<Account> getAccounts(); 



}
