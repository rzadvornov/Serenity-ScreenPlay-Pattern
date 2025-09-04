package com.rzadvornov.serenity.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import com.rzadvornov.serenity.config.EnvConfigs;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SearchItemGET implements Task {

    private final String keyword;

    public SearchItemGET(String keyword) {
        this.keyword = keyword;
    }

    public static SearchItemGET byKeyword(String keyword) {
        return instrumented(SearchItemGET.class, keyword);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(EnvConfigs.getConfig("searchEndPoint") + "{keyword}")
                        .with(request -> request.pathParam("keyword", keyword))
        );
    }

}
