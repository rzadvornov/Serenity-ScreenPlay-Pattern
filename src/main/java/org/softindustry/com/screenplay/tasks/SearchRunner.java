package org.softindustry.com.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;

public class SearchRunner {

    public static void runSearch(String method, String keyword, Actor actor) {
        Search search = switch (method.toLowerCase()) {
            case "get" -> new Search(new SearchItemGET(keyword));
            case "post" -> new Search(new SearchItemPOST(keyword));
            case "put" -> new Search(new SearchItemPUT(keyword));
            case "patch" -> new Search(new SearchItemPATCH(keyword));
            case "delete" -> new Search(new SearchItemDELETE(keyword));
            default -> null;
        };
        assert search != null;
        search.execute(actor);
    }

}
