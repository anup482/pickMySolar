package com.pms.generic;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class ScreenShotLib 
{
	public void getScreenShot(WebDriver driver, String filename, String browserName, String folderName)
	{
		if(browserName.equalsIgnoreCase("chrome")){
			EventFiringWebDriver efw= new EventFiringWebDriver(driver);
			File srcFile = efw.getScreenshotAs(OutputType.FILE);
			String filepath = "/var/www/mn-testing/Meritnation/Screenshot/Chrome";
			File file = new File(filepath);
			if(!file.exists()){
				file.mkdir();
			}
			
			//to create subfolder in the form of current timestamp
		/*	SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH:mm:ss");  
			Date date = new Date();  
			String chromeFolder = "/var/www/mn-testing/Meritnation/Screenshot/Chrome/" + formatter.format(date);  
			File file1 = new File(chromeFolder);
			if(!file1.exists()){
				file1.mkdir();
			}*/
			
			
			//to set created subfolder as destination path where we wish to save the screeshot
			File destFile = new File(folderName+"/" + filename+".png");
			
			//to copy screenshot into subfolder
			try
			{
				FileUtils.copyFile(srcFile, destFile);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				
			}
		}
		else if (browserName.equalsIgnoreCase("firefox")){
			EventFiringWebDriver efw= new EventFiringWebDriver(driver);
			File srcFile = efw.getScreenshotAs(OutputType.FILE);
			String filepath = "/var/www/mn-testing/Meritnation/Screenshot/Firefox";
			File file = new File(filepath);
			if(!file.exists()){
				file.mkdir();
			}
			File destFile = new File("/var/www/mn-testing/Meritnation/Screenshot/Firefox/ "+ filename+".png");
			
			
			try
			{
				FileUtils.copyFile(srcFile, destFile);
			} 
			catch (IOException e) 
			{
			
				e.printStackTrace();
				
			}
			
		}
		
		
	}
}	


