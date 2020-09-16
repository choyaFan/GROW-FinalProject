package portfolio.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "investmentValue")
public class InvestmentValue extends NetWorthValue {

    public InvestmentValue(LocalDate created, double value) {
        super(created, value);
    }
}
