package com.ptt.entity;

public class Person {
    int ID;
    private Name name;
    private Address address;

    public Person(){
    }

    public Person(Name name, int ID, Address address) {
        this.ID = ID;
        this.name = name;
        this.address = address;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

}
