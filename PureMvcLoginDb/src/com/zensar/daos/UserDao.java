package com.zensar.daos;

import java.sql.SQLException;
import java.util.List;

import com.zensar.entities.User;

/**
 * @author gayatri Sinnarkar
 * @version 1.0
 * @creation_date 21st September 2019 5:29PM
 * @modification_date 21st september 2019 5:29PM
 * @copyright Zensar Technologies. All rights reserved.
 * @description It is Data Access Object Interface.
 *				It is used in persistance layer of application.
 */
public interface UserDao {
	
	void insert(User user)throws SQLException;
	void update(User user)throws SQLException;
	void delete(User user)throws SQLException;
	User getbyUsername(String username)throws SQLException;
	List<User> getAll()throws SQLException;
}
