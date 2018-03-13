package com.revature.controller;

import com.revature.model.*;

import static com.revature.exception.Streams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.revature.service.Bridge;

public  class UserImpl implements UserDao {

	private static Logger logger = Logger.getLogger(UserImpl.class);
	
	public UserImpl(){
		logger.trace("Get All Users");
		getAllUsers();
		}
	
	public static List<User> userList = new ArrayList<>();

	private static int lastIndex = 0;
	
	public static void flushLastIndex () {
		lastIndex = 0;
		}
	
	protected static void setLastIndex (int a) {
		lastIndex = a;
		}
	
	protected static int getLastIndex () {
		return lastIndex;
		}
	
	public static int currentUserMoney () {
		return userList.get(getLastIndex()).getBits(); 
		} 
	public static String currentUserName() {
		return userList.get(getLastIndex()).getUsername();
		}
	
	@Override
	public boolean checkEmpty() {
		if(totallUser() == 0) {
			return true;
			}
		else {
			return false;
			}
	}
	
	@Override
	public boolean checkUniqueUser(String u) {

		Statement stmt = null;
		ResultSet rs = null;
		
		try(Connection conn = Bridge.connect()){
			
			String sql = "SELECT USERNAME FROM USER_LIST";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			while(rs.next()){
				if(u.equals( rs.getString(1))) {
					return true;
					}
				}
			}
		catch(SQLException e){
			e.printStackTrace();
			}
		finally{
			close(stmt);close(rs);
			}
		return false;
	}

	@Override
	public void addUser(String u, String p) {
		
		PreparedStatement ps = null;
		
		try(Connection conn = Bridge.connect()){
			String sql = "INSERT INTO USER_LIST VALUES(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,u);
			ps.setString(2, p);
			ps.setString(3,"NOP");
			ps.setInt(4, 0);
			ps.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
			}
		finally{
			userList.add(new User(u,p,false,0));
		close(ps);
		}
		
		newTran(u, getTransId(),0,"I");
	}
//////////////////////////////////////////////////
	@Override
	public void deleteUser(String u) {
		
		PreparedStatement ps = null;
		
		try(Connection conn = Bridge.connect()){
			String sql = "DELETE Username FROM USER_list Where Username =?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, u);
			ps.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
			}
		finally{
			userList.remove((u));
		close(ps);
		}
		}
		
		////////////////////////////////////////

	@Override
	public int valUser(String user, String pass) {
		if(totallUser() == 0){
			return 0;
			} 
		if(!adminPass(pass)) {
			return 1;
			} 
		if(!checkUniqueUser(user)) {
			return 2;
			} 

		ListIterator<User> li = userList.listIterator(0);
		
			while(li.hasNext()) {
				if(li.next().getUsername().equals(user)) {
					if(!li.previous().getVal()) {
					
					li.next().setVal(true);
					
					PreparedStatement ps = null;
					
					try(Connection conn = Bridge.connect()){			
						String sql = "UPDATE USER_LIST SET VAL = ?" 
								+ "WHERE USERNAME = ?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, "YEP");
						ps.setString(2, user);
						ps.execute();
						return 3; 
					}catch(SQLException e){
						e.printStackTrace();
						}
					finally{close(ps);
					}
				}else {
					return 4;
					}
				}
			}
			
		return -1;
	}

	@Override
	public int checkCredentials(String u, String p) {
		if(totallUser() == 0) {
			return 0;
			} 
		if(adminUser(u) && adminPass(p)) {
			return 5;
			} 
		if(!checkUniqueUser(u)) {
			return 1;
			} 
		
		ListIterator<User> li = userList.listIterator(0);
		
		
		while(li.hasNext()) {
			if(li.next().getUsername().equals(u)) {
				if(!li.previous().getPassword().equals(p)) {
					flushLastIndex(); 
					return 2;
					} 
					if(li.next().getVal()) {
						setLastIndex(li.previousIndex());  
						return 4;
						} 
					flushLastIndex();
					return 3; 
			}
		}
		flushLastIndex();
		return -1;
	}

	@Override
	public List<User> getAllUsers() {
		Statement stmt = null;
		ResultSet rs = null;
		String y = "YEP";
		boolean v = true;
		
		try(Connection conn = Bridge.connect()){
			
			String sql = "SELECT * FROM USER_LIST";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			while(rs.next()){
				
				if(	y.equals(rs.getString(3))){ v = true;}else {v = false;}
				userList.add(new User(
							rs.getString(1),
							rs.getString(2),
							v
							,
							rs.getInt(4)));
				}
			}
		catch(SQLException e){
			e.printStackTrace();
			}
		finally{
			close(stmt);close(rs);
			}
		
		return userList;
	}

