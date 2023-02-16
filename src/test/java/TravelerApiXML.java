
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers;

import io.restassured.response.Response;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static ReusableMethods.ReusableMethods.*;

import static io.restassured.RestAssured.*;

public class TravelerApiXML {

    Response response;
    String id;
    String payload = xmlPayload();



    @BeforeTest
    void getURI()
    {
        RestAssured.baseURI = "http://restapi.adequateshop.com";
    }


    @Test(priority = 1)
    void createTraveler() throws IOException, SAXException {
        response = given().header("Content-Type", "application/xml")
                .header("Accept", "application/xml")
                .body(payload)
                .when().post("/api/Traveler")
                .then()
                .log().all().extract().response();

        id = idFromXmlResponse(response);
        xmlSchemaValidation(response);

    }

    @Test(priority = 2)
    void getTraveler()
    {
        given().header("Accept", "application/xml")
                .when().get("/api/Traveler/" + id + "")
                .then()
                .assertThat()
                .body(RestAssuredMatchers.matchesXsdInClasspath("travelerXMLSchema.xsd"))
                .log().all();

    }

    @Test(priority = 3)
    void updateTraveler() throws JsonProcessingException
    {
        given().header("Content-Type", "application/xml")
                .header("Accept", "application/xml")
                .body(xmlPayload())
                .when().put("/api/Traveler/" + id + "")
                .then().log().all();

    }

    @Test(priority = 4)
    void deleteTraveler()
    {
        given()
                .header("Accept", "application/xml")
                .when().delete("/api/Traveler/" + id + "")
                .then().log().all();
    }


}



