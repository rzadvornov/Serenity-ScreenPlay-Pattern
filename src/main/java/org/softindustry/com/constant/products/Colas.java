package org.softindustry.com.constant.products;

import lombok.Getter;

@Getter
public enum Colas {

    COLA( "cola" ),
    PEPSI("pepsi");

    private final String typeOfCola;

    Colas(String typeOfCola) {
        this.typeOfCola = typeOfCola;
    }

}
