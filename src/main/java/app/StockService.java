package app;

import java.util.ArrayList;
import java.util.List;

public class StockService {
    public static void addStock(Stock stock) {
        stocks.add(stock);
    }

    public static void removeStock(Stock stock) {
        stocks.remove(stock);
    }

    public static Stock getStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }

    public static void update(Stock stock) {
        Stock oldStock = getStock(stock.getSymbol());
        removeStock(oldStock);
        addStock(stock);
    }

    public static List<Stock> getStocks() {
        return stocks;
    }

    private static List<Stock> stocks = new ArrayList<Stock>();

    static {
        generateStocks();
    }

    private static void generateStocks() {
        addStock(new Stock("IBM", 43.12, "USD", "US"));
        addStock(new Stock("APPL", 320.0, "USD", "US"));
    }
}
