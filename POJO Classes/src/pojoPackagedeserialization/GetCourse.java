package pojoPackagedeserialization;

public class GetCourse {
	
	private String url;
	private String services;
	private String expertise;
	private String instructor;
	private String linkedIn;
	
	//We are linking the child class CourseChildClass to the parent class
	//It'll check that the class CoursesChildClass is present. Compiler executes that class
	private CoursesChildClass courses;
	
	public CoursesChildClass getCourses() {
		return courses;
	}
	
	//Even the return type comes from CourseChildClass
	public void setCourses(CoursesChildClass courses) {
		this.courses = courses;
	}
	
	
	//Declare the variables and give ALT+SHIFT+S to generate getter and setter methods
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	

	
}
