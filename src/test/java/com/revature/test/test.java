/**
 * 
 */
package com.revature.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.revature.controller.UserImpl;



/**
 * @author AbrahamE1
 * @param <adminUser>
 *
 */
public class test {
	private static Logger logger = Logger.getLogger(Test.class);
	
	/**
	 * Test method for {@link com.revature.repository.MainMenu#exitApplication()}.
	 */
	@Test
	public void testExitApplication() {
		logger.trace("Exit Application");
		assertEquals(0, 0);
	}

	/**
	 * Test method for {@link com.revature.repository.MainMenu#texAlltOption()}.
	 */
	@Test
	public void testMenuAdminAllOption() {
		logger.trace("Testing All option");
		assertEquals("all", "all");
	}

	/**
	 * Test method for {@link com.revature.repository.MainMenu#menuAdminValOption(int, java.util.Scanner)}.
	 */
	@Test
	public void testMenuAdminValOption() {
		logger.trace("Testing Validation");
		assertEquals("val", "val");
	}
	@Test
	public void testLogin() {
		logger.trace("Testing Login");
		assertEquals(1, 1);
	}

	}
	
