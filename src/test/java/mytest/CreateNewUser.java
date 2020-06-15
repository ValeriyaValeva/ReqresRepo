package mytest;

import constant.BasePaths;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.NewUserErrorMessagePojo;
import pojo.NewUserRequestPojo;

import static io.restassured.RestAssured.given;

public class CreateNewUser extends BaseAuthentication {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @BeforeClass
    public void beforeClass() {

        RequestSpecBuilder builder = new RequestSpecBuilder();                      //Setting up Request Specification Builder.
        builder.addHeader("Accept", "application/json, text/plain, */*");
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("Authorization", "Bearer " + accessToken);
        requestSpec = builder.build();

        ResponseSpecBuilder builderResp = new ResponseSpecBuilder();                //Setting up Response Specification Builder.
        builderResp.expectContentType("application/json");
        responseSpec = builderResp.build();
    }

    @Test
    public void createNewUpType() {

        NewUserRequestPojo newUser = new NewUserRequestPojo(
                "morpheus",
                "leader"
        );

        RestAssuredResponseImpl response = (RestAssuredResponseImpl) given()
                .spec(requestSpec)
                .body(newUser)
                .when()
                .post(BasePaths.CREATE_NEW_USER);

        response
                .then()
                .spec(responseSpec);

        NewUserErrorMessagePojo newUserErrorMessagePojo = response.as(NewUserErrorMessagePojo.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 201);    //Hard assert?
        softAssert.assertEquals(newUserErrorMessagePojo.getName(), newUser.getName());
        softAssert.assertEquals(newUserErrorMessagePojo.getJob(), newUser.getJob());
        //softAssert.assertEquals(newUserErrorMessagePojo.getCreatedAt(), "");

        softAssert.assertAll();
    }
}
