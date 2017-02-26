package server.model;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "stocks")
@Getter
@ToString
@EqualsAndHashCode
@Builder
@Value
public class Stock {
    private String symbol;
    private BigDecimal price;
    private String currency;
    private String country;
}