package org.softindustry.com.constant.products;

import lombok.Getter;

@Getter
public enum Pastas {

    PASTA( "pasta"),
    BERTOLLI("bertolli"),
    SPAGHETTI("spaghetti"),
    PENNE( "penne"),
    TORTELLINI("tortellini"),
    LASAGNE("lasagne"),
    RAVIOLI ("ravioli"),
    LINGUINE("linguine"),
    RIGATONI("rigatoni"),
    RIGATE("rigate"),
    FARFALLE("farfalle"),
    FUSILLI("fusilli"),
    MACARONI("macaroni"),
    TAGLIATELLE("tagliatelle"),
    GNOCCHI("gnocchi"),
    CANNELLONI("cannelloni");

    private final String typeOfPasta;

    Pastas(String typeOfPasta) {
        this.typeOfPasta = typeOfPasta;
    }


}
