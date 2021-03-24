package com.pms.pageobject;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {
	public static WebDriver driver;
	
	
	
	public static void sleepForMilliSecond(int time)  
	{
		try{
			Thread.sleep(time);
																																																																																																								 }
	catch(InterruptedException ex){
			
		}
	}
	
	
	
}
