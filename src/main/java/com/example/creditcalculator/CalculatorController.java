package com.example.creditcalculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalculatorController {


    @GetMapping("/credit")
    public String greetingForm(Model model) {
        model.addAttribute("calcOdds", new CalcOdds());
        return "credit-calculator";
    }


    @PostMapping("/credit")
    public String calcSubmit(@ModelAttribute CalcOdds calcOdds, Model model) {
        calcOdds.setResult(calcEquation(calcOdds.getTotalValueOfLoan(), calcOdds.getHowLongInYears(), calcOdds.getCreditInterest()));
        model.addAttribute("calcOdds", calcOdds);
        return "result";
    }


    private Float calcEquation(float valueOfLoan, float years, float creditInterest) {

        creditInterest = valueOfLoan * (creditInterest / 100);
        return valueOfLoan / (years * 12) + creditInterest;

    }


}
