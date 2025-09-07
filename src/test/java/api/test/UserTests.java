package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import com.github.javafaker.Faker;

import api.endpoints.UserEndpointsMethods;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayload;
	public Logger logger = LogManager.getLogger(UserTests.class);

	@BeforeMethod
	public void setupData() {
		faker = new Faker();
		userPayload = new User();

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername("twasif");
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		userPayload.setUserStatus(1);

		logger.info("Setup data completed");
	}

	@Test(priority = 1)
	public void testPostuser() {
		logger.info("Create Post User");
		Response response = UserEndpointsMethods.createUser(userPayload);
		response.then().log().all();

		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}
	
	
	@Test(priority = 2, dependsOnMethods = {"testPostuser"})
	public void testGetUserByName() {
		logger.info("Read User");
		Response response = UserEndpointsMethods.readUser(userPayload.getUsername());
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}
	
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		logger.info("Update User");

		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		
		Response response = UserEndpointsMethods.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.statusCode(), 200);
		
		
		//Check data after updating
		Response responseAfter_Update = UserEndpointsMethods.readUser(this.userPayload.getUsername());
		
		AssertJUnit.assertEquals(responseAfter_Update.getStatusCode(), 200);
	}
	
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		logger.info("Delete User");
		Response response = UserEndpointsMethods.deleteUser(this.userPayload.getUsername());
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}
	
}
