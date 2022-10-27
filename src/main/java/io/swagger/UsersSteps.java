package io.swagger;
/* 
 Created by Jaydip Patel
 */


import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.swagger.constants.EndPoints;
import io.swagger.model.Category;
import io.swagger.model.Pet;
import io.swagger.model.Tag;
import io.swagger.utils.PropertyReader;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.Arrays;

public class UsersSteps {
    public static PropertyReader propertyReader;
    static int invalid_id;
    private int id;
    static String photoURL;


    public UsersSteps() {
        propertyReader = PropertyReader.getInstance();
        id = Integer.parseInt(propertyReader.getProperty("id"));
        invalid_id = Integer.parseInt(propertyReader.getProperty("invalid_id"));
        photoURL = propertyReader.getProperty("photoURL");
    }


    @Step("Add a new pet to the Store : ")
    public ValidatableResponse addNewPet() {
        Category category = new Category();
        category.setId(34L);
        category.setName("Sporting_Group");

        Tag tag = new Tag();
        tag.setId(34L);
        tag.setName("Army_Sport");

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName("Axel");
        pet.setStatus("available");
        pet.setCategory(category);
        pet.setTags(Arrays.asList(tag));
        pet.setPhotoUrls(Arrays.asList(photoURL));
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post(EndPoints.POST_PET)
                .then();
    }

    @Step("Add a new pet to the Store for Schema validation : ")
    public String addNewPetForSchema() {
        Category category = new Category();
        category.setId(34L);
        category.setName("Sporting_Group");

        Tag tag = new Tag();
        tag.setId(34L);
        tag.setName("Army_Sport");

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName("Axel");
        pet.setStatus("available");
        pet.setCategory(category);
        pet.setTags(Arrays.asList(tag));
        pet.setPhotoUrls(Arrays.asList(photoURL));
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post(EndPoints.POST_PET)
                .getBody().asString();
    }

    @Step("Find pet by ID : ")
    public ValidatableResponse getPetById(int petId) {

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("petId", petId)
                .get(EndPoints.GET_PET_BY_ID)
                .then();
    }

    @Step("Find pet by ID For SchemaValidation : ")
    public String getPetByIdForSchemaValidation(int petId) {

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("petId", petId)
                .get(EndPoints.GET_PET_BY_ID)
                .getBody().asString();
    }

    @Step("Add a new pet to the Store with invalid input body : ")
    public ValidatableResponse addNewPetWithInvalidBody() {

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body("Test 1 invalid body")
                .when()
                .post(EndPoints.POST_PET)
                .then();
    }

    @Step("Add new Pet without payload/body : ")
    public ValidatableResponse addNewPetWithoutPayload() {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .post(EndPoints.POST_PET)
                .then();
    }
}
