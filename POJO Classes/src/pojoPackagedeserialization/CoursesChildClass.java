package pojoPackagedeserialization;

import java.util.List;

public class CoursesChildClass {
	
	//After creating WebAutomationClass,ApiClass,MobileClass
	//Declare it and ALT SHIFT S for fetter setter methods 
	
	//We are specifying it as list bcos there might be many course title and price inside each classes
	private List<WebAutomationClass> webAutomation;
	private List<ApiClass> api;
	private List<MobileClass> mobile;
	
	
	public List<WebAutomationClass> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<WebAutomationClass> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<ApiClass> getApi() {
		return api;
	}
	public void setApi(List<ApiClass> api) {
		this.api = api;
	}
	public List<MobileClass> getMobile() {
		return mobile;
	}
	public void setMobile(List<MobileClass> mobile) {
		this.mobile = mobile;
	}
	
	
	/*
	STEP 1
	
	Before creating the list code will look like this.
	 
	private WebAutomationClass webAutomation;
	private ApiClass api;
	private MobileClass mobile;
	
	
	public WebAutomationClass getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(WebAutomationClass webAutomation) {
		this.webAutomation = webAutomation;
	}
	public ApiClass getApi() {
		return api;
	}
	public void setApi(ApiClass api) {
		this.api = api;
	}
	public MobileClass getMobile() {
		return mobile;
	}
	public void setMobile(MobileClass mobile) {
		this.mobile = mobile;
	} 
  STEP 2
   Now change it as and then create getter setter methods
   //WE ARE CHANGING IT AS LIST BCOS WE HAVE MULTIPLE courseTitle and Price inside API, MOBILE,WEBAUTOMATION
    private List<WebAutomationClass> webAutomation;
	private List<ApiClass> api;
	private List<MobileClass> mobile;
	
	*/
	
	
	
	

}
