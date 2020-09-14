package portfolio;

import com.mongodb.MongoException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

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
}
