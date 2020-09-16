package portfolio.dao;

import com.mongodb.MongoException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import portfolio.entity.Cash;
import portfolio.entity.CashValue;
import portfolio.entity.Investment;
import portfolio.entity.InvestmentValue;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

@Component
public class NetWorthDaoImpl implements NetWorthDao {
    @Resource
    MongoTemplate mongoTemplate;

    public void init(){
        LocalDate end = LocalDate.now().plusDays(2);
        List<CashValue> cashValueList = new ArrayList<>();
        List<InvestmentValue> investmentValueList = new ArrayList<>();
        investmentValueList.add(new InvestmentValue(end.plusDays(3),36147));
        cashValueList.add(new CashValue(end.plusDays(3),20488));
        mongoTemplate.insert(investmentValueList,InvestmentValue.class);
        mongoTemplate.insert(cashValueList,CashValue.class);

    }
    @Override
    public List<Cash> getAllCash() {
        try{
            LocalDate end = LocalDate.now();
            Query query = new Query(Criteria.where("created").lte(end));
            query.with(Sort.by("created"));
            return mongoTemplate.find(query,Cash.class);
        } catch (MongoException ex){
            System.err.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Investment> getAllInvestments() {
        try{
            LocalDate end = LocalDate.now();
            Query query = new Query(Criteria.where("created").lte(end));
            query.with(Sort.by("created"));
            return mongoTemplate.find(query,Investment.class);
        } catch (MongoException ex){
            System.err.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<CashValue> getCash_preWeek() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(6);
        Query query = new Query(Criteria.where("created").gte(start).lte(end));
        query.with(Sort.by("created"));
        return mongoTemplate.find(query,CashValue.class);
    }

    @Override
    public List<CashValue> getCash_preMonth() {
        LocalDate end = LocalDate.now();
        List<LocalDate> dateList = new ArrayList<>();
        for(int i = 0;i<30;i=i+2){
            dateList.add(end.minusDays(i));
        }
        Query query = new Query(Criteria.where("created").in(dateList));
        query.with(Sort.by("created"));
        return mongoTemplate.find(query,CashValue.class);
    }

    @Override
    public List<CashValue> getCash_preQuarter() {
        LocalDate end = LocalDate.now();
        List<LocalDate> dateList = new ArrayList<>();
        for(int i = 0;i<90;i=i+5){
            dateList.add(end.minusDays(i));
        }
        Query query = new Query(Criteria.where("created").in(dateList));
        query.with(Sort.by("created"));
        return mongoTemplate.find(query,CashValue.class);
    }

    @Override
    public List<CashValue> getCash_preYear() {
        LocalDate end = LocalDate.now();
        List<LocalDate> dateList = new ArrayList<>();
        for(int i = 0;i<12;i++){
            dateList.add(end.minusMonths(i));
        }
        Query query = new Query(Criteria.where("created").in(dateList));
        query.with(Sort.by("created"));
        return mongoTemplate.find(query,CashValue.class);
    }

    @Override
    public List<InvestmentValue> getInvestment_preWeek() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(6);
        Query query = new Query(Criteria.where("created").gte(start).lte(end));
        query.with(Sort.by("created"));
        return mongoTemplate.find(query,InvestmentValue.class);
    }

    @Override
    public List<InvestmentValue> getInvestment_preMonth() {
        LocalDate end = LocalDate.now();
        List<LocalDate> dateList = new ArrayList<>();
        for(int i = 0;i<30;i=i+2){
            dateList.add(end.minusDays(i));
        }
        Query query = new Query(Criteria.where("created").in(dateList));
        query.with(Sort.by("created"));
        return mongoTemplate.find(query,InvestmentValue.class);
    }

    @Override
    public List<InvestmentValue> getInvestment_preQuarter() {
        LocalDate end = LocalDate.now();
        List<LocalDate> dateList = new ArrayList<>();
        for(int i = 0;i<90;i=i+5){
            dateList.add(end.minusDays(i));
        }
        Query query = new Query(Criteria.where("created").in(dateList));
        query.with(Sort.by("created"));
        return mongoTemplate.find(query,InvestmentValue.class);
    }

    @Override
    public List<InvestmentValue> getInvestment_preYear() {
        LocalDate end = LocalDate.now();
        List<LocalDate> dateList = new ArrayList<>();
        for(int i = 0;i<12;i++){
            dateList.add(end.minusMonths(i));
        }
        Query query = new Query(Criteria.where("created").in(dateList));
        query.with(Sort.by("created"));
        return mongoTemplate.find(query,InvestmentValue.class);
    }

}
