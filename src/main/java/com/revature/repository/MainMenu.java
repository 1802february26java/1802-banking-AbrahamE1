package com.revature.repository;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.controller.UserImpl;
import com.revature.service.Bridge;

public class MainMenu {
	
	private static Logger logger = Logger.getLogger(MainMenu.class);
	
	public static void Banking () {
	
		Scanner s = singleScanner.getScanner();
		startMenu(s);
	}
	
	public static class singleScanner {
		private static Scanner scanner;
		
		public static Scanner getScanner(){
			if(scanner == null){
				scanner = new Scanner(System.in);
				}
			return scanner;
		}
		public static void closeScanner() {
			scanner.close();
			}
	}
	
	public static void startMenu(Scanner s) {
		textIntro();
		textOption1(); 
		menuScreen1(s);
		}
	
	public static void exitApplication() {
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("=====================================================");
		System.out.println("Thank  you for using the Banking System!"
							+ "\nGoodbye!");
		System.out.println("=====================================================");
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println();
		
		DataControl.flush();
		singleScanner.closeScanner();
	}

	public static void textIntro(){		
		System.out.println("=======================================================");
		System.out.println("       Hello and welcome to the Banking System        ");
		System.out.println("                                                      ");
		System.out.println("=======================================================");
		System.out.println("                                                      ");
		System.out.println("                   Please select                      ");
		System.out.println("=======================================================");
		LocalDate localDate = LocalDate.now();
        System.out.println("Current date : " + localDate);
	}
	
	public static void textOption1() {
		System.out.println("=======================================================");
		System.out.println("Please type in the following options ");
		System.out.println("=======================================================");
		System.out.println("Please press |'1'| to login to you account.");
		System.out.println("Please press |'2'| to register for an account");
		System.out.println("Please press |'exit'| to close the application.");
		System.out.println("=======================================================");
		System.out.println("You MUST exit sucessfully if you want to save changes. ");
		System.out.println("=======================================================");
	}
	
	
	public static void menuScreen1(Scanner s) {
		
		String option = s.next();
		
		switch(option){
		
		case "1":
			System.out.println("=======================================================");
			System.out.println("You have selected 'LOGIN'");
			System.out.println("=======================================================");
			menuScreen2(s);
			break; 
		case "2":
			System.out.println("========================================================");
			System.out.println("You have selected 'REGISTER'");
			System.out.println("========================================================");
			menuScreen3(s);
			break;
		case "exit":
			exitApplication();
			break;
		case " ":
			System.out.println("=======================================================");
			System.out.println("Invalid option, please input either '1' or '2' or 'exit' for this menu:");
			System.out.println("=======================================================");
			startMenu(s);
			break;
	
		case "debugmenu":
			try (Connection conn = Bridge.connect()){System.out.println("SUCESS");}
			catch (Exception e) {e.printStackTrace();System.out.println("FAILURE?");}
			DataControl.checkAllUsers();
			System.out.println("===============Total number of users=================");
			System.out.println(DataControl.returnTotalUsers());
			System.out.println("===============Total number of users=================");
			
			
			break;
		default:
			System.out.println("=====================================================");
			System.out.println("Invalid option, please input either '1' or '2' or 'exit' for this menu:");
			System.out.println("=====================================================");
			startMenu(s);
			break;
		}
	}
	
	public static void menuScreen2(Scanner s) {
		String inputUsername= menuInputUsername(s);
		String inputPassword = menuInputPassword(s);
		menuLogin(inputUsername, inputPassword,s);
	}
	
	public static void menuScreen3(Scanner s) {
		textIntro();
		String inputUsername= menuInputUsername(s);
		
		if(DataControl.checkDuplicateUser(inputUsername) == false) {	
			String inputPassword = menuInputPassword(s);
			menuRegister(inputUsername, inputPassword);
			System.out.println("=====================================================");
			System.out.println("=====================================================");
			System.out.println("--------------------------------------------------------------");
			System.out.println("Thank you very much for Registering an account with The Banking System!\n"
					+ "Please keep in mind that your account needs to FIRST be VALIDATED by an admin BEFORE you may login"
					+ "successfully.\nYou will now be return to the main menu.");
			System.out.println("----------------------------------------------------------------");
			System.out.println("=====================================================");
			System.out.println("=====================================================");
			startMenu(s);
			}else {	
				System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
				System.out.println("Username is already taken! Sorry! Please select a different one.");
				System.out.println("Returning username input");
				System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
				menuScreen3(s);
				}
	}
	
	public static String menuInputUsername(Scanner s) {
		
		System.out.println("=======================================================");
		System.out.println("Please Type in your Username");
		System.out.println("=======================================================");
		return s.next(); 
	}
	
