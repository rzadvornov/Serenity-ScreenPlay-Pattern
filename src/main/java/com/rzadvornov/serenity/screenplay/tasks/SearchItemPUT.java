package org.softindustry.com.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.softindustry.com.config.EnvConfigs;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SearchItemPUT implements Task {

    private final String keyword;

    public SearchItemPUT(String keyword) {
        this.keyword = keyword;
    }

    public static SearchItemPUT byKeyword(String keyword) {
        return instrumented(SearchItemPUT.class, keyword);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to(EnvConfigs.getConfig("searchEndPoint") + "{keyword}")
                        .with(request -> request.pathParam("keyword", keyword))
        );
    }

}
