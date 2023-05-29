import org.testng.Assert;

import files.SampleJson;
import io.restassured.path.json.JsonPath;

public class JsonHandling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(SampleJson.jsonFile());
		
		//1. Print No of courses returned by API
		int courseCount = js.getInt("courses.size()");
		System.out.println("The no of courses is "+courseCount);
		
		//2.Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("The purchase amount is "+purchaseAmount);
		
		//3. Print Title of the first course
		String firstCoursetitle = js.get("courses[0].title");
		System.out.println("The title of the first course is "+firstCoursetitle);
	
		//4. Print All course titles and their respective Prices
		
		for(int i=0;i<courseCount;i++) {
			
			String courseTitle = js.get("courses["+i+"].title");
			int coursePrice = js.getInt("courses["+i+"].price");
			System.out.println("The course title is "+courseTitle+" and the course price is "+coursePrice);
			
			//TO get the price in the form of string 
			//METHOD 1
			String strPrice = js.getString("courses["+i+"].price");
			System.out.println(strPrice);
			
			//METHOD 2
			System.out.println(js.get("courses["+i+"].price").toString());
			
		}

		//5. Print no of copies sold by RPA Course
		
		for(int i=0;i<courseCount;i++) {
			
			String courseTitle = js.get("courses["+i+"].title");
			
			if(courseTitle.equalsIgnoreCase("RPA")) {
				
				
				int RPAcopies = js.getInt("courses["+i+"].copies");
				System.out.println("The number of RPA copies is "+RPAcopies);
				break;
				
			}
			
		}

		//6. Verify if Sum of all Course prices matches with Purchase Amount
		
		int finalVal = js.getInt("dashboard.purchaseAmount");
		System.out.println("The purchase amount is "+finalVal);
		
		int sum =0;
		for(int i=0;i<courseCount;i++) {
			
			String courseTitle = js.get("courses["+i+"].title"); 
			int priceVal = js.getInt("courses["+i+"].price");
			int copiesVal = js.getInt("courses["+i+"].copies");
			
			System.out.println("COURSE TITLE "+courseTitle+" COURSE PRICE "+priceVal+" COURSE COPIES "+copiesVal);
			sum = sum + (priceVal*copiesVal);	
			
			
		}
		
		System.out.println("The added value is "+sum);
		Assert.assertEquals(finalVal, sum);
		
	

	}

}

/*{

"dashboard": {
	"purchaseAmount": 910,
	"website": "rahulshettyacademy.com"
},
"courses": [
	{
		"title": "Selenium Python",
		"price": 50,
		"copies": 6
	},

	{
		"title": "Cypress",
		"price": 40,
		"copies": 4
	},
	
	{
		"title": "RPA",
		"price": 45,
		"copies": 10
	}

]

}*/