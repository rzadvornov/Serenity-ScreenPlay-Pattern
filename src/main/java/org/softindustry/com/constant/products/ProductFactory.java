package org.softindustry.com.constant.products;

import java.util.List;

public class ProductFactory {

    public static List<String> getProducts(ProductType product) {
        return product.getProducts();
    }

}
