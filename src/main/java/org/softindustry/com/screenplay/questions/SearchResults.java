package org.softindustry.com.screenplay.questions;

import io.restassured.http.Headers;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;


public class SearchResults {

    public static Question<Integer> statusCode() {
        return new TheResponseStatusCode();
    }

    public static Question<Headers> headers() {
        return new ResponseHeaders();
    }

    public static Question<io.restassured.response.ResponseBody> body() {
        return new ResponseBody();
    }
}
