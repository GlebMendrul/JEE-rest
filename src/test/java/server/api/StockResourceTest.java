package server.api;

import org.junit.Before;
import org.junit.Test;
import server.model.Stock;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static server.utils.StockTestUtils.createNotFoundStock;
import static server.utils.StockTestUtils.createStock;

/**
 * Created by gleb on 2/26/2017.
 */
public class StockResourceTest {

    private static final int HTTP_OK_STATUS = 200;
    private static final int HTTP_BAD_REQUEST_STATUS = 400;

    private StockResource stockResource = new StockResource();

    @Before
    public void setUp() {
        stockResource.deleteStock(createStock(BigDecimal.ONE));
        stockResource.deleteStock(createStock(BigDecimal.TEN));
    }

    @Test(expected = NullPointerException.class)
    public void getStockWithNull() throws Exception {
        stockResource.getStock(null);
    }

    @Test
    public void getStockNotFound() {
        Stock expected = createNotFoundStock();
        Stock actual = stockResource.getStock("not existing stock");

        assertEquals(expected, actual);
    }

    @Test
    public void getStocks() throws Exception {
        Stock expectedStock = createStock(BigDecimal.ONE);
        Response addStockResp = stockResource.addStock(expectedStock);

        List<Stock> actual = stockResource.getStocks();
        assertThat(actual, hasItems(expectedStock));
        assertEquals(HTTP_OK_STATUS, addStockResp.getStatus());
    }

    @Test
    public void addStockEntity() throws Exception {
        Stock expectedStock = createStock(BigDecimal.ONE);
        Response addStockResp = stockResource.addStock(expectedStock);

        Stock actualStock = stockResource.getStock(expectedStock.getSymbol());
        assertEquals(HTTP_OK_STATUS, addStockResp.getStatus());
        assertEquals(expectedStock, actualStock);
    }

    @Test
    public void addStock() throws Exception {
        Stock expectedStock = createStock(BigDecimal.ONE);
        Response addStockResp = stockResource.addStock(expectedStock.getSymbol(),
                expectedStock.getCurrency(),
                expectedStock.getPrice().toString(),
                expectedStock.getCountry());

        Stock actualStock = stockResource.getStock(expectedStock.getSymbol());
        assertEquals(expectedStock, actualStock);
        assertEquals(HTTP_OK_STATUS, addStockResp.getStatus());
    }

    @Test
    public void addExistingStock() {
        Stock stock = createStock(BigDecimal.ONE);
        Response successfulAddResp = stockResource.addStock(stock);
        Response failAddResp = stockResource.addStock(stock);

        assertEquals(HTTP_OK_STATUS, successfulAddResp.getStatus());
        assertEquals(HTTP_BAD_REQUEST_STATUS, failAddResp.getStatus());
    }

    @Test
    public void updateStockEntity() throws Exception {
        Stock testStock = createStock(BigDecimal.ONE);
        Stock updateTestStock = createStock(BigDecimal.TEN);

        Response addStockResp = stockResource.addStock(testStock);
        Response updateStockResp = stockResource.updateStock(updateTestStock);

        assertEquals(HTTP_OK_STATUS, addStockResp.getStatus());
        assertEquals(HTTP_OK_STATUS, updateStockResp.getStatus());
        assertEquals(updateTestStock, stockResource.getStock(testStock.getSymbol()));
    }

    @Test
    public void updateStock() throws Exception {
        Stock testStock = createStock(BigDecimal.ONE);
        Stock updateTestStock = createStock(BigDecimal.TEN);

        Response addStockResp = stockResource.addStock(testStock);
        Response updateStockResp = stockResource.updateStock(
                updateTestStock.getSymbol(),
                updateTestStock.getCurrency(),
                updateTestStock.getPrice().toString(),
                updateTestStock.getCountry()
        );

        assertEquals(HTTP_OK_STATUS, addStockResp.getStatus());
        assertEquals(HTTP_OK_STATUS, updateStockResp.getStatus());

        assertEquals(updateTestStock, stockResource.getStock(testStock.getSymbol()));
    }

    @Test
    public void deleteStock() throws Exception {
        Stock stock = createStock(BigDecimal.ONE);
        Response addStockResp = stockResource.addStock(stock);
        Response deleteStockResp = stockResource.deleteStock(stock);

        assertEquals(HTTP_OK_STATUS, addStockResp.getStatus());
        assertEquals(HTTP_OK_STATUS, deleteStockResp.getStatus());
        assertThat(stockResource.getStocks(), not(hasItems(stock)));
    }

}