package com.pms.scripts;


import org.testng.annotations.Test;

import com.pms.generic.BaseLib;
import com.pms.generic.GetPropertyValues;




public class CreateAccountTest extends BaseLib {

	@Test(groups = "Amazon")
	public void TC_001_CreateAccountTest() {
		createAccountPage.createAccount();
	}
	@Test(groups = "Amazon")
	public void TC_002_SignInIntoAmazon() {
		createAccountPage.signInintoAmazon();
		createAccountPage.logout(driver);
	}
	@Test(groups = "Amazon")
	public void TC_003_SearchItemsIntoAmazon() {
		createAccountPage.signInintoAmazon();
		createAccountPage.searchItems(GetPropertyValues.getPropertyValue("itemName"));
		createAccountPage.logout(driver);
	}
	@Test(groups = "Amazon")
	public void TC_004_AddItemsIntoCart() {
		createAccountPage.signInintoAmazon();
		createAccountPage.searchItems(GetPropertyValues.getPropertyValue("itemName"));
		createAccountPage.addItemIntoCart(driver);
		createAccountPage.logout(driver);
		
	}
	

}
