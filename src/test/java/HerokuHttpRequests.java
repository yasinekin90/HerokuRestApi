import POJOS.AuthPojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

/**
 * given()
 * content-type,set cookies,add auth ,add param,set headers info etc..
 *
 * when()
 * get,post,put,delete
 *
 * then()
 *validate status code,extract response,extract headers cookies & response body...
 */
import static ReusableMethods.ReusableMethods.*;
import static org.hamcrest.Matchers.*;


public class HerokuHttpRequests {

    String token;
    int id;

    @BeforeTest
    public void baseURI_and_Auth() {

        AuthPojo authPojo = new AuthPojo("admin", "password123");
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Response response =
                given().header("Content-Type", "application/json")
                        .and().body(authPojo)
                        .when().post("/auth")
                        .then().assertThat().statusCode(Matchers.oneOf(200, 201))
                        .extract().response();

        token = response.jsonPath().getString("token");
    }

    @Test(priority = 1)
    public void createBooking() {
        Response response = given().header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .and().body(jsonPayLoad())
                .when().post("/booking")
                .then().assertThat()
                .statusCode(Matchers.oneOf(200, 201))
                .contentType("application/json; charset=utf-8")
                .header("Connection", "keep-alive").extract().response();


        id = response.jsonPath().getInt("bookingid");

    }

    @Test(priority = 2)
    public void getBook() {
        given().header("Authorization", "Bearer " + token)
                .when().get("/booking/" + id)
                .then().assertThat()
                .statusCode(Matchers.oneOf(200, 201))
                .log().all();


    }

    @Test(priority = 3)
    public void getBookings() {
        Response response = given().header("Authorization", "Bearer " + token)
                .when().get("/booking")
                .then().assertThat()
                .statusCode(oneOf(200, 201))
                .extract().response();
        JSONArray jo = new JSONArray(response.asString());

        System.out.println(jo.getJSONObject(0).get("bookingid"));


    }

    @Test(priority = 4)
    public void updateBooking() {
        Response response = given().header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .and().body(jsonPayLoad())
                .when().put("/booking/" + id)
                .then().assertThat().statusCode(Matchers.oneOf(200, 201))
                .log().all().extract().response();


    }

    @Test(priority = 5)
    public void deleteBooking() {
        given().header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .when().delete("/booking/" + id)
                .then().extract().response()
                .then().assertThat()
                .statusCode(Matchers.oneOf(200, 201))
                .contentType("text/plain; charset=utf-8")
                .header("Server", "Cowboy")
                .body(equalTo("Created"))
                .log().all();
    }

}
