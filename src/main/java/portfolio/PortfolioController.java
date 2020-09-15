package portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/getCashByTime")
    public ResponseEntity<List<DateValue>> getCashByTime(@RequestParam("start") String start, @RequestParam("end") String end){
        try{
            return new ResponseEntity<>(service.getCashByTime(sdf.parse(start),sdf.parse(end)), HttpStatus.OK);
        } catch (ParseException ex){
            HttpHeaders headers = new HttpHeaders();
            headers.add("msg","Date should be in format:MM/dd/yyyy.");
            return new ResponseEntity<>(null, headers,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getAllInvestments")
    public List<Investment> getAllInvestments(){
        return service.getAllInvestments();
    }
    @GetMapping("/getInvestmentTotalValue")
    public double getInvestmentTotalValue(){
        return service.getInvestmentTotalValue();
    }
    @GetMapping("/getInvestmentsByTime")
    public ResponseEntity<List<DateValue>> getInvestmentsByTime(@RequestParam("start") String start, @RequestParam("end") String end){
        try{
            return new ResponseEntity<>(service.getInvestmentsByTime(sdf.parse(start),sdf.parse(end)), HttpStatus.OK);
        } catch (ParseException ex){
            HttpHeaders headers = new HttpHeaders();
            headers.add("msg","Date should be in format:MM/dd/yyyy.");
            return new ResponseEntity<>(null, headers,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getNetWorthByTime")
    public ResponseEntity<List<DateValue>> getNetWorthByTime(@RequestParam("start") String start, @RequestParam("end") String end){
        try{
            return new ResponseEntity<>(service.getNetWorthByTime(sdf.parse(start),sdf.parse(end)), HttpStatus.OK);
        } catch (ParseException ex){
            HttpHeaders headers = new HttpHeaders();
            headers.add("msg","Date should be in format:MM/dd/yyyy.");
            return new ResponseEntity<>(null, headers,HttpStatus.BAD_REQUEST);
        }
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
