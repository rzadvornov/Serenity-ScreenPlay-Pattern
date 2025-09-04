package org.softindustry.com.constant.products;

public enum Oranges {

    ORANGE( "orange" ),
    ORANGE_DUTCH("sinas"),
    ORANGE_DUTCH2("sinaas");


    private final String typeOfOrange;

    Oranges(String typeOfOrange) {
        this.typeOfOrange = typeOfOrange;
    }

    public String getTypeOfOrange() {
        return typeOfOrange;
    }
}
