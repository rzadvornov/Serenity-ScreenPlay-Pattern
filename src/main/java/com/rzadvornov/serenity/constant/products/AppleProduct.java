package com.rzadvornov.serenity.constant.products;

import java.util.Arrays;
import java.util.List;

public class AppleProduct implements Product {

    @Override
    public List<String> getProducts() {
        return Arrays.stream(Apples.values()).map(Apples::getTypeOfApple).toList();
    }
}
