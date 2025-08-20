package core.clients;

import core.settings.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class APIClient {
    private final String baseUrl;

    public APIClient() {
        this.baseUrl = determineBaseUrl();
    }

    private String determineBaseUrl() {

        String environment = System.getProperty("env", "test");

        String configFileName = "application-" + environment + ".properties";
        Properties properties = new Properties();

        try (InputStream input =
                     getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new IllegalStateException("Configuration file not found: "
                        + configFileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to load configuration file: " + configFileName, e
            );
        }

        return properties.getProperty("baseUrl");
    }

    private RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .baseUri(baseUrl)
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

    public Response createPostWithName(String name) {
        return getRequestSpec()
                .body(Map.of("name", name))
                .when()
                .post(ApiEndpoints.POSTS.getPath())
                .then()
                .extract()
                .response();
    }

    public Response getPostsId(int id) {
        return getRequestSpec()
                .when()
                .get(ApiEndpoints.POSTS.getPath() + "/" + id)
                .then()
                .extract()
                .response();
    }

    public Response getPostsPage(int page, int limit) {
        return getRequestSpec()
                .queryParam("_page", page)
                .queryParam("_limit", limit)
                .when()
                .get(ApiEndpoints.POSTS.getPath())
                .then()
                .extract().response();
    }
}