package payloadfiles;

import io.restassured.path.json.JsonPath;

public class JsonPathParam {
	
	public static JsonPath jsonFunction(String strResponse) {
		
		JsonPath js = new JsonPath(strResponse);
		return js;
		
	}

}
