import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloadfiles.JsonPathParam;
import payloadfiles.Payload;

import static io.restassured.RestAssured.*;

public class DataProviderAddBody {
	
	@Test(dataProvider = "getData")
	public void postLibraryAPI(String isbn, String aisle) {
		
		//GIVEN - ALL INPUT DETAILS
		//WHEN - SUBMIT THE API (Resource, http Method)
		//THEN - VALIDATE THE RESPONSE
				
		RestAssured.baseURI = "http://216.10.245.166";
		//Given does not give suggestions
		// Add this package import static io.restassured.RestAssured.*;
		//Header content type gets populated when we enter the details
		System.out.println("===============THE RESPONSE PRINTED FROM POST IS ================");
		
		
		//ADD PARAMETERS TO THE BODY
		String response = given().log().all().header("Content-Type", "application/json")
				                 .body(Payload.payloadBody(isbn,aisle))
				                 
	    //Here, the data has to be changed every time to get the response.
	    // So we are sending data every time	                 
		
		.when().post("Library/Addbook.php")
		
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("===============THE RETRIEVED RESPONSE FROM POST USING STRING IS ================");
		System.out.println(response);
		
		//Two outputs will be printed. One for log.all and one for Sysout
		
		JsonPath jp = JsonPathParam.jsonFunction(response);
		String bookID = jp.getString("ID");
		
		System.out.println("===============THE ID RETRIEVED FROM JSON IS ================");
		System.out.println(bookID);
		
		//Here, change the parameters in payload so that the test gets passed.
		//Everytime, we have to send new parameters for the ID to get generated
		
//================================================================================================
		//HERE, WE ARE DELETING THE CREATED ADDRESS SO THAT WE DONT HAVE TO GIVE NEW PARAMETERS EVERYTIME
		
		System.out.println("===============THE RESPONSE PRINTED FROM DELETE IS ================");
		String delString = given().log().all().header("Content-Type", "application/json")
				                   .body("{\r\n" + 
				                   		" \r\n" + 
				                   		"\"ID\" : \""+bookID+"\"\r\n" + 
				                   		" \r\n" + 
				                   		"} \r\n" + 
				                   		"")
		
		.when().delete("Library/DeleteBook.php")

		//Here, the data has to be changed every time to get the response.
		// So we are sending data every time	                 
		
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("===============THE RETRIEVED RESPONSE FROM DELETE USING STRING IS ================");
		System.out.println(delString);
		
		JsonPath jsn = JsonPathParam.jsonFunction(delString);
		String delMsg = jsn.getString("msg");
		
		System.out.println("===============THE MESSAGE RETRIEVED FROM DELETE JSON IS ================");
		System.out.println(delMsg);
		
		
	}
	
//=============================================================================================

	//The data gets passed to post method
	@DataProvider
	
	public Object[][] getData() {
		
		Object[][] data = {{"dark17","123"},{"dark18","456"},{"dark19","789"}};
		return data;

	}
	


}
