package pwd.manager.hibernate.model.impl;

import org.hibernate.SessionFactory;

import pwd.manager.hibernate.model.*;

public class HibernateServiceImpl implements HibernateService {

	
	
	
	private SessionFactory sessionFactory;
	
	/**
	 * 
	 * @param sessionFactory
	 */
	public HibernateServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
}
