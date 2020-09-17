package portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.service.MarketService;
import portfolio.service.MarketServiceImpl;

@RestController
@CrossOrigin
public class MarketController {
    private final MarketService service;
    @Autowired
    public MarketController(MarketServiceImpl service){
        this.service = service;
    }

    @GetMapping("/getIndex")
    public String getIndex(){
        return service.getIndices();
    }

    @GetMapping("/getGainer")
    public String getGainer(){
        return service.getGainers();
    }

    @GetMapping("/getLoser")
    public String getLoser(){
        return service.getLosers();
    }

    @GetMapping("/getHolding")
    public double getHolding(){
        return service.getHoldings();
    }
}
