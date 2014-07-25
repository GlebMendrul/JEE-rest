package app;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/stock")
public class StockResource {

    @GET
    @Path("{symbol}")
    @Produces({"application/xml", "application/json", "text/plain"})
    public Stock getStock(@PathParam("symbol") String symbol) {

        Stock stock = StockService.getStock(symbol);

        if (stock == null) {
            return new Stock("NOT FOUND", 0.0, "--", "--");
        }

        return stock;
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

        if (StockService.getStock(symb) != null)
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Stock " + symb +
                            " already exists").type("text/plain").build();

        double priceToUse;
        try {
            priceToUse = new Double(price);
        } catch (NumberFormatException e) {
            priceToUse = 0.0;
        }

        StockService.addStock(new Stock(symb, priceToUse,
                currency, country));

        return Response.ok().build();
    }

    @PUT
    @Consumes({"application/xml", "application/json", "text/plain"})
    public Response addStock(Stock stock) {

        if (StockService.getStock(stock.getSymbol()) != null)
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Stock " + stock.getSymbol() +
                            " already exists").type("text/plain").build();

        StockService.addStock(stock);

        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response updateStock(@FormParam("symbol") String symb,
                                @FormParam("currency") String currency,
                                @FormParam("price") String price,
                                @FormParam("country") String country) {
        Stock stock = new Stock(symb, Double.parseDouble(price), currency, country);
        StockService.update(stock);
        return Response.ok().build();
    }

    @POST
    @Consumes({"application/xml", "application/json", "text/plain"})
    public Response updateStock(Stock stock) {
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