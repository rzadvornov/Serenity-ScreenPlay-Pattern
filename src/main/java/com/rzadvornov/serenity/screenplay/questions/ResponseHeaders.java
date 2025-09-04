package org.softindustry.com.screenplay.questions;

import io.restassured.http.Headers;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.rest.questions.LastResponse;


@Subject("the Response headers")
public class ResponseHeaders implements Question<Headers> {

    @Override
    public Headers answeredBy(Actor actor) {
        return new LastResponse().answeredBy(actor).headers();
    }


}
