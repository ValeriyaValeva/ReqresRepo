package mytest;

import constant.BasePaths;
import constant.ErrorMessageConstants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.LoginBodyPojo;
import pojo.LoginErrorMessagePojo;

import static io.restassured.RestAssured.given;

public class FailedLogin {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @BeforeClass
    public void beforeClass() {

        RequestSpecBuilder builder = new RequestSpecBuilder();                      //Setting up Request Specification Builder.
        builder.addHeader("Accept", "application/json, text/plain, */*");
        builder.addHeader("Content-Type", "application/json");
        requestSpec = builder.build();

        ResponseSpecBuilder builderResp = new ResponseSpecBuilder();                //Setting up Response Specification Builder.
        builderResp.expectContentType("application/json");
        responseSpec = builderResp.build();
    }

    @Test
    public void createNewUpType() {

        LoginBodyPojo loginBodyPojo = new LoginBodyPojo();
        loginBodyPojo.setEmail("peter@klaven");

        RestAssuredResponseImpl response = (RestAssuredResponseImpl) given()
                .spec(requestSpec)
                .body(loginBodyPojo)
                .when()
                .post(BasePaths.LOGIN_ENDPOINT);

        response
                .then()
                .spec(responseSpec);

        LoginErrorMessagePojo loginErrorMessagePojo = response.as(LoginErrorMessagePojo.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 400);    //Hard assert?
        softAssert.assertEquals(loginErrorMessagePojo.getError(), ErrorMessageConstants.ERR_MISSING_CRED);

        softAssert.assertAll();
    }
}