	public static String menuInputPassword(Scanner s) {
		System.out.println("=======================================================");
		System.out.println("Please Type in your password");
		System.out.println("=======================================================");
		return s.next(); 		
	}

	public static void menuRegister (String username, String password) {
		DataControl.addNewUser(username, password);}

	public static void menuLogin(String u, String p, Scanner s) {	
	int key = DataControl.checkLogin(u, p);
	switch (key) {
	case 0:
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		System.out.println("Username NOT FOUND!!!");
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		System.out.println("You are the first ever user of the BankingSystem!"
						+ "\nPlease go back to the main menu and REGISTER for an account!"
						+ "\nReturning to main menu.");
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		startMenu(s);
		break;
	case 1:
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		System.out.println("Username NOT FOUND!!!");
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		System.out.println("Please go back to the main menu and REGISTER for an account!"
							+ "\nReturning to main menu.");
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		startMenu(s);
		break;
	case 2:
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		System.out.println("WRONG USERNAME OR PASSWORD!");
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		System.out.println("Please verify your Information!" + "\nReturning to main menu.");
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		startMenu(s);
		break;
	case 3:
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		System.out.println("USER NOT YET VALIDATED!!!");
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		System.out.println("Please contact an administrator to have your account validated!" 
							+ "\nReturning to main menu.");
		System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
		startMenu(s);
		break;
	case 4:
		menuScreenUser(s);
		break;
	case 5:
		menuScreenAdmin(s);
		break;
	default:
		System.err.println("Something must have gone really wrong...");
		break;
		}
	}
	
	public static void menuScreenAdmin(Scanner s) {
		System.out.println("=====================================================");
		System.out.println("Hello and welcome to the Banking System."             );
		System.out.println("=====================================================");
		System.out.println();
		System.out.println("=====================ADMIN LOGIN=====================");
		System.out.println();
		System.out.println("=====================================================");
		System.out.println("Please use your keyboard to nagivate through our menu.");
		System.out.println("Your current options are as follows:");
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		System.out.println("Please type in the following options EXACTLY how you see them");
		System.out.println("=====================================================");
		System.out.println("Please type | 'all'| To see list of all users");
		System.out.println("Please type |'val'| validate a user account"    );
		System.out.println("Please type |'del'| delete a user account"    );
		System.out.println("Please type |'exit'| to close the application.");
		System.out.println("=====================================================");
		System.out.println("You MUST exit The Banking System sucessfully if you want to save ANY changes.");
		System.out.println("=====================================================");
		
		String key = s.next();
		
		switch (key) {
		case "all":
			DataControl.checkAllUsers();
			menuScreenAdmin(s);
			break;
			
		case"val":
			System.out.println("=====================================================");
			System.out.println("Please use your keyboard to nagivate through our menu.");
			System.out.println("Your current options are as follows:");
			System.out.println("=====================================================");
			System.out.println("=====================================================");
			System.out.println("Please Type in The Username You wish to VALIDATE");
			System.out.println("=====================================================");
			
			String valUsername = s.next(); 
			
			System.out.println("=====================================================");
			System.out.println("Please Type in Your Admin Password for security");
			System.out.println("=====================================================");
			
			String security = s.next();
			
			int key1 = DataControl.validateUser(valUsername, security);
			
			menuAdminValOption(key1,s);
			break;
			
		////////////////////////////////////////////////////////////////////////////	
		case "del":
			System.out.println("=====================================================");
			System.out.println("Please use your keyboard to nagivate through our menu.");
			System.out.println("Your current options are as follows:");
			System.out.println("=====================================================");
			System.out.println("=====================================================");
			System.out.println("Please Type in The Username You wish to Delete");
			System.out.println("=====================================================");
			
			String u = s.next(); 
			
			System.out.println("=====================================================");
			System.out.println("Please Type in Your Admin Password for security");
			System.out.println("=====================================================");
			
			String secu = s.next();
			
			long key2 = DataControl.deleteUser(u);
			menuScreenAdmin(s);
			break;
		/////////////////////////////////////////////////////////////////////////////////////	
		case "exit":
			exitApplication();
			break;
			
		default:
			System.out.println("=====================================================");
			System.out.println("Invalid option, please input either 'all' or 'val' or 'exit' for this menu:");
			System.out.println("=====================================================");
			menuScreenAdmin(s);
			break;
		}
	}
	

