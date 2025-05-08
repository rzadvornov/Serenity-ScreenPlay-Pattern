package org.softindustry.com.constant.products;

public enum Apples {

    APPLE( "apple" );

    private final String typeOfApple;

    Apples(String typeOfApple) {
        this.typeOfApple = typeOfApple;
    }

    public String getTypeOfApple() {
        return typeOfApple;
    }

}
