package org.softindustry.com.constant.products;

import java.util.Arrays;
import java.util.List;

public class OrangeProduct implements Product {

    @Override
    public List<String> getProducts() {
        return Arrays.stream(Oranges.values()).map(Oranges::getTypeOfOrange).toList();
    }
}
