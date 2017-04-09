package pwd.manager.hibernate.model.impl;


import pwd.manager.hibernate.model.Account;
import pwd.manager.hibernate.model.User;




public class AccountImpl implements Account {

	
	private String acc; 
	private String pwd; 
	private Integer id;
	private String descr; 
	private String hint; 
	private User user; 
	
	public void setAccount(String account) {
		this.acc = account; 
	}

	public String getAccount() {
		return this.acc;
	}

	public void setPassword(String password) {
		this.pwd = password; 
	}
	
	public String getPassword() {
		return this.pwd;
	}

	public Integer getId() {
		
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescription(String description) {
		// TODO Auto-generated method stub
		this.descr= description; 
		
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return this.descr; 
	}

	public void setHint(String hint) {
		// TODO Auto-generated method stub
		this.hint = hint; 
		
	}

	public String getHint() {
		// TODO Auto-generated method stub
		return this.hint;
	}

	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user = user; 
	}

	public User getUser() {
		// TODO Auto-generated method stub
		return this.user;
	}

}
