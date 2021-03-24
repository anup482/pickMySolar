package com.pms.pageobject;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.pms.generic.GetPropertyValues;


public class CreateAccountPage
{
	WebDriver driver;
	@FindBy(xpath="//span[contains(text(), 'Sign in')]")
	private WebElement signInBtn;
	@FindBy(id="createAccountSubmit") 
	public WebElement createAcnBtn;
	@FindBy(name="customerName") 
	public WebElement nameTxtFld;
	@FindBy(name="email") 
	public WebElement mobileTxtFld;
	
	@FindBy(xpath="//input[@type='password']")
	private WebElement pwdTxtFld;
	@FindBy(id="continue") 
	public WebElement continueBtn;
	@FindBy(id="signInSubmit") 
	public WebElement signInBtnInner;
	@FindBy(id="twotabsearchtextbox") 
	public WebElement searchBox;
	@FindBy(id="nav-search-submit-button") 
	public WebElement searchSubmitBtn;
	@FindBy(xpath="//a[contains(@href, 'Renewed-OnePlus-Glacier')]")
	private List <WebElement> searchedItem;
	@FindBy(xpath ="//input[@value='Add to Cart']") 
	public WebElement addToCartBtn;
	@FindBy(xpath="//h1[contains(text(),'Added to Cart')]")
	private WebElement confirmationTxt;
	@FindBy(xpath ="//input[@value='Add to Cart']") 
	public WebElement cartBtn;
	@FindBy(xpath ="//a[contains(@href,'youraccount_btn')]") 
	public WebElement myAcnBtn;
	@FindBy(xpath ="//div[@id='nav-al-your-account']//a[@id='nav-item-signout']") 
	public WebElement signoutLnk;
	
	
	
	//div[@id='nav-cart-count-container']
	
	//Create Account on Amazon needs otp authentication process in which I need Amazon DB acess which is not possible. I have created a Signup script
	//till the OTP/Captcha Screen.
	public void createAccount()
	{
		signInBtn.click();
		BasePage.sleepForMilliSecond(1000);
		createAcnBtn.click();
		BasePage.sleepForMilliSecond(1000);
		nameTxtFld.sendKeys(GetPropertyValues.getPropertyValue("customerName"));
		BasePage.sleepForMilliSecond(1000);
		mobileTxtFld.sendKeys(GetPropertyValues.getPropertyValue("mobileNo"));
		BasePage.sleepForMilliSecond(1000);
		pwdTxtFld.sendKeys(GetPropertyValues.getPropertyValue("pwd"));
		BasePage.sleepForMilliSecond(1000);
		continueBtn.click();
		BasePage.sleepForMilliSecond(1000);
		}
	
	public void signInintoAmazon() {
		signInBtn.click();
		BasePage.sleepForMilliSecond(1000);
		mobileTxtFld.sendKeys(GetPropertyValues.getPropertyValue("mobileNo"));
		BasePage.sleepForMilliSecond(1000);
		continueBtn.click();
		BasePage.sleepForMilliSecond(1000);
		pwdTxtFld.sendKeys(GetPropertyValues.getPropertyValue("pwd"));
		BasePage.sleepForMilliSecond(1000);
		signInBtnInner.click();
		BasePage.sleepForMilliSecond(1000);
	}
	
	
	public void searchItems(String itemName) {
		searchBox.sendKeys(itemName);
		BasePage.sleepForMilliSecond(1000);
		searchSubmitBtn.click();
		BasePage.sleepForMilliSecond(1000);
		
	}
	public void addItemIntoCart(WebDriver driver) {
		searchedItem.get(0).click();
		BasePage.sleepForMilliSecond(1000);
		Set <String> allWindow = driver.getWindowHandles();
		Iterator<String> itr = allWindow.iterator();
		List<String> myl = new ArrayList<String>();
		for (int i =0;i<allWindow.size();i++)
		{
			myl.add(itr.next());
		}
		driver.switchTo().window(myl.get(1));
		
		BasePage.sleepForMilliSecond(2000);
		addToCartBtn.click();
		BasePage.sleepForMilliSecond(2000);
		Assert.assertTrue(confirmationTxt.getText().equalsIgnoreCase("Added to Cart"));
		BasePage.sleepForMilliSecond(2000);
		
	}
	
	public void logout(WebDriver driver) {
		Actions act = new Actions(driver);
		act.moveToElement(myAcnBtn).moveToElement(signoutLnk).click().build().perform();
		
	//	BasePage.moveToElementAndClick(driver, myAcnBtn,signoutLnk);
		
	}
	
	
	
	
	public CreateAccountPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	
	}
	
}
