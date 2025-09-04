package com.rzadvornov.serenity.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

@Subject("the Response body")
public class ResponseBody implements Question<io.restassured.response.ResponseBody>  {

    @Override
    public io.restassured.response.ResponseBody answeredBy(Actor actor) {
        return new LastResponse().answeredBy(actor).body();
    }


}
