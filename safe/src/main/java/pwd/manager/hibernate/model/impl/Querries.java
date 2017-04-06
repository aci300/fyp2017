package pwd.manager.hibernate.model.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import pwd.manager.hibernate.model.HibernateService;
import pwd.manager.hibernate.model.Account;
import pwd.manager.hibernate.model.impl.AccountImpl;
import pwd.manager.hibernate.model.QuerriesService;


public class Querries extends HibernateServiceImpl implements QuerriesService{

	public Querries(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void addNewAcc(String newacc, String password, String desc ,  String hint) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			
			AccountImpl newaccount = new AccountImpl(); 
			newaccount.setAccount(newacc);
			
			newaccount.setPassword(password);
			
			newaccount.setDescription(desc);
			
			
			newaccount.setHint(hint);
			session.save(newaccount);

			tx.commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	public void changePassword(String username, String newpassword, String confirm) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
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

		
		}  catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return status; 
		
		
	}

	public Boolean checkPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	public Integer getAccID(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addNewAcc(String newusername, String password) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	public Boolean checkUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
