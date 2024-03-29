package com.zensar.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * @author Gayatri Sinnarkar
 * @version 1.0
 * @creation_date 21st September 2019 5:37PM
 * @modification_date 23st september 2019 10.20AM
 * @copyright Zensar Technologies. All rights reserved.
 * @description It is Data Access Object Interface implimentor class.
 * 				It is used in Persistence Layer.
 */
import java.util.List;

import com.zensar.entities.User;


public class UserDaoImpl implements UserDao {
	
	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(User user) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO USER_LOGIN VALUES(?,?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		int insertCount = pstmt.executeUpdate();
		if(insertCount>0)
		{
			System.out.println("New user record is inserted");
		}
		else
		{
			System.out.println("Sorry! new user record cannot be inserted");
		}

	}

	@Override
	public void update(User user) throws SQLException{
		// TODO Auto-generated method stub
		
		String sql = "Update user_login set password = ? where username =?";
		PreparedStatement  pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getUsername());
		int updateCount = pstmt.executeUpdate();
		if(updateCount>0)
		{
			System.out.println("user record updated successfully");
			
		}
		else
		{
			System.out.println("Sorry! user record cannot be updated");
		}
	}

	@Override
	public void delete(User user) throws SQLException{
		// TODO Auto-generated method stub
		String sql = "delete from user_login where username=?";
		PreparedStatement  pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, user.getPassword());
		int deleteCount = pstmt.executeUpdate();
		if(deleteCount>0)
		{
			System.out.println("user record deleted successfully");
			
		}
		else
		{
			System.out.println("Sorry! user record cannot be deleted");
		}
	}
		

	

	@Override
	public User getbyUsername(String username)throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("method is called ");
		String sql = "select username,password from user_login where username=?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, username);
		System.out.println("method is called " + pstmt);
		ResultSet rs = pstmt.executeQuery();
		System.out.println("method is called " + rs);
		if(rs.next())
		{
			User user = new User();
			user.setUsername(rs.getString(1));
			user.setPassword(rs.getString(2));
			return user;
		}
		else
		return null;
		
	}

	@Override
	public List<User> getAll()throws SQLException {
		// TODO Auto-generated method stub
		
		String sql = "Select username, password from user_login";
		Statement stmt = connection.createStatement();

		ResultSet rs = stmt.executeQuery(sql);
		List<User> users = new ArrayList<User>();
		while(rs.next())
		{
			User user = new User();
			user.setUsername(rs.getString(1));
			user.setPassword(rs.getString(2));
			users.add(user);
		}
		return users;
	}	

}
