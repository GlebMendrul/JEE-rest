package server.api;

import server.model.Stock;
import server.service.StockService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;
import static org.apache.commons.lang3.math.NumberUtils.isDigits;

@Path("/stock")
public class StockResource {

    private static final String NOT_FOUND_SYMBOL = "NOT FOUND";
    private static final String NOT_FOUND_COUNTRY = "--";
    private static final String NOT_FOUND_CURRENCY = "--";

    @GET
    @Path("{symbol}")
    @Produces({"application/xml", "application/json", "text/plain"})
    public Stock getStock(@PathParam("symbol") String symbol) {
        requireNonNull(symbol);
        Optional<Stock> stock = StockService.getStock(symbol);
        return stock.orElse(Stock.builder()
                .symbol(NOT_FOUND_SYMBOL)
                .price(BigDecimal.ZERO)
                .country(NOT_FOUND_COUNTRY)
                .currency(NOT_FOUND_CURRENCY).build());
    }

    @GET
    @Produces({"application/xml", "application/json", "text/plain"})
    public List<Stock> getStocks() {
        return StockService.getStocks();
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    public Response addStock(@FormParam("symbol") String symb,
                             @FormParam("currency") String currency,
                             @FormParam("price") String price,
                             @FormParam("country") String country) {
        requireNonNull(symb);
        if (StockService.getStock(symb).isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Stock " + symb + " already exists").
                    type("text/plain").
                    build();
        }

        BigDecimal priceToUse = isDigits(price) ? createBigDecimal(price) : BigDecimal.ZERO;
        StockService.addStock(Stock.builder()
                .symbol(symb)
                .price(priceToUse)
                .currency(currency)
                .country(country).build());
        return Response.ok().build();
    }

    @PUT
    @Consumes({"application/xml", "application/json", "text/plain"})
    public Response addStock(Stock stock) {
        requireNonNull(stock);
        if (StockService.getStock(stock.getSymbol()).isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Stock " + stock.getSymbol() + " already exists").
                    type("text/plain").
                    build();
        }
        StockService.addStock(stock);
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response updateStock(@FormParam("symbol") String symb,
                                @FormParam("currency") String currency,
                                @FormParam("price") String price,
                                @FormParam("country") String country) {
        requireNonNull(symb);
        StockService.update(Stock.builder()
                .symbol(symb)
                .price(createBigDecimal(price))
                .currency(currency)
                .country(country).build());
        return Response.ok().build();
    }

    @POST
    @Consumes({"application/xml", "application/json", "text/plain"})
    public Response updateStock(Stock stock) {
        requireNonNull(stock);
        StockService.update(stock);
        return Response.ok().build();
    }

    @DELETE
    @Consumes({"application/xml", "application/json", "text/plain"})
    public Response deleteStock(Stock stock) {
        StockService.removeStock(stock);
        return Response.ok().build();
    }

}