package portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.entity.*;
import portfolio.service.NetWorthService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin
public class PortfolioController {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    @Autowired
    NetWorthService service;

    @GetMapping("/getAllCash")
    public List<Cash> getAllCash(){
        return service.getAllCash();
    }
    @GetMapping("/getCashTotalValue")
    public double getCashTotalValue(){
        return service.getCashTotalValue();
    }
    @GetMapping("/getCash_preWeek")
    public List<CashValue> getCash_preWeek(){
        return service.getCash_preWeek();
    }
    @GetMapping("/getCash_preMonth")
    public List<CashValue> getCash_preMonth(){
        return service.getCash_preMonth();
    }
    @GetMapping("/getCash_preQuarter")
    public List<CashValue> getCash_preQuarter(){
        return service.getCash_preQuarter();
    }
    @GetMapping("/getCash_preYear")
    public List<CashValue> getCash_preYear(){
        return service.getCash_preYear();
    }
    @GetMapping("/getAllInvestments")
    public List<Investment> getAllInvestments(){
        return service.getAllInvestments();
    }
    @GetMapping("/getInvestmentTotalValue")
    public double getInvestmentTotalValue(){
        return service.getInvestmentTotalValue();
    }
    @GetMapping("/getInvestment_preWeek")
    public List<InvestmentValue> getInvestment_preWeek(){
        return service.getInvestment_preWeek();
    }
    @GetMapping("/getInvestment_preMonth")
    public List<InvestmentValue> getInvestment_preMonth(){
        return service.getInvestment_preMonth();
    }
    @GetMapping("/getInvestment_preQuarter")
    public List<InvestmentValue> getInvestment_preQuarter(){
        return service.getInvestment_preQuarter();
    }
    @GetMapping("/getInvestment_preYear")
    public List<InvestmentValue> getInvestment_preYear(){
        return service.getInvestment_preYear();
    }
    @GetMapping("/getNetWorth_preWeek")
    public List<NetWorthValue> getNetWorth_preWeek(){
        return service.getNetWorth_preWeek();
    }
    @GetMapping("/getNetWorth_preMonth")
    public List<NetWorthValue> getNetWorth_preMonth(){
        return service.getNetWorth_preMonth();
    }
    @GetMapping("/getNetWorth_preQuarter")
    public List<NetWorthValue> getNetWorth_preQuarter(){
        return service.getNetWorth_preQuarter();
    }
    @GetMapping("/getNetWorth_preYear")
    public List<NetWorthValue> getNetWorth_preYear(){
        return service.getNetWorth_preYear();
    }
    @GetMapping("/getIncome")
    public ResponseEntity<CashFlow> getIncome(@RequestParam("start") String start, @RequestParam("end") String end){
        try{
            return new ResponseEntity<>(service.getIncome(sdf.parse(start),sdf.parse(end)), HttpStatus.OK);
        } catch (ParseException ex){
            HttpHeaders headers = new HttpHeaders();
            headers.add("msg","Date should be in format:MM/dd/yyyy.");
            return new ResponseEntity<>(null, headers,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getSpending")
    public ResponseEntity<CashFlow> getSpending(@RequestParam("start") String start, @RequestParam("end") String end){
        try{
            return new ResponseEntity<>(service.getSpending(sdf.parse(start),sdf.parse(end)), HttpStatus.OK);
        } catch (ParseException ex){
            HttpHeaders headers = new HttpHeaders();
            headers.add("msg","Date should be in format:MM/dd/yyyy.");
            return new ResponseEntity<>(null, headers,HttpStatus.BAD_REQUEST);
        }
    }
}
