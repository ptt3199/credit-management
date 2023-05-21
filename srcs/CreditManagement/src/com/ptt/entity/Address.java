package com.ptt.entity;

public class Address {
    private int number;
    private String street;
    private String district;
    private String city;


    public Address(int number, String street, String district, String city) {
        this.number = number;
        this.street = street;
        this.district = district;
        this.city = city;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public void setStreet(String street) {
        this.street = street;
    }


    public void setDistrict(String district) {
        this.district = district;
    }


    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressText() {
        return number + " " + street + ", "+ district + ", " + city;
    }

    public String getForFile(){ return number + "," + street + ","+ district + "," + city;}
}
