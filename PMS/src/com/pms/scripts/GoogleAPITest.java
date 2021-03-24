package com.pms.scripts;

import org.testng.annotations.Test;

import com.pms.generic.BaseLib;




public class GoogleAPITest extends BaseLib{
	
	@Test(groups = "googleapi")
	public void TC_001_createFolder() {
		googleApiPage.createFolder();
	}
	@Test(groups = "googleapi")
	public void TC_002_createFile() {
		googleApiPage.createFile();
	}
	@Test(groups = "googleapi")
	public void TC_003_getFile() {
		googleApiPage.getFile();
	}
	@Test(groups = "googleapi")
	public void TC_004_deleteFile() {
		googleApiPage.deleteFile();
	}
	@Test(groups = "googleapi")
	public void TC_005_deleteFolder() {
		googleApiPage.deleteFolder();
	}

}