	@Override
	public int totallUser() {
		logger.trace("TotalUsers");
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try(Connection conn = Bridge.connect()){
			
			String sql = "SELECT COUNT(USERNAME) FROM USER_LIST";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				count = rs.getInt(1);}
			}
		catch(SQLException e){
			e.printStackTrace();}
		finally{
			close(stmt);close(rs);}
		return count;
	}

	@Override
	public boolean adminUser(String username) {
		logger.trace("Admin");
		Statement stmt = null;
		ResultSet rs = null;
		String check = new String();
		
		try(Connection conn = Bridge.connect()){
			String sql = "SELECT ADMIN_USERNAME FROM ADMIN_TABLE";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				check = rs.getString(1);
				}
			}
		catch(SQLException e){
			e.printStackTrace();
			}
		finally{
			close(stmt);close(rs);
			}
		return check.equals(username);
	}

	@Override
	public boolean adminPass(String password) {
		logger.trace("Admin Pass");
		Statement stmt = null;
		ResultSet rs = null;
		String check = new String();
		
		try(Connection conn = Bridge.connect()){
			String sql = "SELECT ADMIN_PASSWORD FROM ADMIN_TABLE";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				check = rs.getString(1);}
			}
		catch(SQLException e){
			e.printStackTrace();
			}
		finally{
			close(stmt);close(rs);
			}
		return check.equals(password);
	}
	@Override
	public boolean userDeposit(int d) {
		
		boolean v = userList.get(getLastIndex()).makeDeposit(d);
		if(v) {
		PreparedStatement ps = null;
		
		int tranId = 0;
		int newBal = userList.get(getLastIndex()).getBits();
		
		try(Connection conn = Bridge.connect()){			
			String sql = "UPDATE USER_LIST SET BITS = ?" 
					+ "WHERE USERNAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newBal);
			ps.setString(2, userList.get(getLastIndex()).getUsername());
			ps.executeUpdate();
			ps = null;
			
		}catch(SQLException e){
			e.printStackTrace();
			}
		finally{close(ps);
		}

		int tran = getTransId();
		updateTran(userList.get(getLastIndex()).getUsername(),tran,d,"D");
		
		return true;
		}
		else {
		return false;
		}
	}
	
	@Override
	public boolean userWithdrawl(int w) {
		boolean v = userList.get(getLastIndex()).makeWithdrawl(w);
		if(v) {
		PreparedStatement ps = null;
		int newBal = userList.get(getLastIndex()).getBits();
		try(Connection conn = Bridge.connect()){			
			String sql = "UPDATE USER_LIST SET BITS = ?" 
					+ "WHERE USERNAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newBal);
			ps.setString(2, userList.get(getLastIndex()).getUsername());
			ps.execute();
			
			
		}catch(SQLException e){
			e.printStackTrace();
			}
		finally{close(ps);
		}
		
		int tran = getTransId();
		updateTran(userList.get(getLastIndex()).getUsername(),tran,w,"W");
		
		return true;
		}
		else {return false;
		}
	}
	@Override
	public int getTransId() {
		ResultSet rs = null;
		Statement stmt = null;
		int tranId = 0;
		
		try(Connection conn = Bridge.connect()){			
			
			String sql = "SELECT COUNT(TRANS_ID) FROM TRANSACTION_RECORDS";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				tranId = rs.getInt(1);
				}
			
		}catch(SQLException e){
			e.printStackTrace();
			}
		finally{
			close(rs);
			}
		return tranId;
	}
	@Override
	public void updateTran(String u, int i, int a, String t) {
		PreparedStatement ps = null;
		try(Connection conn = Bridge.connect()){			
			String sql = "INSERT INTO TRANSACTION_RECORDS VALUES(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ps.setString(2, userList.get(getLastIndex()).getUsername());
			ps.setInt(3, a);
			ps.setString(4, t);
			ps.execute();
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{close(ps);
		}
		
	}
	
	public void newTran(String u, int i, int a, String t) {
		PreparedStatement ps = null;
		try(Connection conn = Bridge.connect()){			
			String sql = "INSERT INTO TRANSACTION_RECORDS VALUES(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ps.setString(2, u);
			ps.setInt(3, a);
			ps.setString(4, t);
			ps.execute();
			
			
		}catch(SQLException e){
			e.printStackTrace();
			}
		finally{close(ps);
		}
		
	}
	
	@Override
	public void userHistory (String u) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		
		try(Connection conn = Bridge.connect()){
			
			String sql = "SELECT * FROM TRANSACTION_RECORDS "
					+ " WHERE USERNAME = ? ";
		
			ps = conn.prepareStatement(sql);
			ps.setString(1, u);
			rs = ps.executeQuery(); 
			
			System.out.println("===============LIST OF ALL TRANSACTIONS FOR " + u +  " ================");
			while(rs.next()){
			System.out.println(	
				
				"TRANSCATION ID: " + rs.getInt(1) + " | " +
				"TRANSACTION USER: " + rs.getString(2) + " | " +
				"TRANSACTION OF: " + rs.getInt(3) + " Money | " +
				"TRANSACTION TYPE OF: " + rs.getString(4) + " | "
					);
				}
			System.out.println("===============LIST OF ALL TRANSACTIONS FOR " + u +  " ================");
			}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			close(ps);close(rs);
			}
		
	}
}