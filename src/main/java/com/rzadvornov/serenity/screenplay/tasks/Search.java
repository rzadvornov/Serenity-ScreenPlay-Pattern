package org.softindustry.com.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class Search {

    private final Task task;

    public Search(Task task){
        this.task = task;
    }

    public void execute(Actor actor) {
        this.task.performAs(actor);
    }

}
