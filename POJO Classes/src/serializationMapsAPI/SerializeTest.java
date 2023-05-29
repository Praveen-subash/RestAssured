package serializationMapsAPI;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import pojoPackageSerialization.AddPlace;
import pojoPackageSerialization.Location;

public class SerializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Serialization starts 
		AddPlace p =new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
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
		
		
		Response res=given().log().all().queryParam("key", "qaclick123")
							.body(p)//Java object is passed as json here
							.when().post("/maps/api/place/add/json")
							.then().assertThat().statusCode(200).extract().response();

		String responseString=res.asString();
		System.out.println(responseString);

	}

}
