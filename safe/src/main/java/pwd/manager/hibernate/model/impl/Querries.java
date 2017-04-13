package pwd.manager.hibernate.model.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.google.common.base.Objects;
import pwd.manager.hibernate.model.Account;
import pwd.manager.hibernate.model.User;
import pwd.manager.hibernate.model.impl.AccountImpl;
import pwd.manager.hibernate.model.QuerriesService;

public class Querries extends HibernateServiceImpl implements QuerriesService {

	public Querries(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void addNewUser(String newusername, String password) throws IllegalArgumentException {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {

			if (checkUser(newusername)) {
				throw new IllegalArgumentException("That username already  exists in the database");
			}

			if (newusername.equals("") || password.equals("") || newusername.contains(" ") || password.contains(" ")) {
				throw new IllegalArgumentException("Character not accepted!");
			} else {
				User newuser = new UserImpl();
				newuser.setUsername(newusername);

				newuser.setPassword(password);
				session.save(newuser);

				tx.commit();

			}
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	public void deleteUser(String username) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			User useraccount = (User) session.createCriteria(UserImpl.class).add(Restrictions.eq("username", username))
					.uniqueResult();
			if (useraccount == null) {
				return;
			} else {
				session.delete(useraccount);
			}
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Integer addNewAcc(String newacc, String desc, String hint, String username) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Integer id = -1;
		try {

			User user = (User) session.createCriteria(UserImpl.class).add(Restrictions.eq("username", username))
					.uniqueResult();
			// Integer userID = user.getId();
			List<Account> accounts = new ArrayList<Account>();
			accounts = session.createCriteria(AccountImpl.class).add(Restrictions.eq("account", newacc)).list();

			if (!accounts.isEmpty()) {
				for (Account acc : accounts)
					if (acc.getUser().equals(user))
						throw new IllegalArgumentException(
								"This account already exists. Choose a different name for the acc!");

			}

			else {

				Account newaccount = new AccountImpl();
				newaccount.setAccount(newacc);

				// newaccount.setPassword(password);

				newaccount.setDescription(desc);
				newaccount.setHint(hint);
				newaccount.setUser(user);
				session.save(newaccount);
				id = newaccount.getId();

				tx.commit();
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		return id;
	}

	public void addAccPassword(Integer accountID, String newpassword) throws IllegalArgumentException {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {

			// Integer n = Integer.valueOf(accountID);
			if (newpassword.equals("") || newpassword.contains(" ")) {
				throw new IllegalArgumentException("Character (space) not accepted!");
			}

			else {

				Account acc = (Account) session.createCriteria(AccountImpl.class).add(Restrictions.eq("id", accountID))
						.uniqueResult();

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

	public void changeUserPassword(String username, String newpassword, String confirm)
			throws IllegalArgumentException {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {

			if (newpassword.equals(confirm)) {

				if (newpassword.equals("") || newpassword.contains(" ")) {
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

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}

	}

	public void changeAccPassword(String accountname, String newpassword, String confirm)
			throws IllegalArgumentException {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			if (newpassword.equals(confirm)) {

				if (newpassword.equals("") || newpassword.contains(" ")) {
					throw new IllegalArgumentException("Character (space) not accepted!");
				}

				else {

					Account acc = (Account) session.createCriteria(AccountImpl.class)
							.add(Restrictions.eq("account", accountname)).uniqueResult();

					acc.setPassword(newpassword);
					session.update(acc);

					tx.commit();

				}
			} else {
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
		Boolean status = false;
		try {

			User newaccount = (User) session.createCriteria(UserImpl.class).add(Restrictions.eq("username", username))
					.uniqueResult();

			if (newaccount == null) {
				return status;
			}

			if (username.equals("") || username.contains(" ")) {
				throw new IllegalArgumentException("Character not accepted!");
			} else {
				status = true;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}

		return status;

	}

	public Boolean checkUserPass(String username, String pass) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Boolean status = false;
		try {

			User user = (User) session.createCriteria(UserImpl.class).add(Restrictions.eq("username", username))
					.uniqueResult();

			if (user == null) {
				throw new IllegalArgumentException("User doesn't exist!");
			}
			String dbPass = user.getPassword();
			System.out.println("DB pass: " + user.getPassword());
			System.out.println("Given pass: " + pass);
			System.out.println(status);
			if (Objects.equal(dbPass, pass))
				status = true;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		System.out.println(status);
		return status;

	}

	public Boolean checkAccount(String acc) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Boolean status = false;
		try {

			Account newaccount = (AccountImpl) session.createCriteria(AccountImpl.class)
					.add(Restrictions.eq("account", acc)).uniqueResult();

			if (newaccount == null) {
				return status;
			}

			else
				status = true;

		} catch (HibernateException e) {
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

			if (useraccount.getHint().equals(hint)) {
				status = true;
			} else {
				status = false;
			}
		} catch (HibernateException e) {
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
			if (useraccount == null) {
				return;
			} else {
				session.delete(useraccount);
			}
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	public Integer getAccID(String username, String account) {
		// TODO Auto-generated method stub

		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Integer ID = null;
		// Vector<String> subjects = new Vector<String>();
		try {

			User user = (User) session.createCriteria(UserImpl.class).add(Restrictions.eq("username", username))
					.uniqueResult();

			Set<Account> accounts = new HashSet<Account>();
			accounts = user.getAccounts();

			for (Account acc : accounts) {
				if (acc.getAccount().equals(account))
					ID = acc.getId();

			}

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		return ID;
	}

	public String getEncUserPass(String account, String username) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String encPwd = null;
		// Vector<String> subjects = new Vector<String>();
		try {

			User user = (User) session.createCriteria(UserImpl.class).add(Restrictions.eq("username", username))
					.uniqueResult();

			Set<Account> accounts = new HashSet<Account>();
			accounts = user.getAccounts();

			for (Account acc : accounts) {
				if (acc.getAccount().equals(account))
					encPwd = acc.getPassword();

			}

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		return encPwd;

	}

	public String showPass(String account) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String pwd = null;
		try {
			Account useraccount = (Account) session.createCriteria(AccountImpl.class)
					.add(Restrictions.eq("account", account)).uniqueResult();
			if (useraccount == null) {
				throw new IllegalArgumentException("This account doesn't exist");

			} else {
				pwd = useraccount.getPassword();
			}
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		return pwd;
	}

	public Vector<String> getAllAccounts(String username) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Vector<String> subjects = new Vector<String>();
		try {

			User user = (User) session.createCriteria(UserImpl.class).add(Restrictions.eq("username", username))
					.uniqueResult();

			Set<Account> accounts = new HashSet<Account>();
			accounts = user.getAccounts();

			for (Account acc : accounts) {
				subjects.add(acc.getAccount());

			}

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}

		return subjects;
	}

	public Set<Account> AllAccounts(String username) {
		Session session = this.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Set<Account> accounts = new HashSet<Account>();
		try {

			User user = (User) session.createCriteria(UserImpl.class).add(Restrictions.eq("username", username))
					.uniqueResult();

			accounts = user.getAccounts();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}

		return accounts;
	}

}
