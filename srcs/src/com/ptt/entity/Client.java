package com.ptt.entity;

public class Client extends Person {
    private int loanID;

    public Client(Name name, int ID, Address address) {
        super(name, ID, address);
    }

    public Client(Name name, int ID, Address address, int loanID) {
        super(name, ID, address);
        this.loanID = loanID;
    }

    public Client() {
    }

    public int getLoanID(){ return loanID; }

    public void setLoanID(int loanID){
        this.loanID = loanID;
    }

}
