package org.softindustry.com.constant.products;

import java.util.Arrays;
import java.util.List;

public class PastaProduct implements Product {

    @Override
    public List<String> getProducts() {
        return Arrays.stream(Pastas.values()).map(Pastas::getTypeOfPasta).toList();
    }
}
