package payloadfiles;

public class Payload {
	
	public static String payloadBody(String isbn,String aisle) {
		
		return "{\r\n" + 
				"\r\n" + 
				"\"name\":\"Learn Nothing\",\r\n" + 
				"\"isbn\":\""+isbn+"\",\r\n" + 
				"\"aisle\":\""+aisle+"\",\r\n" + 
				"\"author\":\"TestAuthor\"\r\n" + 
				"}\r\n" + 
				"";
		
		//Add double quotes,+var+ again " ----> "+locationName"
		
		
	}

}
