import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import files.payload;


public class JsonBasics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		//Given does not give suggestions
		// Add this package import static io.restassured.RestAssured.*;
		//Header content type gets populated when we enter the details
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			   .body(payload.mapBody())
			 //The body comes from payload.java class for code simplification
		.when().post("maps/api/place/add/json")

		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
			   .header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString(); 
												
			
			//Add package import static org.hamcrest.Matchers.* to get suggestions for equalTo
			// See the server header under Body in POSTMAN
			//Always server validation is necessary
			//ALWAYS NOTE THAT WHEN THERE'S A HEADER BEFORE THEN, IT IS INPUT VALIDATION
			//WHEN THERE'S A HEADER AFTER THEN, IT IS OUTPUT VALIDATION
		
		System.out.println(response);
		//Two outputs will be printed. One for log.all and one for Sysout
		
		//JSON PATH ACCEPTS STRING AND PARSES IT AND CONVERTS IT INTO JSON
		JsonPath js = new JsonPath(response);
		String locationName = js.getString("place_id"); //This place id is from json and not string
		//If we have the flow, we have to give the flow acc to json. Ex : location.lattitude
		//That is why it is in ""
		
		System.out.println("THE LOCATION RETRIEVED IS - "+locationName);
		
		
		
		
		
		
		
		
		

	}

}
