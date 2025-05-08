package org.softindustry.com.steps;


import com.google.common.net.MediaType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;
import org.softindustry.com.config.EnvConfigs;
import org.softindustry.com.constant.products.*;
import org.softindustry.com.screenplay.questions.SearchResults;
import org.softindustry.com.screenplay.tasks.SearchRunner;
import org.softindustry.com.utilities.StringUtilities;

import java.net.HttpURLConnection;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;

public class SearchStepDefinitions {
    public EnvironmentVariables environmentVariables;
    Actor actor;

    @Given("{string} wants to buy an item")
    public void actorWantsToBuyAnItem(String actorName) {
        setTheRestApiBaseUrl(actorName);
    }

    @When("he/she performs search for an {string}")
    public void actorPerformsSearchByKeyword(String keyword) {
        SearchRunner.runSearch("get", keyword, actor);
    }

    @When("he/she performs search for an {string} using http {string}")
    public void actorPerformsSearchByKeywordWithHttpMethod(String keyword, String method) {
        SearchRunner.runSearch(method, keyword, actor);
    }

    @Then("{string} and {string} are shown for an {string}")
    public void errorMessageAndStatusCodeAreShownForAnItem(String code, String errorMessage, String item) {
        actor.attemptsTo(
                Ensure.that("Response status code should be " + code,
                        SearchResults.statusCode()).isEqualTo(Integer.parseInt(code))
        );
        errorMessageIsShownForAnItem(errorMessage, item);
    }


    @Then("he/she sees the results are displayed for {string}")
    public void actorGetsResults(String keyword) {
        actor.attemptsTo(
                Ensure.that("Response status code should be " + HttpURLConnection.HTTP_OK,
                        SearchResults.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK)
        );
        checkHeaders();
        checkJsonSchema("schemas/search.json");
        checkResponseContent(keyword);
    }

    @Then("he/she doesn't see the results")
    public void actorGetsEmptyResults() {
        checkHeaders();
        checkJsonSchema("schemas/search_no_results.json");
    }

    @And("{string} is shown for an {string}")
    public void errorMessageIsShownForAnItem(String errorMessage, String item) {
        actor.attemptsTo(
                Ensure.that(
                        Boolean.valueOf(SearchResults.body().answeredBy(actor)
                                .jsonPath().get("detail.error").toString())).isTrue(),
                Ensure.that(
                        SearchResults.body().answeredBy(actor)
                                .jsonPath().get("detail.message").toString()).isEqualToIgnoringCase(errorMessage),
                Ensure.that(
                        SearchResults.body().answeredBy(actor)
                                .jsonPath().get("detail.requested_item").toString()).isEqualToIgnoringCase(item),
                Ensure.that(
                        SearchResults.body().answeredBy(actor)
                                .jsonPath().get("detail.served_by").toString())
                                .isEqualToIgnoringCase(EnvConfigs.getConfig("servedByEndPoint").toString())
        );
    }

    private void setTheRestApiBaseUrl(String actorName) {
        String theRestApiBaseUrl = environmentVariables.optionalProperty("restapi.baseurl")
                .orElseThrow(IllegalArgumentException::new);
        actor = Actor.named(actorName + " the api user").whoCan(CallAnApi.at(theRestApiBaseUrl));
        EnvConfigs.setConfigs();
    }

    private void checkHeaders() {
        String date = StringUtilities.getFormattedDate();
        actor.attemptsTo(
                Ensure.that(
                        SearchResults.headers().answeredBy(actor)
                                .getValue("Connection")).isEqualTo("keep-alive"),
                Ensure.that(
                        SearchResults.headers().answeredBy(actor)
                                .getValue("Via")).isEqualTo("1.1 vegur"),
                Ensure.that(
                        SearchResults.headers().answeredBy(actor)
                                .getValue("Server")).isEqualTo("uvicorn"),
                Ensure.that(
                                SearchResults.headers().answeredBy(actor)
                                        .getValue("Content-Type"))
                        .isEqualTo(MediaType.JSON_UTF_8.type() + "/" + MediaType.JSON_UTF_8.subtype()),
                Ensure.that(
                        SearchResults.headers().answeredBy(actor)
                                .getValue("Date")).isLessThanOrEqualTo(date),
                Ensure.that(
                        Long.valueOf(SearchResults.headers().answeredBy(actor)
                                .getValue("Content-Length"))).isGreaterThan(1L)
        );
    }

    private void checkJsonSchema(String pathToSchemaAtClassPath) {
        actor.should(seeThatResponse(response -> response
                .body("", not(empty())).body(matchesJsonSchemaInClasspath(pathToSchemaAtClassPath))));
    }

    private void checkResponseContent(String item) {
        List<LinkedHashMap> root = SearchResults.body().answeredBy(actor).jsonPath().getList("$");
        List<String> products = ProductFactory.getProducts(ProductType.valueOf(item.toUpperCase()));
        actor.attemptsTo(
                Ensure.that(getFoundResults(products, root)).isEqualTo((long) root.size())
        );
    }

    private static long getFoundResults(List<String> items, List<LinkedHashMap> root) {
        Set<LinkedHashMap> results = new HashSet<>();
        items.forEach(item -> results.addAll(root.stream().filter(e ->
                e.get("image").toString().toLowerCase().contains(item) ||
                e.get("promoDetails").toString().toLowerCase().contains(item) ||
                e.get("brand").toString().toLowerCase().contains(item) ||
                e.get("title").toString().toLowerCase().contains(item) ||
                e.get("url").toString().toLowerCase().contains(item)).collect(Collectors.toSet())));
        return results.size();
    }

}
