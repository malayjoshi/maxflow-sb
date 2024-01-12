package edu.syr.cs.maxflow.Controllers;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import edu.syr.cs.maxflow.Models.CalculateReq;
import edu.syr.cs.maxflow.Models.MaxCalculateResponse;
import edu.syr.cs.maxflow.Services.MaxFlowService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/maxflow")
@CrossOrigin
public class MaxFlowController {
    @Autowired
    private MaxFlowService maxFlowService;

    @PostMapping("/calculate")
    public ResponseEntity<MaxCalculateResponse> calculate(@RequestBody CalculateReq req) {
        
        
        return new ResponseEntity<MaxCalculateResponse>(maxFlowService.calculate(req), HttpStatus.OK);
    }
    
}
