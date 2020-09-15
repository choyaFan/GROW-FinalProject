package portfolio;

import com.mongodb.MongoException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class NetWorthDaoImpl implements NetWorthDao{
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public List<Cash> getAllCash() {
        try{
            return mongoTemplate.findAll(Cash.class);
        } catch (MongoException ex){
            System.err.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Investment> getAllInvestments() {
        try{
            return mongoTemplate.findAll(Investment.class);
        } catch (MongoException ex){
            System.err.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Map<Date, Double> getNetWorthByTime2(Date start, Date end) {
        Criteria criteria = Criteria.where("created").gte(start).lte(end);
        Aggregation aggregation = newAggregation(
                match(criteria),
                group("created").sum("value").as("value"),
                project("created","value"),
                sort(Sort.Direction.DESC,"created")
        );
        AggregationResults<Cash> ar = mongoTemplate.aggregate(aggregation, "Cash",
                Cash.class);
        List<Cash> dateValueList = ar.getMappedResults();
        System.out.println("=====Result Testing=====");
        dateValueList.forEach(System.out::println);
        System.out.println("=====END======");
        return null;
    }
}
