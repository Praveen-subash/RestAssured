import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class EndToEnd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//HERE, WE ARE POSTING THE DATA, UPDATING IT AND VALIDATING IT
		
		
		
		//==============================STEP 1 - POST the API data================================
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		System.out.println("===============THE RESPONSE PRINTED FROM POST IS ================");
		//Given does not give suggestions
		// Add this package import static io.restassured.RestAssured.*;
		//Header content type gets populated when we enter the details
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			   .body(payload.mapBody())
			 //The body comes from payload.java class for code simplification
		.when().post("maps/api/place/add/json")

		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
			   //.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString(); 
		.extract().response().asString();
			
			//Add package import static org.hamcrest.Matchers.* to get suggestions for equalTo
			// See the server header under Body in POSTMAN
			//Always server validation is necessary
			//ALWAYS NOTE THAT WHEN THERE'S A HEADER BEFORE THEN, IT IS INPUT VALIDATION
			//WHEN THERE'S A HEADER AFTER THEN, IT IS OUTPUT VALIDATION
		System.out.println("===============THE RETRIEVED RESPONSE FROM POST USING STRING IS ================");
		System.out.println(response);
		
		//Two outputs will be printed. One for log.all and one for Sysout
		
		//We are getting the place id in the form of variable.
		JsonPath js = new JsonPath(response);
		String locationName = js.getString("place_id");
		
		System.out.println("===============THE LOCATION RETRIEVED FROM JSON IS ================");
		System.out.println(locationName);
		
		//==============================STEP 2 - UPDATE the address ================================
		
		System.out.println("===============THE RESPONSE PRINTED FROM PUT IS ================");
		
		
		String newAddress = "70 winter walk, USA"; 
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		//The body is taken from put method body. Place id should be taken from the location name captured
		//Since, we are changing the address for the created placeid
		   .body("{\r\n" + 
		   		"    \"place_id\": \""+locationName+"\",\r\n" + 
		   		"    \"address\": \""+newAddress+"\",\r\n" + 
		   		"    \"key\": \"qaclick123\"\r\n" + 
		   		"}")
		   
		 //Add double quotes,+var+ again " ----> "+locationName"
		    
		.when().put("/maps/api/place/update/json")
			
		//Instead of assert, we can also capture it using JsonPath and assert it.
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//==============================STEP 3 - GET the updated address ================================
		
		System.out.println("===============THE RESPONSE PRINTED FROM GET IS ================");
		
		String getAddress = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", locationName)
		//NOTE THAT HEADER IS NOT NEEDED IN GET			
					 
		.when().get("/maps/api/place/get/json")
		
		
		.then().assertThat().log().all().statusCode(200).body("address", equalTo(newAddress)).extract().response().asString();
		
		System.out.println("===============THE RETRIEVED RESPONSE FROM GET USING STRING IS ================");
		System.out.println(getAddress);
		//ALWAYS NOTE THAT WE CAN ASSERT IT DIRECTLY OR GET IT AS A STRING AND ASSERT IT USING JsonPath
		
		JsonPath jp = new JsonPath(getAddress);
		String getJsonAddress = jp.getString("address");
		
		System.out.println("===============THE UPDATED ADDRESS FROM JSON IS ================");
		System.out.println(getJsonAddress);
		
		Assert.assertEquals(newAddress, getJsonAddress);
		
		                   
		
	
	
	}

}
