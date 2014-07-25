package app;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "stocks")
public class Stock implements Serializable {
    private String symbol;
    private Double price;
    private String currency;
    private String country;

    public Stock() {
    }

    public Stock(String symbol, Double price, String currency, String country) {
        this.symbol = symbol;
        this.price = price;
        this.currency = currency;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (country != null ? !country.equals(stock.country) : stock.country != null) return false;
        if (currency != null ? !currency.equals(stock.currency) : stock.currency != null) return false;
        if (price != null ? !price.equals(stock.price) : stock.price != null) return false;
        if (symbol != null ? !symbol.equals(stock.symbol) : stock.symbol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}