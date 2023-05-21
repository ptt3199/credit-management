package com.ptt.entity;

public class Loan {
    private double annualInterestRate;
    private int numberOfYears;
    private double loanAmount;
    private final String loanDate;
    private final int clientID;

    public Loan(double annualInterestRate, int numberOfYears, double loanAmount, String loanDate, int clientID) {
        this.annualInterestRate = annualInterestRate;
        this.numberOfYears = numberOfYears;
        this.loanAmount = loanAmount;
        this.loanDate = loanDate;
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double monthlyPayment() {
        double monthlyInterestRate = annualInterestRate / 1200;
        return loanAmount * monthlyInterestRate / (1 -
                (Math.pow(1 / (1 + monthlyInterestRate), numberOfYears * 12)));
    }

    public double totalPayment() {
        return monthlyPayment() * numberOfYears * 12;
    }

}
