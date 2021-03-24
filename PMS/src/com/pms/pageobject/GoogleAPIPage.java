package com.pms.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pms.generic.GetPropertyValues;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GoogleAPIPage {
	
	WebDriver driver;
	@FindBy(xpath="//input[@type = 'email']")
	private WebElement emailTxtFld;

public static String getUserAuthToken(String BaseURI, String Resource,String scope,
String auth_url,String client_id,String responseType,String redirect_uri,String state,WebDriver driver) 
{ 
	String token="";
	
//To Create AuthURL
String URL = BaseURI+Resource+"?scope="+scope+"&auth_url="+auth_url+"&client_id="+client_id+
    "&response_type="+responseType+"&redirect_uri="+redirect_uri+"&state="+state;

//To Launch Chrome Browser
System.setProperty("webdriver.chrome.driver", "/var/www/mn-testing/PMS/Exe Files/chromedriver");
ChromeOptions options = new ChromeOptions();
options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
          UnexpectedAlertBehaviour.IGNORE);
options.addArguments("disable-geolocation");
options.addArguments("--disable-notifications");
options.addArguments("--no-sandbox");
driver = new ChromeDriver(options);
driver.manage().window().maximize();

//To Navigate to the created URL
driver.get(URL);
BasePage.sleepForMilliSecond(2000);
driver.findElement(By.xpath("//input[@type = 'email']")).sendKeys(GetPropertyValues.getPropertyValue("userEmail"));
BasePage.sleepForMilliSecond(2000);
driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
BasePage.sleepForMilliSecond(5000);
driver.findElement(By.xpath("//input[@type = 'password']")).sendKeys(GetPropertyValues.getPropertyValue("userPwd"));
BasePage.sleepForMilliSecond(2000);
driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
BasePage.sleepForMilliSecond(2000);
driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
BasePage.sleepForMilliSecond(10000);

//To get the Browser code and storing into array
String[] arr =  driver.getCurrentUrl().split("&code=");
String code[] = arr[0].split("&scope=");

//To get Access Token for authentication using Browser code
RestAssured.baseURI=GetPropertyValues.getPropertyValue("baseURI");
RequestSpecification request = RestAssured.given();
Response res = request.urlEncodingEnabled(false)
.queryParam("code",code[0])
.queryParam("client_id",GetPropertyValues.getPropertyValue("client_id"))
.queryParam("client_secret", GetPropertyValues.getPropertyValue("client_secret"))
.queryParam("redirect_uri", GetPropertyValues.getPropertyValue("redirect_uri"))
.queryParam("grant_type","authorization_code")
.queryParam("access_type","offline").
when()
.post("/oauth2/v4/token").
then()
.assertThat().statusCode(401).extract().response();
//System.out.println("The response with Access Token is : " +res.asString());
JsonPath json = res.jsonPath();

//Hitting the endpoint with the access token get in previous step
 request.auth().preemptive().oauth2((String) json.get("access_token")).
 header("Content-Type","application/json").
 queryParam(GetPropertyValues.getPropertyValue("apiCredentials")). //API Credentials
when().
 get("/gmail/v1/users/"+GetPropertyValues.getPropertyValue("userEmail")+"/profile").then().assertThat().statusCode(200).extract().response();
// System.out.println("User profile response : " +res.asString());
 token=  json.get("access_token");
 return token;
} 






//To Create Folder
 public void createFolder() {
 RequestSpecification request1 = RestAssured.given();
 request1.auth().preemptive().oauth2(getUserAuthToken("https://accounts.google.com", "/o/oauth2/v2/auth", "https://www.googleapis.com/auth/androidpublisher","https://www.gmail.com"   , "379279903035-7uvdqd5bqjnjtlkbf3bg836ullqphc7s.apps.googleusercontent.com", "code", "https://mail.google.com"  , "empty", driver)).
 header("Content-Type","application/json").
 queryParam(GetPropertyValues.getPropertyValue("apiCredentials")).
 queryParam("name","PMS_Folder").
 queryParam("id","1").
 queryParam("mime_type","application/vnd.google-apps.folder").
 when().
 post("/drive/v3/files").
 then()
 .assertThat().statusCode(200).extract().response();
 driver.close();
}
 
//TO Create a File
 public void createFile() {
 RequestSpecification request1 = RestAssured.given();
 request1.auth().preemptive().oauth2(getUserAuthToken("https://accounts.google.com", "/o/oauth2/v2/auth", "https://www.googleapis.com/auth/androidpublisher","https://www.gmail.com"   , "379279903035-7uvdqd5bqjnjtlkbf3bg836ullqphc7s.apps.googleusercontent.com", "code", "https://mail.google.com"  , "empty", driver)).
 header("Content-Type","application/json").
 queryParam(GetPropertyValues.getPropertyValue("apiCredentials")).
 queryParam("name","PMS_File").
 queryParam("parents","id,1").
 queryParam("id","1").
 queryParam("fileExtension",".png").
 when().
 post("/drive/v3/files").
 then()
 .assertThat().statusCode(200).extract().response();
 driver.close();
 }
 
//To get the file
 public void getFile() {
	 RequestSpecification request1 = RestAssured.given();
	 request1.auth().preemptive().oauth2(getUserAuthToken("https://accounts.google.com", "/o/oauth2/v2/auth", "https://www.googleapis.com/auth/androidpublisher","https://www.gmail.com"   , "379279903035-7uvdqd5bqjnjtlkbf3bg836ullqphc7s.apps.googleusercontent.com", "code", "https://mail.google.com"  , "empty", driver)).
	 header("Content-Type","application/json").
	 queryParam(GetPropertyValues.getPropertyValue("apiCredentials")).
	 when().
	 get("/drive/v3/files/1").
	 then()
	 .assertThat().statusCode(200).extract().response();
	 driver.close();
 	}
 
//To Delete the file
 public void deleteFile() {
	 RequestSpecification request1 = RestAssured.given();
	 request1.auth().preemptive().oauth2(getUserAuthToken("https://accounts.google.com", "/o/oauth2/v2/auth", "https://www.googleapis.com/auth/androidpublisher","https://www.gmail.com"   , "379279903035-7uvdqd5bqjnjtlkbf3bg836ullqphc7s.apps.googleusercontent.com", "code", "https://mail.google.com"  , "empty", driver)).
	 header("Content-Type","application/json").queryParam(GetPropertyValues.getPropertyValue("apiCredentials")).when().delete("/drive/v3/files/1").then().assertThat().statusCode(200).extract().response();
	 driver.close();
 }
 
//To Delete the Folder
 public void deleteFolder() {
	 RequestSpecification request1 = RestAssured.given();
	 request1.auth().preemptive().oauth2(getUserAuthToken("https://accounts.google.com", "/o/oauth2/v2/auth", "https://www.googleapis.com/auth/androidpublisher","https://www.gmail.com"   , "379279903035-7uvdqd5bqjnjtlkbf3bg836ullqphc7s.apps.googleusercontent.com", "code", "https://mail.google.com"  , "empty", driver)).
	 header("Content-Type","application/json").
	 queryParam(GetPropertyValues.getPropertyValue("apiCredentials")).
	 queryParam("name","PMS_Folder").
	 queryParam("id","1").
	 queryParam("mime_type","application/vnd.google-apps.folder").
	 when().
	 delete("/drive/v3/files/1").
	 then()
	 .assertThat().statusCode(200).extract().response();
	 driver.close();
 

}	



	

	
	
	
	
	


	
	
	
	
	
	
	



	public GoogleAPIPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

}
