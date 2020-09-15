package portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MarketController {
    private final MarketService service;
    @Autowired
    public MarketController(MarketServiceImpl service){
        this.service = service;
    }

    @GetMapping("/getIndexPercent")
    public String getIndexPercent(){
        return service.getIndicesPercent();
    }

    @GetMapping("/getGainerPercent")
    public String getGainerPercent(){
        return service.getGainersPercent();
    }

    @GetMapping("/getLoserPercent")
    public String getLoserPercent(){
        return service.getLosersPercent();
    }

    @GetMapping("/getHolding")
    public double getHolding(){
        return service.getHoldings();
    }
}
