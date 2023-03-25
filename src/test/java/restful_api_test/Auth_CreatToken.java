package restful_api_test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.*;

public class Auth_CreatToken {
    String token = "aabc7ef1ce7ed87";
    String url = "https://restful-booker.herokuapp.com";

    Response response;
    String body = "{\"username\" : \"admin\",\n" +
            "    \"password\" : \"password123\"\n" +
            "}";

    @DisplayName("Post Auth_Create token")
    @Test()
    public void postCreatToken() {
         response = given().body(body).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post(url + "/auth").then().statusCode(200).extract().response().prettyPeek();


    }

    @DisplayName("Get Booking ID")
    @Test()
    public void getBookingID() {
        Response response1 = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .pathParam("id",988)
                .when().get(url + "/booking/{id}").prettyPeek()
                .then().statusCode(200).extract().response();
        //List<Map<String,Objects>>

    }
    @DisplayName("Get Booking ID")
    @Test()
    public void getBookingID2() {
        Response response1 = given().accept(ContentType.JSON).contentType(ContentType.JSON)

                .when().get(url + "/booking").prettyPeek()
                .then().statusCode(200).extract().response();


    }

    @DisplayName("Get Booking firstname")
    @Test()
    public void getBookingFirstname() {
        String firstName = "sally";
        String lastName = "brown";
        given().accept(ContentType.JSON)
                .queryParam("firstname", firstName)
                .queryParam("lastname", lastName)
                .when().get(url + "/booking").then().statusCode(200);

    }

    @DisplayName("Get Booking check-in and check-out")
    @Test()
    public void getBookingCheckinOut() {
        given().accept(ContentType.JSON)
                .queryParam("checkin", "2014-03-13")
                .queryParam("checkout", "2014-05-21")
                .when().get(url + "/booking").then().statusCode(200);
    }

    @DisplayName("Create booking (Post)")
    @Test()
    public void postBooking() {
        String postBody = "{\n" +
                "    \"firstname\" : \"Tay\",\n" +
                "    \"lastname\" : \"Sornloon\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        JsonPath jsonPath = given().body(postBody).contentType(ContentType.JSON)
                .pathParam("id",1)
                .when().post(url+"/booking/{id}").prettyPeek().then().statusCode(201).extract().jsonPath();


    }
    @DisplayName("update (put) booking ")
    @Test()
    public void putBooking() {
        String cookieToken = "a5c30e101363c17";

        String putUpdate = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        given().body(putUpdate).contentType(ContentType.JSON)
                .pathParam("id",1)
                .cookie("token",cookieToken)
                .when().put(url+"/booking/{id}").prettyPeek()
                .then().statusCode(200);




    }
    @DisplayName("update (patch) booking ")
    @Test()
    public void patchBooking() {
        String cookieToken = "ea77a82ce7f7c62";

        String patchUpdate = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\"\n" +
                "}";

        given().body(patchUpdate).contentType(ContentType.JSON)
                .cookie("token",cookieToken)
                .pathParam("id",1).when().patch(url+"/booking/{id}").prettyPeek()
                .then().statusCode(200);
    }
    @DisplayName("Delete  booking ")
    @Test()
    public void deleteBooking() {
        String cookieToken = "a5c30e101363c17";

        given().contentType(ContentType.JSON).cookie("token",cookieToken)
                .when().delete(url+"/booking/27").then().statusCode(201);
    }
    @DisplayName("Ping health to check endpoint if API is up and run ")
    @Test()
    public void pingBooking() {
            given().log().all().when().get(url+"/ping").then().statusCode(201);
    }
    public void getBookingTest() {
        int bookingId = 13;
        Response response = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking/" + bookingId)
                .when()
                .get();

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("application/json; charset=utf-8", response.getContentType());
        Assertions.assertNotNull(response.getBody().jsonPath().getString("bookingid"));

    }

}


