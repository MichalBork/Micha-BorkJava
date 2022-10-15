package com.example.creditcalculator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CalcOdds {

    @Min(value = 1)
    private float totalValueOfLoan;
    @Min(value = 1)
    private float howLongInYears;
    @Min(value = 1)
    private float creditInterest;


    public Float getCreditInterest() {
        return creditInterest;
    }

    public void setCreditInterest(float creditInterest) {
        this.creditInterest = creditInterest;
    }

    public float getTotalValueOfLoan() {
        return totalValueOfLoan;
    }


    public void setTotalValueOfLoan(float totalValueOfLoan) {
        this.totalValueOfLoan = totalValueOfLoan;
    }

    public float getHowLongInYears() {
        return howLongInYears;
    }

    public void setHowLongInYears(float howLongInYears) {
        this.howLongInYears = howLongInYears;
    }

    public float getResult() {
        float localCreditInterest = getTotalValueOfLoan() * (getCreditInterest() / 100);
        return getTotalValueOfLoan() / (getHowLongInYears() * 12) + localCreditInterest;

    }
}


