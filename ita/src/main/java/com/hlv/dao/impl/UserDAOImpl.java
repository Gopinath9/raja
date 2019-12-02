package com.hlv.dao.impl;

import java.awt.print.Book;
import java.util.List;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.apache.lucene.search.Query;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.hlv.dao.UserDAO;
import com.hlv.entity.Users;

@Repository
public class UserDAOImpl extends GenericDAOImpl<Users, Long> implements UserDAO {

	// private static final Logger logger =
	// LoggerFactory.getLogger(UserDAOImpl.class);

	/*
	 * @Autowired private SessionFactory sessionFactory;
	 * 
	 * @Autowired public void setSessionFactory(SessionFactory sf){
	 * this.sessionFactory = sf; }
	 */

	private SessionFactory sessionFactory;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void addUser(Users p) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.persist(p);
		// has not solution for 2 case in here ????????????????
		// this.save(p); //should not use save in insert, so if exists id then
		// insert become update
		// this._persist(p); // if use _persist: when create new user and has roles
		// then error, so at that time id null and can not
		// reference in user_role table
		// logger.info("Users saved successfully, Users Details="+p);
	}

	@Override
	public void saveUsers(Users users) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.persist(users);
		// this.save(p);
		// this._update(p); // should not use save in update, so if not exists
		// id
		// then update become to insert
		// logger.info("Users updated successfully, Users Details="+p);
	}

	@Override
	public void addUsers(Users p) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.persist(p);

	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<Users> listUsers() { Session session =
	 * this.getSessionFactory().getCurrentSession(); List<Users> UserssList =
	 * session.createQuery("from Users order by id desc").list(); return UserssList;
	 * // return findAll(); }
	 */

	@Override
	public Users find(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users[] find(Long... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users getReference(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users[] getReferences(Long... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeById(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeByIds(Long... arg0) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> listUsers() {
		Session session = this.getSessionFactory().getCurrentSession();

		// Criteria criteria=createEntityCriteria().addOrder(Order.asc("id"));
		// criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Users> users = session.createQuery("from Users order by id asc").list();
		return users;
	}

	private Criteria createEntityCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUsers(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		Users users = (Users) session.get(Users.class, id);
		session.delete(users);
	}

	@Override
	public void updateUsers(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		// Users users = (Users) session.get(Users.class, id);
		session.update(id);
	}

	@Override
	public Users getUsers(int id) {
		return (Users) getSessionFactory().getCurrentSession().get(Users.class, id);

	}

	@Override
	public Users updateUsers(Users users) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editsaveUsers(Users users) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.update(users);

	}

	@Override
	public void editUsers(Users users) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.saveOrUpdate(users);

	}

	@Override

	public List<Users> getpagination(Users users) {
		Session session = (Session) this.sessionFactory.getCurrentSession().createQuery("from users order by id asc")
				.list();
		return listUsers();

	}

	@Override
	public void savepagination(Users users) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.save(users);
	}

	@Override
	public List<Users> searchByName(String username) {
		Session session = this.getSessionFactory().getCurrentSession();

		Criteria criteria = session.createCriteria(Users.class);

		criteria.add(Restrictions.like("username", username, MatchMode.ANYWHERE));

		List<Users> results = criteria.list();
		return results;

	}

	private void createQuery() {
		// TODO Auto-generated method stub

	}

}
