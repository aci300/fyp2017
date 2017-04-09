package pwd.manager.hibernate.model;

public interface Account {

	
	public void setAccount(String account); 
	
	public String getAccount(); 
	
	public void setPassword(String password); 
	
	public String getPassword(); 

	public Integer getId(); 
	
	public void setId(Integer id); 

	public void setDescription(String password); 

	public String getDescription(); 
	
	public void setHint(String password);
	
	public String getHint(); 
	
	public void setUser(User user); 
	
	public User getUser(); 

}
