package server.utils;

import server.model.Stock;

import java.math.BigDecimal;

/**
 * Created by gleb on 2/26/2017.
 */
public final class StockTestUtils {

    private static final String NOT_FOUND_SYMBOL = "NOT FOUND";
    private static final String NOT_FOUND_COUNTRY = "--";
    private static final String NOT_FOUND_CURRENCY = "--";

    private StockTestUtils() {
    }

    public static Stock createStock(BigDecimal price) {
        return Stock.builder()
                .symbol("GOOGL")
                .currency("USD")
                .country("US")
                .price(price).build();
    }

    public static Stock createNotFoundStock() {
        return Stock.builder()
                .symbol(NOT_FOUND_SYMBOL)
                .price(BigDecimal.ZERO)
                .country(NOT_FOUND_COUNTRY)
                .currency(NOT_FOUND_CURRENCY).build();
    }
}
