package org.softindustry.com.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;
import org.softindustry.com.config.EnvConfigs;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SearchItemPATCH implements Task {

    private final String keyword;

    public SearchItemPATCH(String keyword) {
        this.keyword = keyword;
    }

    public static SearchItemPATCH byKeyword(String keyword) {
        return instrumented(SearchItemPATCH.class, keyword);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Patch.to(EnvConfigs.getConfig("searchEndPoint") + "{keyword}")
                        .with(request -> request.pathParam("keyword", keyword))
        );
    }

}
