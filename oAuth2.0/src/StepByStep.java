import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class StepByStep {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//NOTE THAT WE ARE FOLLOWING BOTTOM TO TOP APPROACH HERE
		
		//1. actualRequest
		
		//This is same as actualRequest from the document. Refer the request from the doc
		//Since we dont have access token, we are leaving it blank
		//Instead of getting it using then, we are getting it directly using asString
		
		/* 
		
		actualRequest -  
		EndPoint : https://rahulshettyacademy.com/getCourse.php
		Query Parameters : Access Token
		
		*/
		String response = given().log().all().queryParam("access_token", "")
		.when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		//2. exchange code
		
		//This is same as exchange code from the document. Refer the request from the doc
		//Now, we have to perform steps to get the access token
		//Since we dont have authorization code, we are leaving it as blank
		//Since multiple queryParams are there, we are giving it as queryParams and not queryParam
		
		/*  Mandatory fields for GetAccessToken Request :

			End point : Access token url
			Query Params : Code, client_id, client_secret, redirect_uri, grant_type
			Output : Hit that URL and get the Access Token   */
	
		
		String accessTokenResponse = given().log().all().queryParams("code", "")
											.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
											.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
											.queryParams("grant_type", "authorization_code")
											.queryParams("state", "verifyfjdss")
											.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
											.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                                   .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		
		//3. getCode 
		//This is same as getCode from the document. Refer the request from the doc
		//This is done through UI automation
		
		/* 	Mandatory fields for GetAuthorization Code Request ;
			End Point : Authorization server url
			Query Params: Scope, Auth_url, client_id, response_type, redirect_uri
			
			Output : Code in the form of URL which  Google authenticates and gives after entering the credentials   */

		System.setProperty("webdriver.chrome.driver", "D:\\Rest Assured\\jars lib\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		String url = driver.getCurrentUrl();
		//From this url, we need to get the auth code which has to be passed in Code and then the accessToken has to be passed to get the response
		/* 
		  https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_
		  XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0
		  &session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#    */
		
		//The uRL will be something like this
		
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&")[0];
		
		//The code is the authorization code which we are looking for
		

	}

}
