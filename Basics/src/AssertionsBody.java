
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

import com.google.common.io.Files;

import files.payload;

public class AssertionsBody {

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
			   .body(payload.mapBody())
			 //The body comes from payload.java class for code simplification
		.when().post("maps/api/place/add/json")

		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
			                    .header("Server", "Apache/2.4.18 (Ubuntu)");
			//Add package import static org.hamcrest.Matchers.* to get suggestions for equalTo
			// See the server header under Body in POSTMAN
			//Always server validation is necessary
			//ALWAYS NOTE THAT WHEN THERE'S A HEADER BEFORE THEN, IT IS INPUT VALIDATION
			//WHEN THERE'S A HEADER AFTER THEN, IT IS OUTPUT VALIDATION
			
		

	}

}
