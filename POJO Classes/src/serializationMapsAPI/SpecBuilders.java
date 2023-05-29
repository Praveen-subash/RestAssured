package serializationMapsAPI;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoPackageSerialization.AddPlace;
import pojoPackageSerialization.Location;

public class SpecBuilders {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Serialization starts 
		AddPlace p =new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IS");
		p.setPhone_number("(+91) 983 123 3937");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName("Frontline house");
		
		//This list is to create list of strings
		List<String> myList =new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		
		//This is for sub classes
		Location l =new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		p.setLocation(l);
		
		
		
		
		//Keep this for reference and compare it with below Reqquest specification
		
		/*Response res=given().log().all().queryParam("key", "qaclick123")
							.body(p)
							.when().post("/maps/api/place/add/json")
							.then().assertThat().statusCode(200).extract().response();

		String responseString=res.asString();
		System.out.println(responseString);*/
		
		//REQUEST SPECIFICATION - given()
		//RESPONSE SPECIFICATION - then()
		
		//Request spec builder is common utils for inputs (Base URI, header)
		//Create a object for RequestSpecification class
		RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
															.setContentType(ContentType.JSON).build();  //Contenttype.json is optional. Just for understanding
		
		
		//Spec is for specifying the request specification
		//HERE, WE ARE BREAKING THE WHOLE CODE. ONLY GIVEN IS THERE. So, THE RETURN TYPE IS RequestSpecification not String response
		RequestSpecification response = given().spec(req).body(p); //we should give only generic methods in spec builder. body is not unique for all
		
		//Request spec builder is common utils for request retrieval (Base URI, header)
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		//INSTEAD OF GETTING IT AS String response, we are getting it in this way and converting as string
		Response finalResponse = response.when().post("/maps/api/place/add/json").then().spec(res).extract().response();
		
		String strResponse = finalResponse.asString();
		
		System.out.println(strResponse);
		
		
		
		
	                             
	                             
		
	}

}
