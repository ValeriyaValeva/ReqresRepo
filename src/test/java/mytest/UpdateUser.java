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

public class UpdateUser extends BaseAuthentication {
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
    public void updateUser() {

        NewUserRequestPojo updateUser = new NewUserRequestPojo(
                "morpheus",
                "zion resident"
        );

        RestAssuredResponseImpl response = (RestAssuredResponseImpl) given()
                .spec(requestSpec)
                .body(updateUser)
                .when()
                .put(BasePaths.UPDATE_USER);

        response
                .then()
                .spec(responseSpec);

        NewUserErrorMessagePojo newUserErrorMessagePojo = response.as(NewUserErrorMessagePojo.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200);    //Hard assert?
        softAssert.assertEquals(newUserErrorMessagePojo.getName(), updateUser.getName());
        softAssert.assertEquals(newUserErrorMessagePojo.getJob(), updateUser.getJob());
        //softAssert.assertEquals(newUserErrorMessagePojo.getCreatedAt(), "");

        softAssert.assertAll();
    }
}
