package org.softindustry.com.constant.products;

import java.util.ArrayList;
import java.util.List;

public class ProductFactory {

    public static List<String> getProducts(String product) {
        return switch (product.toLowerCase()) {
            case "apple" -> new AppleProduct().getProducts();
            case "orange" -> new OrangeProduct().getProducts();
            case "cola" -> new ColaProduct().getProducts();
            case "pasta" -> new PastaProduct().getProducts();
            default -> new ArrayList<>();
        };
    }



}
