package base_url;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseUrl {

    public static RequestSpecification spec;


    public static void setUp() {
        spec = new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                setBaseUri("http://164.92.252.42:8080").
                build();
    }
}