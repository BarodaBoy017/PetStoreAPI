package io.swagger.cucumber.steps;
/* 
 Created by Jaydip Patel
 */



import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.swagger.UsersSteps;

import io.swagger.utils.PropertyReader;
import net.thucydides.core.annotations.Steps;
import org.hamcrest.Matchers;
import org.junit.Assert;
import java.util.HashMap;
import java.util.List;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MyStepdefs {

    static int petID;
    static List<String> photoUrl;
    static ValidatableResponse response;
    HashMap<Object, Object> data;
    public static PropertyReader propertyReader;
    static int invalid_id_Expected;
    private int id_Expected;
    static String photoURL_Expected;

    public MyStepdefs() {
        propertyReader = PropertyReader.getInstance();
        id_Expected = Integer.parseInt(propertyReader.getProperty("id"));
        invalid_id_Expected = Integer.parseInt(propertyReader.getProperty("invalid_id"));
        photoURL_Expected = propertyReader.getProperty("photoURL");
    }

    @Steps
    UsersSteps usersSteps;

    /****************************************************************************************************/

    @When("^User upload data on a project by submitting POST request on \"([^\"]*)\" endpoint$")
    public void userUploadDataOnAProjectBySubmittingPOSTRequestOnEndpoint(String arg0) throws Throwable {
        response = usersSteps.addNewPet();
    }

    @Then("^The server should handle it and return a success status and valid Response data\\.$")
    public void theServerShouldHandleItAndReturnASuccessStatusAndValidResponseData() {
        response.assertThat().statusCode(200);//test
        response.contentType(ContentType.JSON);//test
        response.time(Matchers.lessThan(2000L));//test
        petID = response.log().all().extract().path("id");
        photoUrl = response.log().all().extract().path("photoUrls");
        System.out.println("Pet ID : " + petID);
        System.out.println("actual url : " + photoUrl.get(0));
        data = response.extract().path("");
        System.out.println("Data : " + data);


        Assert.assertThat("Response body should have 'id' : ", data, hasKey("id"));//test
        Assert.assertThat("Response body should have 'category' : ", data, hasKey("category"));//test
        Assert.assertThat("Response body should have 'name' : ", data, hasKey("category"));//test
        Assert.assertThat("Response body should have 'tags' : ", data, hasKey("tags"));//test
        Assert.assertThat("Response body should have 'photoUrls' : ", data, hasKey("photoUrls"));//test
        Assert.assertThat("Response body should have 'status' : ", data, hasKey("status"));//test

        Assert.assertEquals("Pet id not matched : ", id_Expected, petID);//test
        //  Assert.assertEquals("Pet id not matched : ",photoURL_Expected,petID);
        Assert.assertEquals("Pet id not matched : ", photoURL_Expected, photoUrl.get(0));//test

        String responseJson = usersSteps.addNewPetForSchema();
        Assert.assertThat(responseJson, matchesJsonSchemaInClasspath("POST_Response_Schema.json"));//Schema Validation
    }


    @When("User want to get information  by submitting GET request on {string} endpoint")
    public void userWantToGetInformationBySubmittingGETRequestOnEndpoint(String arg0) {

        response = usersSteps.getPetById(petID);

    }

    @Then("The requested data is returned successfully")
    public void theRequestedDataIsReturnedSuccessfully() {
        response.assertThat().statusCode(200);//test
        response.contentType(ContentType.JSON);//test
        response.time(Matchers.lessThan(2000L));//test
        data = response.extract().path("");
        System.out.println("Data : " + data);


        Assert.assertThat("Response body should have 'id' : ", data, hasKey("id"));//test
        Assert.assertThat("Response body should have 'category' : ", data, hasKey("category"));//test
        Assert.assertThat("Response body should have 'name' : ", data, hasKey("category"));//test
        Assert.assertThat("Response body should have 'tags' : ", data, hasKey("tags"));//test
        Assert.assertThat("Response body should have 'photoUrls' : ", data, hasKey("photoUrls"));//test
        Assert.assertThat("Response body should have 'status' : ", data, hasKey("status"));//test

        Assert.assertEquals("Pet id not matched : ", id_Expected, petID);//test
        Assert.assertEquals("Pet id not matched : ", photoURL_Expected, photoUrl.get(0));//test

        String responseJson = usersSteps.getPetByIdForSchemaValidation(petID);
        Assert.assertThat(responseJson, matchesJsonSchemaInClasspath("GET_Response_Schema.json"));//Schema Validation
    }

    @When("User upload empty data on a project by submitting POST request on {string} endpoint")
    public void userUploadEmptyDataOnAProjectBySubmittingPOSTRequestOnEndpoint(String arg0) {
        response = usersSteps.addNewPetWithoutPayload();
    }

    @Then("The server should handle it and return a message with {int} status code.")
    public void theServerShouldHandleItAndReturnAMessageWithStatusCode(int statusCode) {
        response.assertThat().statusCode(statusCode);//test
        response.contentType(ContentType.JSON);//test
        response.time(Matchers.lessThan(2000L));//test
        data = response.extract().path("");
        System.out.println("Data : " + data);
        Assert.assertThat("Response body should have 'id' : ", data, hasKey("code"));//test
        Assert.assertThat("Response body should have 'id' : ", data, hasKey("type"));//test
        Assert.assertThat("Response body should have 'id' : ", data, hasKey("message"));//test

        Assert.assertEquals("Status code not matched : ", statusCode, response.extract().statusCode());//test
        Assert.assertEquals("Error message not matched : ", "no data", response.extract().path("message"));//test

    }

    @When("User pass invalid body on a project by submitting POST request on {string} endpoint")
    public void userPassInvalidBodyOnAProjectBySubmittingPOSTRequestOnEndpoint(String arg0) {
        response = usersSteps.addNewPetWithInvalidBody();
    }

    @Then("The server should handle it and return a error message with {int} status code.")
    public void theServerShouldHandleItAndReturnAErrorMessageWithStatusCode(int statusCode) {
        response.assertThat().statusCode(statusCode);//test
        response.contentType(ContentType.JSON);//test
        response.time(Matchers.lessThan(2000L));//test
        data = response.extract().path("");
        System.out.println("Data : " + data);
        Assert.assertThat("Response body should have 'id' : ", data, hasKey("code"));//test
        Assert.assertThat("Response body should have 'id' : ", data, hasKey("type"));//test
        Assert.assertThat("Response body should have 'id' : ", data, hasKey("message"));//test

        Assert.assertEquals("Status code not matched : ", statusCode, response.extract().statusCode());//test
        Assert.assertEquals("Error message not matched : ", "bad input", response.extract().path("message"));//test

    }
}
