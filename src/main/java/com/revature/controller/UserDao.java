package com.revature.controller;

import java.util.List;

import com.revature.model.*;


public interface UserDao {
	public boolean checkEmpty ();
	
	public boolean checkUniqueUser(String u);
	
	public void addUser(String u, String p);
	
	public void deleteUser(String u);
	
	public int valUser(String user, String pass);
	
	public int checkCredentials (String u, String p);
	
	public List <User> getAllUsers (); 
	
	public int totallUser();
	
	public boolean adminUser(String username);
	
	public boolean adminPass (String password);
	
	public boolean userDeposit(int d);
	
	public boolean userWithdrawl(int w);
	
	public int getTransId();
	
	public void updateTran(String u, int i, int a, String t);
	
	public void userHistory(String u);
}
