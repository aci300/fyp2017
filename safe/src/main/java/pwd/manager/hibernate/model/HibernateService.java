package pwd.manager.hibernate.model;

import org.hibernate.SessionFactory;

public interface HibernateService {
	/**
	 * The get Hibernate's SessionFactory. 
	 * 
	 * <strong>You might want to set this inside of the constructor. Pass it from main().</strong>
	 * 
	 * @return a session factory for use with a service.
	 */
	public SessionFactory getSessionFactory();
}
