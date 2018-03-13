package com.revature.repository;


import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.revature.model.User;
import com.revature.controller.UserImpl;

	public class DataControl {
		static UserImpl data = new UserImpl();
		private static Logger logger = Logger.getLogger(DataControl.class);
		
		protected static boolean checkDuplicateUser(String s) {
			logger.trace("Check Duplicates");
			if(returnTotalUsers() == 0) {
				return false;
				} 
			if(checkAdmin(s)) {
				return true;
				}
			if (confirmDuplUser(s)) {
				return true;
				}
			return false;
			}

		protected static void checkAllUsers() {
			data.userList.clear();
			data.getAllUsers();
			System.out.println("===============LIST OF ALL USERS ================");
			ListIterator<User> li = data.userList.listIterator(0);
			while(li.hasNext()) {
				System.out.println(li.next().toString());
			}
			System.out.println("===============LIST OF ALL USERS ================");
		}
		
		static int returnTotalUsers () {
			return data.totallUser();
			}
		
		static boolean checkAdmin(String username) {
			return data.adminUser(username);
			}
		
		static boolean confirmDuplUser (String username) { 
			return data.checkUniqueUser(username);
			}
		
		protected static void addNewUser (String u,String p) {
			data.addUser(u,p);
			};
		
		static int checkLogin(String u,String p) {
			return data.checkCredentials(u, p);
			}
		
		static int validateUser(String user, String pass) {
			return data.valUser(user,pass);
			}
		
		static String getLoggedInUsername() {
			return data.currentUserName();
			}
		
		static int getLoggedInBits() {
			return data.currentUserMoney();
			}
		
		static void flush() {
			data.flushLastIndex ();
			}
		
		static boolean transcationDeposit(int d) { 
			return data.userDeposit(d);
			}
		
		static boolean transcationWitdraw(int w) {
			return data.userWithdrawl(w);
			}
		
		static void transactionHistory(String u) {
			data.userHistory(u);
			}
		//////////////////////////////////
		static long deleteUser(String u){
			data.deleteUser(u);
			return 0;
		}
		///////////////////////////////////
		
		
		
		
		
}
