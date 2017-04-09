package pwd.manager.hibernate.model.impl;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import pwd.manager.hibernate.model.HibernateService;
import pwd.manager.hibernate.model.Account;
import pwd.manager.hibernate.model.User; 
import pwd.manager.hibernate.model.impl.AccountImpl;
import pwd.manager.hibernate.model.QuerriesService;


public class Querries extends HibernateServiceImpl implements QuerriesService{

	public Querries(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	
	public void addNewUser(String newusername, String password) throws IllegalArgumentException {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			
			
			if(checkUser(newusername)){ 
				throw new IllegalArgumentException("That username already  exists in the database");
			}
			
			if(newusername.equals("") || password.equals("")
					 || newusername.contains(" ") || password.contains(" ") ){
				throw new IllegalArgumentException("Character not accepted!");
			}
			else {
				User newuser = new UserImpl(); 
				newuser.setUsername(newusername);
				
				newuser.setPassword(password);
				session.save(newuser);

				tx.commit();
				
			}
		}
		catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
	}
	
	public void deleteUser(String username){
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			User useraccount = (User) session.createCriteria(UserImpl.class)
					.add(Restrictions.eq("username", username)).uniqueResult();
			if (useraccount == null){
				return;
			}
			else{
				session.delete(useraccount);
			}
			tx.commit();
			
		}  catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
	}
		
	public void addNewAcc(String newacc, String desc ,  String hint, Integer userID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			
			Account acc = (Account) session.createCriteria(AccountImpl.class)
					.add(Restrictions.eq("account", newacc)).add(Restrictions.eq("user_id", userID)).uniqueResult();
      
			if (acc == null )
			{
				User user = (User) session.createCriteria(UserImpl.class)
						.add(Restrictions.eq("id", userID)).uniqueResult();
				
				
				Account newaccount = new AccountImpl(); 
				newaccount.setAccount(newacc);
				
				//newaccount.setPassword(password);
				
				newaccount.setDescription(desc);
				newaccount.setHint(hint);
				newaccount.setUser(user);
				session.save(newaccount);

				tx.commit();
			}
			else 
				throw new IllegalArgumentException("This account already exists. Choose a different name for the acc!");

			
			
		}
		catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
	}
	
	public Integer lastAccID(String account, String username)
	{
		
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Integer result = -1; 
		try {
	
			User user = (User) session.createCriteria(UserImpl.class)
					.add(Restrictions.eq("username", username)).uniqueResult();
			Integer userID = user.getId(); 
			
			Account acc = (Account) session.createCriteria(AccountImpl.class)
					.add(Restrictions.eq("account", account)).add(Restrictions.eq("user_id", userID)).uniqueResult();
        	
		  Serializable ser = session.save(acc);
	        if (ser != null) {
	            result = (Integer) ser;
	            
	        	tx.commit(); }
			}
			catch (HibernateException e) {
				e.printStackTrace();
				if (tx != null) {
					tx.rollback();
				}
			
			}
		return result; 
		
	}
	
	public void addAccPassword(String accountID, String newpassword) throws IllegalArgumentException {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			

				if (newpassword.equals("") || newpassword.contains(" ")){
					throw new IllegalArgumentException("Character (space) not accepted!");
				}

				else {

					Account acc = (Account) session.createCriteria(AccountImpl.class)
							.add(Restrictions.eq("id", accountID)).uniqueResult();

					acc.setPassword(newpassword);
					session.update(acc);

					tx.commit();

				}
			

		}

		catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}

	}

	public void changeUserPassword(String username, String newpassword, String confirm) throws IllegalArgumentException {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			
			if (newpassword.equals(confirm)){
				
				
				if (newpassword.equals("") || newpassword.contains(" ")){
					throw new IllegalArgumentException("Character (space) not accepted!");
				}
				
				User useraccount = (User) session.createCriteria(UserImpl.class)
						.add(Restrictions.eq("username", username)).uniqueResult();
				
					useraccount.setPassword(newpassword);
					session.update(useraccount);
					tx.commit();
			}
			
			else {
				throw new IllegalArgumentException("Password not properly confirmed");
			}	
			
		}  catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		
		
	}
	
	public void changeAccPassword(String accountname, String newpassword, String confirm) throws IllegalArgumentException {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			if (newpassword.equals(confirm)) {

				if (newpassword.equals("") || newpassword.contains(" ")){
					throw new IllegalArgumentException("Character (space) not accepted!");
				}

				else {

					Account acc = (Account) session.createCriteria(AccountImpl.class)
							.add(Restrictions.eq("account", accountname)).uniqueResult();

					acc.setPassword(newpassword);
					session.update(acc);

					tx.commit();

				}
			} 
			else {
				throw new IllegalArgumentException("Password not properly confirmed");
			}

		}

		catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}

	}

	public Boolean checkUser(String username) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
	    Boolean status = false ; 
		try {
			
			User newaccount = (User) session.createCriteria(UserImpl.class)
					.add(Restrictions.eq("username", username)).uniqueResult();
	
			if (newaccount == null){
				return status;
			}

			if(username.equals("") ||  username.contains(" ") ){ 
				 throw new IllegalArgumentException("Character not accepted!");
			}	
			else {
				status =  true; 
			}
		}  catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return status; 
		
		
	}

	public Boolean checkAccount(String acc) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
	    Boolean status = false ; 
		try {
			
			Account newaccount = (AccountImpl) session.createCriteria(AccountImpl.class)
					.add(Restrictions.eq("account", acc)).uniqueResult();
	
			if (newaccount == null){
				return status;
			}
			
			else 
				status = true;

		
		}  catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return status; 
		
		
	}

	public Boolean checkHint(String account, String hint) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
	    Boolean status = null; 
		
		try {
			Account useraccount = (Account) session.createCriteria(AccountImpl.class)
					.add(Restrictions.eq("account", account)).uniqueResult();
	
			if(useraccount.getHint().equals(hint)){
				status= true; 
			}
			else {
				status= false; 
			}			
		}  catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		return status;
	}

	public void deleteAccount(String accountname) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Account useraccount = (Account) session.createCriteria(AccountImpl.class)
					.add(Restrictions.eq("account", accountname)).uniqueResult();
			if (useraccount == null){
				return;
			}
			else{
				session.delete(useraccount);
			}
			tx.commit();
			
		}  catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	public Integer getAccID(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public String showPass(String account) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String pwd = null; 
		try {
			Account useraccount = (Account) session.createCriteria(AccountImpl.class)
					.add(Restrictions.eq("account", account)).uniqueResult();
			if (useraccount == null){
				throw new IllegalArgumentException("This account doesn't exist");
 
			}
			else{
			      pwd = useraccount.getPassword();
			}
			tx.commit();
			
		}  catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		return pwd;
	}




	
		
}  
	
	


	

	
	


