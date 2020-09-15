package portfolio;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("cashValue")
public class CashValue extends NetWorthValue {

    public CashValue(LocalDate created, double value) {
        super(created, value);
    }
}
