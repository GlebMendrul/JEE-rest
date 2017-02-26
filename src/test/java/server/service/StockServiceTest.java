package server.service;

import org.junit.Before;
import org.junit.Test;
import server.model.Stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static server.utils.StockTestUtils.createStock;

/**
 * Created by gleb on 2/26/2017.
 */
public class StockServiceTest {

    @Before
    public void setUp() {
        StockService.removeStock(createStock(BigDecimal.ONE));
        StockService.removeStock(createStock(BigDecimal.TEN));
    }

    @Test
    public void addStock() throws Exception {
        Stock stock = createStock(BigDecimal.ONE);
        StockService.addStock(stock);
        assertThat(StockService.getStocks(), hasItems(stock));
    }

    @Test
    public void removeStock() throws Exception {
        Stock stock = createStock(BigDecimal.ONE);
        StockService.addStock(stock);
        StockService.removeStock(stock);
        assertThat(StockService.getStocks(), not(hasItems(stock)));
    }

    @Test
    public void getStock() throws Exception {
        Optional<Stock> stock = StockService.getStock("not existing");
        assertFalse(stock.isPresent());

        StockService.addStock(createStock(BigDecimal.ONE));
        Optional<Stock> applStock = StockService.getStock("GOOGL");

        assertTrue(applStock.isPresent());
    }

    @Test
    public void update() throws Exception {
        Stock testStock = createStock(BigDecimal.ONE);
        Stock updateTestStock = createStock(BigDecimal.TEN);

        StockService.addStock(testStock);
        StockService.update(updateTestStock);

        assertEquals(updateTestStock, StockService.getStock(testStock.getSymbol()).orElseThrow(Exception::new));
    }

    @Test
    public void getStocks() throws Exception {
        List<Stock> stocks = StockService.getStocks();
        assertTrue(isNotEmpty(stocks));
    }

}