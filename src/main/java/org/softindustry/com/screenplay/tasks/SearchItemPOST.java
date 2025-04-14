package org.softindustry.com.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.softindustry.com.config.EnvConfigs;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SearchItemPOST implements Task {

    private final String keyword;

    public SearchItemPOST(String keyword) {
        this.keyword = keyword;
    }

    public static SearchItemPOST byKeyword(String keyword) {
        return instrumented(SearchItemPOST.class, keyword);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(EnvConfigs.getConfig("searchEndPoint") + "{keyword}")
                        .with(request -> request.pathParam("keyword", keyword))
        );
    }

}
