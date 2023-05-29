import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;

public class DemoProject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//GIVEN - ALL INPUT DETAILS
		//WHEN - SUBMIT THE API (Resource, http Method)
		//THEN - VALIDATE THE RESPONSE
		
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		//Given does not give suggestions
		// Add this package import static io.restassured.RestAssured.*;
		//Header content type gets populated when we enter the details
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			.body("{\r\n" + 
					"  \"location\": {\r\n" + 
					"    \"lat\": -38.383494,\r\n" + 
					"    \"lng\": 33.427362\r\n" + 
					"  },\r\n" + 
					"  \"accuracy\": 50,\r\n" + 
					"  \"name\": \"Frontline house\",\r\n" + 
					"  \"phone_number\": \"(+91) 983 893 3937\",\r\n" + 
					"  \"address\": \"29, side layout, cohen 09\",\r\n" + 
					"  \"types\": [\r\n" + 
					"    \"shoe park\",\r\n" + 
					"    \"shop\"\r\n" + 
					"  ],\r\n" + 
					"  \"website\": \"http://google.com\",\r\n" + 
					"  \"language\": \"French-IN\"\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"")
			
			//Here, paste the body. Eclipse will convert into string format which java accepts
			//If eclipse doesnt convert, goto Windows->Preferences->Java->Editor->Typing
			//Make sure that Escape Text when Pasting into String Literal is checked
			
			.when().post("maps/api/place/add/json")
			
			//Resource name and http method should be given in when
			//Given all the details, submit the API with http request and method name using when
			//Within the http request, give the request details
			
			.then().log().all().assertThat().statusCode(200);
			
			//then is used for validations to check whether status code is 200
			
			//To get the log details, add log.all in given and then

	}

}