		public static void menuScreenUser(Scanner s) {
			
			System.out.println("=====================================================");
			System.out.println("Hello and welcome to the Banking System.");
			System.out.println("=====================================================");
			System.out.println();
			System.out.println("=====================|" + DataControl.getLoggedInUsername() + "| LOGIN=====================");
			System.out.println("==============|Your balance is: " + DataControl.getLoggedInBits() + "|=====================");
			System.out.println();
			System.out.println("=====================================================");
			System.out.println("Please use your keyboard to nagivate through our menu.");
			System.out.println("Your current options are as follows:");
			System.out.println("=====================================================");
			System.out.println("=====================================================");
			System.out.println("Please type in the following options EXACTLY how you see them");
			System.out.println("=====================================================");
			System.out.println("Please type |'add'| To deposit bits to your account");
			System.out.println("Please type |'sub'| To withdraw bits to your account");
			System.out.println("Please type |'hist'| To see your Transcation History");
			System.out.println("Please type |'exit'| to close the application.");
			System.out.println("=====================================================");
			System.out.println("You MUST exit The Banking System sucessfully if you want to save ANY changes.");
			System.out.println("=====================================================");
			
			String key = s.next();
			
			switch (key) {
			case"add":
				System.out.println("=====================================================");
				System.out.println("You have selected 'ADD'");
				System.out.println("=====================================================");
				System.out.println("Please type in the exact amount you would like to deposit:");
				System.out.println("=====================================================");
				
				int deposit = s.nextInt();
				
				if (DataControl.transcationDeposit(deposit)) {
					int bal = DataControl.getLoggedInBits();
					System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
					System.out.println("Money DEPOSITED SUCESSFULLY!");
					System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
					System.out.println("Your balance as now increased to: |" + bal 
										+ "|\nThe Banking System will now close for security purposes."
										+ "\nNow Exiting.");
					System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
					exitApplication();
				}else {System.err.println("Something must have gone really wrong...");
				}
				break;
				
			case "sub":
				System.out.println("=====================================================");
				System.out.println("You have selected 'SUB'");
				System.out.println("=====================================================");
				System.out.println("Please type in the exact amount you would like to withdraw:");
				System.out.println("=====================================================");
				
				int withdraw = s.nextInt();
				
				if (DataControl.transcationWitdraw(withdraw)) {
					int bal = DataControl.getLoggedInBits();
					System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
					System.out.println("Money WITHDRAWN SUCESSFULLY!");
					System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
					System.out.println("Your balance as now decreased to: |" + bal 
										+ "|\nThe Banking System will now close for security purposes."
										+ "\nNow Exiting.");
					System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
					exitApplication();
				}else {	
				System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
				System.out.println("=!=!=!=!=!=!=!=!=!=!INSUFFICENT FUNDS!!=!=!=!=!=!=!=!=!=!");
				System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
				System.out.println("NOT enough BITs in your account to withdraw!"
									+ "\nReturning to User menu.");
				System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
				menuScreenUser(s);
				}
				break;
				
			case "hist":
				System.out.println("=====================================================");
				System.out.println("You have selected 'HIST'");
				System.out.println("=====================================================");
				DataControl.transactionHistory( DataControl.getLoggedInUsername());
				menuScreenUser(s);
				break;
				
			case "exit":
				exitApplication();
				break;

			default:
				System.out.println("=====================================================");
				System.out.println("Invalid option, please input either 'add', 'sub' or 'hist' or 'exit' for this menu:");
				System.out.println("=====================================================");
				menuScreenUser(s);
				break;
			}
		}

	public static void menuAdminValOption(int key, Scanner s) {
		
		switch (key) {
		case 0:
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("Username NOT FOUND!!!");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("There are currently no users in list.\nReturning to Admin Menu Screen.");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			menuScreenAdmin(s);
			break;
		case 1: 
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("WRONG ADMIN PASSWORD!");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("Please verify your Information!" + "\nNow Exiting.");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			exitApplication();
			break;
		case 2: 
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("Username NOT FOUND!!!");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("Please have User go back to the main menu and REGISTER for an account!"
								+ "\nReturning to Admin Menu Screen.");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			menuScreenAdmin(s);
			break;
		
		case 3: 
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("USER VALIDATED SUCESSFULLY!");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("Please have user attempt to login!"
								+ "The  Banking System will now close for security purposes."
								+ "\nNow Exiting.");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			exitApplication();
			break;
			
		case 4: 
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("Username ALREADY VALIDATED!!!");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			System.out.println("Please have User go back to the main menu and Login!"
								+ "\nReturning to Admin Menu Screen.");
			System.out.println("=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=!=");
			menuScreenAdmin(s);
			break;
		default:
			System.out.println("=====================================================");
			System.out.println("Invalid option, please input either 'all', 'validate' or 'exit' for this menu:");
			System.out.println("=====================================================");
			menuScreenAdmin(s);
			break;
		}
	}
}
