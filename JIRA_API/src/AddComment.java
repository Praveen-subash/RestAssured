import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class AddComment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//http://localhost:8080/rest/api/2/issue/10005/comment
		//BaseURI = http://localhost:8080
		//Resource = /rest/api/2/issue/<Path Param>/comment
		//Path parameters = 10005 (ID)
		
		//ALWAYS NOTE THAT QUERY PARAMETERS START WITH ?
		//PATH PARAMETERS START DOES NOT START WITH ?
		//Also, learn to differentiate b/w resource and parameters
		//Resource is given with url while parameters are manually given
		RestAssured.baseURI = "http://localhost:8080";
		
		//This session id is a alternate from JsonPath
		//Since everytime we need session ID, it becomes a big task to use Json Path, 
		//parse it and then use it again in a variable

		/*    "session": {
	        "name": "JSESSIONID",
	        "value": "62127FAF7CC2B89CA50DC84B5BA95ED5"
	    }*/
		
		//THIS SESSION FILTER TAKES THE Session ID ---> "JSESSIONID-62127FAF7CC2B89CA50DC84B5BA95ED5"
		//THIS GETS STORED IF YOU GIVE filter(sessionID)
		//NOW THIS CAN BE USED ANYWHERE DIRECTLY FOR GIVING THE COOKIE.
		//THIS IS APPLICABLE ONLY WHEN COOKIES ARE GIVEN WITH SESSION ID's
		//ALWAYS NOTE THAT NEW SESSION ID SHOULD BE GIVEN AS COOKIE FOR ALL OPERATIONS
		
		//============================1. CREATE SESSION ID===============================
		
		System.out.println("===============THE RESPONSE PRINTED FROM POST FOR SESSION ID IS ================");
		
		SessionFilter sessionID = new SessionFilter();
		
		//NOTE THAT relaxedHTTPSValidation - Even when there's no proper server certificate, it'll run.
		String response = given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json")
		                   .body("{ \"username\": \"spraveen879\", \r\n" + 
		                   		"\"password\": \"Facepalm1!0\" }").filter(sessionID)
		
	     .when().post("/rest/auth/1/session")
	     .then().log().all().extract().response().asString();
		
		System.out.println("===============THE RETRIEVED RESPONSE FROM POST FOR SESSION ID USING STRING IS ================");
		System.out.println(response);
		
		//============================2. ADD COMMENT===============================
		
		System.out.println("===============THE RESPONSE PRINTED FROM POST FOR ADDING COMMENT IS ================");
		
		String cmntName = "New comment";
		//HERE, NOTE THAT PATH PARAMETERS SHOULD BE GIVEN IN GIVEN AND NOT WHEN
		//So, we are referring the key as path parameter so that both gets appended and forms the endpoint
		String cmntResponse = given().log().all().pathParam("key","10005").header("Content-Type","application/json")
							.body("{\r\n" + 
									"    \"body\": \""+cmntName+"\",\r\n" + 
									"    \"visibility\": {\r\n" + 
									"        \"type\": \"role\",\r\n" + 
									"        \"value\": \"Administrators\"\r\n" + 
									"    }\r\n" + 
									"}").filter(sessionID)
		.when().post("rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		
		System.out.println("===============THE RETRIEVED RESPONSE FROM POST FOR ADDING COMMENT USING STRING IS ================");
		System.out.println(cmntResponse);
		
		JsonPath js = new JsonPath(cmntResponse);
		String commentID = js.getString("id");
		
		System.out.println("===============THE COMMENT ID RETRIEVED FROM ADD COMMENT IS ================");
		System.out.println(commentID);
		
		//============================3. ADD ATTACHMENT===============================
		
		System.out.println("===============THE RESPONSE PRINTED FROM POST FOR ADDING ATTACHMENT IS ================");
		
		given().log().all().pathParam("key","10005").header("X-Atlassian-Token","no-check")
							.multiPart("file",new File("jira.txt")).filter(sessionID) 
		//Instead of body, we are giving multipart. Check the body in postman
		//Jira.txt is created by rtclick->new->file.
		//Since it is within the project folder, we dont have to specify the full path
		
		.when().post("rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//============================4. GET ISSUE===============================
		
		System.out.println("===============THE RESPONSE PRINTED FROM GET FOR ISSUE DETAILS IS ================");
		
		String issueName = given().log().all().pathParam("key","10005").filter(sessionID)

							.when().get("rest/api/2/issue/{key}")
							.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("===============THE RETRIEVED RESPONSE FROM GET FOR ISSUE DETAILS USING STRING IS ================");
		System.out.println(issueName);
		
		//COPY THE RESPONSE AND PASTE IT IN JSON EDITOR ONLINE
		//object-field-comment-comments
		//FOLLOW THIS PATH TO KNOW HOW MANY COMMENTS ARE ADDED
		//https://jsoneditoronline.org/#left=local.jubete&right=local.dotevo
		
		//============================5. DIFFERENCE BETWEEN QUERY AND PATH PARAMETERS ===============================
		
		System.out.println("===============THE RESPONSE PRINTED FROM GET FOR ADDED QUERY PARAMETERS IS ================");
		
		String queryParamStr = given().log().all().pathParam("key","10005").filter(sessionID)
				                                  .queryParam("fields", "comment")

							.when().get("rest/api/2/issue/{key}")
							.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("===============THE RETRIEVED RESPONSE FROM GET FOR ADDED QUERY PARAMETERS IS================");
		System.out.println(queryParamStr);
		
		//NOW TO KNOW THE DIFFERENCE BETWEEN 4 AND 5, COPY BOTH THE issueName and queryParamStr IN JSON EDITOR ONLINE IN TWO WINDOWS
		//SEE THE FILTER RESULTS. THE SECOND RESPONSE GETS LIMITED TO COMMENTS
		
		//============================6. VALIDATE WHETHER THE COMMENT IS ADDED ===============================
		
		JsonPath jp = new JsonPath(queryParamStr);
		int count = jp.getInt("fields.comment.comments.size()");
		
		//Out of all the comments made, we are getting the comment id from the added comment
		//For that comment id, we are traversing and checking whther the body of the added comment and this are same
		
		for(int i=0;i<count;i++) {
			
			String validateCmnt = jp.getString("fields.comment.comments["+i+"].id");
			
			if(validateCmnt.equals(commentID)) {
				
				System.out.println("Comment id is matched with id name "+commentID);
				
				String cmntBody = jp.getString("fields.comment.comments["+i+"].body");
				
				if(cmntBody.equals(cmntName)) {
					
					System.out.println("The comment body is matched - "+cmntName);
					
				}
				
				
			}
			
			//REFER POSTMAN JIRA API NOTES FOR CLEAR EXPLANATION
			
			//CONCLUSION : 	WE ARE SENDING ONE MORE PARAMETER AS QUERY PARAMETER AND LIMITING THE RESPONSE
			// AND NOW, WE ARE ASSERTING WHETHER THE ADDED COMMENT IS SAME AS WHEN WE LIMIT THE RESPONSE THROUGH QUERY PARAMETER
			
			
		}
		
		

	}

}
