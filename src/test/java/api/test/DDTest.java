package api.test;

import api.endpoints.UserEndpointsMethods;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class DDTest {

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)   //The "Data" string is taken from the "DataProvider" class
    public void testPostUser(String UserId, String Username, String FirstName,
                             String LastName, String Email, String Password, String Phone){
        User userPayload = new User();

        userPayload.setId((int) Double.parseDouble(UserId));;
        userPayload.setUsername(Username);
        userPayload.setFirstname(FirstName);
        userPayload.setLastname(LastName);
        userPayload.setEmail(Email);
        userPayload.setPassword(Password);
        userPayload.setPhone(Phone);
        userPayload.setUserStatus(1);

        Response response = UserEndpointsMethods.createUser(userPayload);
        response.then().log().all();

        AssertJUnit.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, dataProvider = "UserName", dataProviderClass = DataProviders.class)
    public void testDeleteUser(String UserName){
        User userPayload = new User();

        Response response = UserEndpointsMethods.deleteUser(UserName);
        AssertJUnit.assertEquals(response.getStatusCode(), 200);
    }
}
