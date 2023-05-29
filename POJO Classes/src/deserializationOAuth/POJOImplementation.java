package deserializationOAuth;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojoPackagedeserialization.ApiClass;
import pojoPackagedeserialization.GetCourse;
import pojoPackagedeserialization.WebAutomationClass;

public class POJOImplementation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//WE ARE DESERIALIZING THE JSON RETRIEVED HERE
		
		//Generate url usinng the below link
		//https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F1QFosUnWUPQGDTOoHVW9Cb9FyBbDBNeRY-ZyFluNSGGQi1VY8NfHlV3NWlNYh4HBwoOiUAaA2iDVfbyFhFw0Klw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&")[0];
		
		System.out.println("The access code is "+code);
		
		
		String accessTokenResponse = given().log().all().urlEncodingEnabled(false).queryParams("code", code) //From previous
											.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
											.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
											.queryParams("grant_type", "authorization_code")
											.queryParams("state", "verifyfjdss")
											.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
											.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                                   .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		System.out.println("The access token is "+accessToken);
		
		//Syntax for Deserialization
		GetCourse gc = given().log().all().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON) //Specifying the type as json
								.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		
		//Retrieving the values for direct keys from object	
		System.out.println("The linkedin url is "+gc.getLinkedIn());
		System.out.println("The expertise field is "+gc.getExpertise());
		
		//This is similar to String strVal = driver.findElements.get(0).getAttribute("value")
		System.out.println("The price amount of 0 index of API testing is "+gc.getCourses().getApi().get(0).getPrice());
		System.out.println("The course title of 0 index of API testing is "+gc.getCourses().getApi().get(0).getCourseTitle());
		
		//Since we dont want to hard code, we are using if condition
		//This is similar to List<Web Element> strVal = driver.findElements
		List<ApiClass> apiVal = gc.getCourses().getApi();
		
		for(int i=0;i<apiVal.size();i++) {
			
			String courseName = apiVal.get(i).getCourseTitle();
			
			if(courseName.equalsIgnoreCase("SoapUI WebServices Testing")) {
				
				System.out.println("The price of Soap UI Testing course is "+apiVal.get(i).getPrice());
				
			}
			
			
		}
		
		//To get all the course names in webAutomation
		List<WebAutomationClass> webVal = gc.getCourses().getWebAutomation();
		
		for(int i=0;i<webVal.size();i++) {
			
			String strVal =webVal.get(i).getCourseTitle();
			System.out.println(strVal);
			
		}
		
		//We are cchecking whether the courses are there in Web Automation
		String[] courseTitle = { "Selenium Webdriver Java","Cypress","Protractor"};
		//The syntax is List listname = Arrays.asList(arrayname);
		List<String> listName = Arrays.asList(courseTitle);
		
		//Create a new array list. fetch the values and compare it with the existing arraylist
		ArrayList<String> a= new ArrayList<String>();
		
		for(int i=0;i<webVal.size();i++) {
			//Add the course title to the arraylist
			a.add(webVal.get(i).getCourseTitle());
			
		}
		
		if (listName.equals(a)) {
			
			System.out.println("The items in both the arraylists are same");
		}
		
		
		

	}

}
