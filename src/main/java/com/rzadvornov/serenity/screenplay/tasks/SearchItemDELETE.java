package org.softindustry.com.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import org.softindustry.com.config.EnvConfigs;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SearchItemDELETE implements Task {

    private final String keyword;

    public SearchItemDELETE(String keyword) {
        this.keyword = keyword;
    }

    public static SearchItemDELETE byKeyword(String keyword) {
        return instrumented(SearchItemDELETE.class, keyword);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from(EnvConfigs.getConfig("searchEndPoint") + "{keyword}")
                        .with(request -> request.pathParam("keyword", keyword))
        );
    }

}
