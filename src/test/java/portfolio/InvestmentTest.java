package portfolio;

import org.junit.jupiter.api.Test;
import portfolio.entity.Investment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class InvestmentTest {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @Test
    public void test_getValue_valid() throws ParseException {
        Investment i = new Investment("i10001","Diageo plc","DEO", "Bond", sdf.parse("5/4/2015"),114.83,130);
        assertEquals(130*120,i.getValue());
    }
    @Test
    public void test_getValue_invalid() throws ParseException {
        Investment i = new Investment("i10001","Diageo","DEO", "Bond", sdf.parse("5/4/2015"),114.83,130);
        assertNull(i.getValue());
    }
    @Test
    public void test_getIncome_valid() throws ParseException {
        Investment i = new Investment("i10001","Diageo plc","DEO", "Bond", sdf.parse("5/4/2015"),114.83,130);
        assertEquals(130*(120-114.83),i.getIncome());
    }
    @Test
    public void test_getIncome_invalid() throws ParseException {
        Investment i = new Investment("i10001","Diageo","DEO", "Bond", sdf.parse("5/4/2015"),114.83,130);
        assertNull(i.getIncome());
    }
    @Test void test_getSpending() throws ParseException {
        Investment i = new Investment("i10001","Diageo plc","DEO", "Bond", sdf.parse("5/4/2015"),114.83,130);
        assertEquals(114.83*130,i.getSpending());
    }

}
