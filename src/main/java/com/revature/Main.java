package com.revature;

import org.apache.log4j.Logger;

import com.revature.repository.*;


public class Main {
	private static Logger logger = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		logger.trace("MainMenu Action");
		MainMenu.Banking ();
	}
}
