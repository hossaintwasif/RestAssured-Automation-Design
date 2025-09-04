package api.endpoints;

import org.testng.annotations.Test;

import api.payload.User;
//import api.endpoint.Routes;
//import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class UserEndpointsMethods {

	//Create user
	public static Response createUser(User payload){
		List<User> users = new ArrayList<>();
		users.add(payload);
		
		Response response = given()
		   .contentType(ContentType.JSON)
		   .accept(ContentType.JSON)
		   .body(Collections.singletonList(payload))
		
		.when()
		   .post(Routes.post_url);
		
		return response;
	}
	
	
	//Read user
	public static Response readUser(String userName){
		
		Response response = given()
		    .pathParam("username", userName)
		    
		.when()
		    .get(Routes.get_url);
		
		return response;
	}
	
	
	//Update user
	public static Response updateUser(String userName, User payload){
		
		Response response = given()
		    .contentType(ContentType.JSON)
		    .accept(ContentType.JSON)
		    .pathParam("username", userName)
		    .body(payload)
		    
		.when()
		    .put(Routes.update_url);
		
		return response;
	}
	
	
	//Delete User
	public static Response deleteUser(String userName){
		
		Response response = given()
		    .pathParam("username", userName)
		    
		.when()
		    .delete(Routes.delete_url);
		
		return response;
	}
}
