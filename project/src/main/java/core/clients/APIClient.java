package core.clients;

import core.settings.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIClient {

    private RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    public Response getUsers() {
        return getRequestSpec()
                .when()
                .get(ApiEndpoints.USERS.getPath())
                .then()
                .extract()
                .response();
    }
}