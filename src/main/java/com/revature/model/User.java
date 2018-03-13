package com.revature.model;

public class User {
	
		private String username;
		
		private String password;
	
		private boolean val;
	
		private int bits;
		
		
		public User() {
			super();
			}
	
		public User(String username, String password, boolean validation, int bits) {
			super();
			this.username = username;
			this.password = password;
			this.val = validation;
			this.bits = bits;
		}
		
		public String getUsername() {
			return username;
			}
		public String getPassword() {
			return password;
			}
		public boolean getVal() {
			return val;
			}
		public int getBits() {
			return bits;
			}
		
		
		public void setUsername(String username) {
			this.username = username;
			}
		public void setPassword(String password) {
			this.password = password;
			}
		public void setVal(boolean val) {
			this.val = val;
			}
		public void setBits(int bits) {
			this.bits = bits;
			}
		
		
		@Override
		public String toString() {
			return username + "," + password + "," + val + "," + bits;
			}

	
		public boolean makeDeposit(int deposit){
			bits = bits + deposit;
			return true;
		};
		
		public boolean makeWithdrawl(int withdraw){
			if(withdraw > bits) {
				return false;
			}else {
				bits = bits - withdraw;
				return true;
			}
		}
}
