package portfolio.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.service.MarketService;
import portfolio.service.MarketServiceImpl;

@RestController
@CrossOrigin
@Api(value = "Portfolio Manager")
public class MarketController {
    private final MarketService service;
    @Autowired
    public MarketController(MarketService service){
        this.service = service;
    }

    @GetMapping("/getIndex")
    @ApiOperation("Get Indices.")
    public String getIndex(){
        return service.getIndices();
    }

    @GetMapping("/getGainer")
    @ApiOperation("Get Top 5 Gainers.")
    public String getGainer(){
        return service.getGainers();
    }

    @GetMapping("/getLoser")
    @ApiOperation("Get Top 5 Losers.")
    public String getLoser(){
        return service.getLosers();
    }

    @GetMapping("/getHolding")
    @ApiOperation("Get Investment Holding.")
    public double getHolding(){
        return service.getHoldings();
    }
}
