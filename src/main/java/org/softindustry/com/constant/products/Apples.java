package org.softindustry.com.constant.products;

import lombok.Getter;

@Getter
public enum Apples {

    APPLE( "apple" );

    private final String typeOfApple;

    Apples(String typeOfApple) {
        this.typeOfApple = typeOfApple;
    }


}
