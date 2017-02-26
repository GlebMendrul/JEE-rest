package server.service;

import lombok.Getter;
import server.model.Stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class StockService {

    @Getter
    private static List<Stock> stocks = new ArrayList<Stock>();

    static {
        generateStocks();
    }

    private StockService() {
    }

    public static void addStock(Stock stock) {
        stocks.add(stock);
    }

    public static void removeStock(Stock stock) {
        stocks.remove(stock);
    }

    public static Optional<Stock> getStock(String symbol) {
        return stocks.stream().filter(stock -> stock.getSymbol().equals(symbol)).findFirst();
    }

    public static void update(Stock stock) {
        getStock(stock.getSymbol()).ifPresent(oldStock -> {
            removeStock(oldStock);
            addStock(stock);
        });
    }

    private static void generateStocks() {
        addStock(Stock.builder()
                .symbol("IBM")
                .price(BigDecimal.valueOf(43.12))
                .currency("USD")
                .country("US").build());
        addStock(Stock.builder()
                .symbol("APPL")
                .price(BigDecimal.valueOf(320.0))
                .currency("USD")
                .country("US").build());
    }
}
