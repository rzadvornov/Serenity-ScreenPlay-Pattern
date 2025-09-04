package org.softindustry.com.constant.products;

public enum Colas {

    COLA( "cola" ),
    PEPSI("pepsi");

    private final String typeOfCola;

    Colas(String typeOfCola) {
        this.typeOfCola = typeOfCola;
    }

    public String getTypeOfCola() {
        return typeOfCola;
    }
}
