package org.softindustry.com.constant.products;

import java.util.List;

public enum ProductType {

    APPLE(new AppleProduct()),
    ORANGE(new OrangeProduct()),
    COLA(new ColaProduct()),
    PASTA(new PastaProduct());

    private final Product productStrategy;

    ProductType(Product productStrategy) {
        this.productStrategy = productStrategy;
    }

    public List<String> getProducts() {
        return productStrategy.getProducts();
    }
}
