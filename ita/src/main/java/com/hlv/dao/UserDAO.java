package com.hlv.dao;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.hlv.entity.Users;

public interface UserDAO extends GenericDAO<Users, Long> {

	public void addUser(Users p);

	public void updateUsers(int id);

	public List<Users> listUsers();

	public void removeUsers(int id);

	public void addUsers(Users p);

	public void saveUsers(Users users);

	public Users getUsers(int id);

	public void editsaveUsers(Users users);

	public void editUsers(Users users);

	public Users updateUsers(Users users);

	public List<Users> getpagination(Users users);

	public void savepagination(Users users);
	public List<Users> searchByName(String username);

}