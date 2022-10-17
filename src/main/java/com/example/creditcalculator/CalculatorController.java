package com.example.creditcalculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CalculatorController {


    @CrossOrigin(origins = "http://localhost:3454")
    @PostMapping(
            value = "/get-credit",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Map getString(@Validated @RequestBody CalcOdds calcOdds) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("result", String.valueOf(calcOdds.getResult()));
        map.put("message", "Twoja rata zostala obliczona");
        map.put("message_type", "info");
        return map;
    }


    @ExceptionHandler({Exception.class})
    public Map handleException() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("message", "Niepoprawne dane formularza");
        map.put("message_type", "error");
        return map;
    }

}
