package com.sample.login;

import java.util.HashMap;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.sample.common_actions.CommonMethods;
import com.sample.page_functions.LoginPage;
import com.sample.utils.Utlities;

public class Login_TC extends CommonMethods{
	
	private static HashMap<String, String> readLoginPageProperties;
	private static HashMap<String, String> readConfigData;
	private String testCaseName;
	
	@BeforeSuite
	public void deleteOldSnaps(){
		deleteFolder(".\\snaps");
	}
	
	@BeforeClass
	public void openBrowser(){
		try {
			readLoginPageProperties = Utlities.readProperties(".\\properties\\LoginProperties.properties");
			readConfigData=Utlities.readProperties(".\\properties\\config.properties");
			
			// Invoke the application
			invokeApp();
		} catch (Exception e) {
			logErrorMessage("Error while invoke the application");
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void TC_01_validate_Username_ErrorMessage(){
		try {
			testCaseName = new Object() {}.getClass().getEnclosingMethod().getName();
			logMessage("\nTest case name: "+ testCaseName);

			// Click on login button
			click("xpath", readLoginPageProperties.get("loginButton"), "Login button");
			
			// Verify invalid error message
			verifyText("id", readLoginPageProperties.get("userNameValidation"), readLoginPageProperties.get("userNameValidationMessage"), "Your username is invalid! error message is ");
		} catch (Exception e) {
			logErrorMessage("Error while validating user name error message");
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void TC_02_verifyLogin(){
		try {
			testCaseName = new Object() {}.getClass().getEnclosingMethod().getName();
			logMessage("\nTest case name: "+ testCaseName);
			
			// Login into application
			LoginPage.loginForm(readLoginPageProperties.get("userNameTextBox"), readLoginPageProperties.get("passwordTextBox"),
					readConfigData.get("user_name"), readConfigData.get("password"), readLoginPageProperties.get("loginButton"));
			
			// Verify application is logged successfully or not
			LoginPage.verifyLogin(readLoginPageProperties.get("loginSuccessMessage"), readLoginPageProperties.get("loginSuccessMessageValue"));
		} catch (Exception e) {
			logErrorMessage("Error while verifying login");
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void TC_03_verifyLogout_button(){
		try {
			testCaseName = new Object() {}.getClass().getEnclosingMethod().getName();
			logMessage("\nTest case name: "+ testCaseName);
			
			// Verify logout button is displayed or not
			if(isElementDisplayed("xpath", readLoginPageProperties.get("logOutButton"))){
				logMessage("Logout button is displayed");
			}else{
				logErrorMessage("Logout button is not displayed");
			}
		} catch (Exception e) {
			logErrorMessage("Error while verifying logout button");
			e.printStackTrace();
			assert false;
		}
	}
	
	@AfterClass 
	public void logOut(){
		try {
			testCaseName = new Object() {}.getClass().getEnclosingMethod().getName();
			logMessage("\nTest case name: "+ testCaseName);
			
			// Click on logout button
			click("xpath", readLoginPageProperties.get("logOutButton"), "Logout button");
			
			// Close the application
			quit();
		} catch (Exception e) {
			logErrorMessage("Error while logout in after class");
			e.printStackTrace();
			assert false;
		}
	}
}
