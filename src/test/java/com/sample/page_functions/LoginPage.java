package com.sample.page_functions;

import com.sample.common_actions.CommonMethods;

public class LoginPage extends CommonMethods{
	
	public static void loginForm(String uName, String pwd, String UsernameValue, String pwdValue, String loginButton) {
		enterText("id", uName, UsernameValue, "User Name");
		enterText("id", pwd, pwdValue, "Password");
		click("xpath", loginButton, "Login button");
	}
	
	public static void verifyLogin(String xpathValue, String expMessage){
		
		if(findElementBy("xpath", xpathValue).getText().trim().contains(expMessage)){
			logMessage("Login is successfull");
		}else{
			logErrorMessage("Login is not successfull");
		}
	}
}
