package pwd.manager.hibernate.model.impl;

import java.util.Set;

import pwd.manager.hibernate.model.Account;
import pwd.manager.hibernate.model.User;

public class UserImpl implements User{

	private String user; 
	private String pwd; 
	private Integer id; 
	private Set<Account> accounts; 
	
	public void setUsername(String user) {
		this.user = user; 
	}

	public String getUsername() {
		return this.user;
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

	public void setAccounts(Set<Account> accounts) {
		// TODO Auto-generated method stub
		this.accounts = accounts; 
	}

	public Set<Account> getAccounts() {
		// TODO Auto-generated method stub
		return this.accounts;
	}

	
	
}
