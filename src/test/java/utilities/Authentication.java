package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Authentication {

    public static String generateToken(String password,String userName) {
        String url = "http://164.92.252.42:8080/auth/login";
        Map<String, String> tokenBody = new HashMap<>();
        tokenBody.put("password", password);
        tokenBody.put("username", userName);
        Response response = given().contentType(ContentType.JSON).body(tokenBody).when().post(url);

        return response.jsonPath().getString("token");
    }
}
