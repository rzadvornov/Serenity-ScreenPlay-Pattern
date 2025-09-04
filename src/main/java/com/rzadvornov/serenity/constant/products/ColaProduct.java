package org.softindustry.com.constant.products;

import java.util.Arrays;
import java.util.List;

public class ColaProduct implements Product {

    @Override
    public List<String> getProducts() {
        return Arrays.stream(Colas.values()).map(Colas::getTypeOfCola).toList();
    }

}
