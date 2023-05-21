package com.ptt.controller;

import com.ptt.entity.Address;
import com.ptt.entity.Client;
import com.ptt.entity.Loan;
import com.ptt.entity.Name;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class Manager {
    private final List<Client> listOfClients;
    private final List<Loan> listOfLoans;
    private final Scanner sc;

    public Manager(){
        this.listOfClients = new ArrayList<>();
        this.listOfLoans = new ArrayList<>();
        this.sc = new Scanner(System.in);
    }

    private int readInt(int min, int max) {
        int choice;
        while (true){
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice >= min && choice <= max)
                    break;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return choice;
    }

    //------------------------------- Client Tasks ---------------------------
    public void addClient(Client client){
        this.listOfClients.add(client);
    }
    public void addClient(){
        Client client = new Client(readName(), readID(), readAddress());
        this.listOfClients.add(client);
    }

    private int readID() {
        System.out.print("-> Enter ID: ");
        return readInt(0,Integer.MAX_VALUE);
    }

    private Address readAddress() {
        System.out.println("-----Enter Client Address-----");
        System.out.print("-> Number: ");
        int number = sc.nextInt();
        sc.nextLine();
        System.out.print("-> Street: ");
        String street = sc.nextLine();
        System.out.print("-> District: ");
        String district = sc.nextLine();
        System.out.print("-> City: ");
        String city = sc.nextLine();
        return new Address(number,street,district,city);
    }

    private Name readName() {
        System.out.println("-----Enter Client Name-----");
        System.out.print("-> First Name: ");
        String firstName = sc.nextLine();
        System.out.print("-> Last Name: ");
        String lastName = sc.nextLine();
        System.out.print("-> Middle Name: ");
        String middleName = sc.nextLine();
        return new Name(firstName,middleName,lastName);
    }

    public boolean removeClient(int index){
        if (index > clientCount()) return false;
        if (index != -1){
            this.listOfClients.remove(index);
            this.listOfLoans.remove(getLoanByID(getClient(index).getLoanID()));
            return true;
        }
        return false;
    }

    public boolean modifyClient(int index){
        if (index > clientCount()) return false;
        if (index != -1){
            Client clientModified = this.listOfClients.get(index);
            System.out.println("Change the info. Type '0' to keep remain");
            modifyName(clientModified);
            modifyID(clientModified);
            modifyAddress(clientModified);
            return true;
        }
        return false;
    }

    private void modifyAddress(Client clientModified) {
        System.out.println("-----Modify Client Address-----");
        System.out.print("-> Number: ");
        int number = sc.nextInt();
        sc.nextLine();
        if (number != 0) clientModified.getAddress().setNumber(number);
        System.out.print("-> Street: ");
        String street = sc.nextLine();
        if (!street.equals("0")) clientModified.getAddress().setStreet(street);
        System.out.print("-> District: ");
        String district = sc.nextLine();
        if (!district.equals("0")) clientModified.getAddress().setDistrict(district);
        System.out.print("-> City: ");
        String city = sc.nextLine();
        if (!city.equals("0")) clientModified.getAddress().setCity(city);
    }

    private void modifyID(Client clientModified) {
        System.out.println("-----Modify Client ID-----");
        System.out.print("-> Enter ID: ");
        int ID = sc.nextInt();
        if (ID != 0) clientModified.setID(ID);
        sc.nextLine();
    }

    private void modifyName(Client clientModified) {
        System.out.println("-----Modify Client Name-----");
        System.out.print("-> First Name: ");
        String firstName = sc.nextLine();
        if (!firstName.equals("0")) clientModified.getName().setFirstName(firstName);
        System.out.print("-> Last Name: ");
        String lastName = sc.nextLine();
        if (!lastName.equals("0")) clientModified.getName().setLastName(lastName);
        System.out.print("-> Middle Name: ");
        String middleName = sc.nextLine();
        if (!middleName.equals("0")) clientModified.getName().setMiddleName(middleName);
    }

    public int clientCount(){
        return this.listOfClients.size();
    }

    public Client getClient(int index){
       return this.listOfClients.get(index);
    }

    public Client getClientByID(int ID){
       int i;
       Client rs = new Client();
       for (i = 0; i < clientCount(); i++){
           Client client = getClient(i);
           if (client.getID() == ID) rs = client;
       }
       return rs;
    }

    //------------------------------- Loan Tasks -----------------------
    public void addLoan(Loan loan){
        this.listOfLoans.add(loan);
    }

    public void addLoan() {
        System.out.print("-> Enter No of Client (If Client doesn't exist, enter '0' to add new client): ");
        int no = readInt(0,Integer.MAX_VALUE);
        if (no == 0) {
            addClient();
            no = clientCount();
        }
        Client client = getClient(no-1);

        System.out.print("-> Enter annual interest rate: ");
        double annualInterestRate = sc.nextDouble(); sc.nextLine();

        System.out.print("-> Enter number of year: ");
        int numberOfYears = sc.nextInt(); sc.nextLine();

        System.out.print("-> Enter loan amount: ");
        double loanAmount = sc.nextDouble(); sc.nextLine();

        System.out.print("-> Enter loan date: ");
        String loanDate = sc.nextLine();

        System.out.print("-> Enter loan ID: ");
        int loanID = sc.nextInt();

        Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount, loanDate, client.getID());

        listOfLoans.add(loan);
        client.setLoanID(loanID);
    }

    public boolean removeLoan(int index){
        if (index > loanCount()) return false;
        if (index != -1){
            listOfLoans.remove(index);
            Loan loan = getLoan(index);
            Client client = getClientByID(loan.getClientID());
            client.setLoanID(0);
            return true;
        }
        return false;
    }

    public boolean modifyLoan(int index){
        if (index > loanCount()) return false;
        if (index != -1){
            Loan loanModified = this.listOfLoans.get(index);
            System.out.println("Change the info. Type '0' to keep remain");

            System.out.println("-> Annual Interest Rate: ");
            double air = sc.nextDouble(); sc.nextLine();
            if (air != 0) loanModified.setAnnualInterestRate(air);

            System.out.println("-> Number of Year: ");
            int noy = sc.nextInt(); sc.nextLine();
            if (noy != 0) loanModified.setNumberOfYears(noy);

            System.out.println("-> Loan Amount: ");
            double la = sc.nextDouble(); sc.nextLine();
            if (la != 0) loanModified.setLoanAmount(la);

            return true;
        }
        return false;
    }

    public int loanCount(){
        return this.listOfLoans.size();
    }

    public Loan getLoan(int index){
        if (index < 0 || index >= loanCount()){
            return null;
        }
        return this.listOfLoans.get(index);
    }

    public Loan getLoanByID(int ID){
        int i;
        for (i = 1; i < loanCount(); i++){
            Loan loan = getLoan(i);
            if (loan.getClientID() == ID) break;
        }
        return getLoan(i);
    }
}
