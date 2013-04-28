package org.greg.resteasy.pojo.response;

public class Helloworld {

	String	message;
	
	public Helloworld() {
		
	}
	
	public Helloworld(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
