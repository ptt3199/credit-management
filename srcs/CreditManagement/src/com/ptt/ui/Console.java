package com.ptt.ui;

import com.ptt.controller.Manager;

import com.ptt.entity.Address;
import com.ptt.entity.Client;
import com.ptt.entity.Loan;
import com.ptt.entity.Name;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Console {
    private final Scanner sc;
    private final Manager m;

    public Console(){
        this.sc = new Scanner(System.in);
        this.m = new Manager();
    }

    private int menu(){
        System.out.println(".______________________________.");
        System.out.println("|===== CREDIT MANAGE MENU =====|");
        System.out.println("| 1. Client Manage Menu        |");
        System.out.println("| 2. Loan Manage Menu          |");
        System.out.println("| 3. Predict Profit            |");
        System.out.println("| 0. Exit                      |");
        System.out.println("|______________________________|");
        System.out.print("Enter your choice: ");
        return readInt(0,3);
    }

    public void Start(){
        loadClientData();
        loadLoanData();
        while(true){
            int choice = menu();
            switch (choice) {
                case 0 -> {
                    writeClientData();
                    writeLoanData();
                    System.exit(0);
                }
                case 1 -> ClientTasks();
                case 2 -> LoanTasks();
                case 3 -> PredictProfit();
                default -> throw new AssertionError();
            }
        }
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
    // --------------------------------------- Client Menu --------------------------------------------
    private void ClientTasks(){
        boolean cont = true;
        while(cont){
            int choice = clientMenu();
            switch (choice) {
                case 0 -> cont = false;
                case 1 -> addClient();
                case 2 -> showAllClient();
                case 3 -> modifyClient();
                case 4 -> removeClient();
                default -> throw new AssertionError();
            }
        }
    }
    private int clientMenu(){
        System.out.println("._______________________.");
        System.out.println("|======Client Menu======|");
        System.out.println("| 1. Add New Client     |");
        System.out.println("| 2. Show All Client    |");
        System.out.println("| 3. Modify Client Info |");
        System.out.println("| 4. Remove Client      |");
        System.out.println("| 0. Back to Main Menu  |");
        System.out.println("|_______________________|");
        System.out.print("Enter your choice: ");
        return readInt(0, 4);
    }

    private void loadClientData(){
        Scanner scc;
        final String clientDataPath = "E:\\OneDrive\\CreditManagement\\src\\ClientData.txt";
        try {
            scc = new Scanner(new File(clientDataPath));
            while (scc.hasNextLine()) {
                String line = scc.nextLine();
                String[] inf = line.split(",");
                String firstName = inf[0];
                String middleName = inf[1];
                String lastName = inf[2];
                Name name = new Name(firstName, middleName, lastName);
                int ID = Integer.parseInt(inf[3]);
                int number = Integer.parseInt(inf[4]);
                String street = inf[5];
                String district = inf[6];
                String city = inf[7];
                int loanID = Integer.parseInt(inf[8]);
                Address address = new Address(number, street, district, city);
                Client c = new Client(name, ID, address, loanID);
                m.addClient(c);
            }
            scc.close();
            System.out.println("Successfully load Client data.");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void addClient() {
       m.addClient();
    }

    private void showAllClient(){
        System.out.println("========= All Client =========");
        System.out.println("No | Full Name | ID | Address ");
        for (int i = 0; i < m.clientCount(); i++) {
            Client c = m.getClient(i);
            System.out.println((i+1) + " | " + c.getName().getText() + " | " + c.getID() + " | "+ c.getAddress().getAddressText());
        }
        System.out.println("===============================");
    }

    private void removeClient(){
        showAllClient();
        System.out.print("-> Enter the No of Client to remove: ");
        int index = readInt(1,Integer.MAX_VALUE);
        boolean result = this.m.removeClient(index - 1);
        if (result) System.out.println("Client was removed!");
        else System.out.println("Client not found!");
    }

    private void modifyClient(){
        showAllClient();
        System.out.print("-> Enter the No of Client to modify: ");
        int index = readInt(1,Integer.MAX_VALUE);
        boolean result = this.m.modifyClient(index - 1);
        if (result) System.out.println("Client was modified!");
        else System.out.println("Client not found!");
    }

    private void writeClientData(){
        final String clientDataPath = "E:\\OneDrive\\CreditManagement\\src\\ClientData.txt";
        try {
            FileWriter myWriter = new FileWriter(clientDataPath);
            for (int i=0; i < m.clientCount(); i++){
                Client client = m.getClient(i);
                myWriter.write(client.getName().getForFile() +"," + client.getID() + "," + client.getAddress().getForFile() + "," + client.getLoanID()+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote Client data to ClientData.txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //--------------------------------------------- Loan Menu ---------------------------------------
    private void LoanTasks(){
        boolean cont = true;
        while(cont){
            int choice = loanMenu();
            switch (choice) {
                case 0 -> cont = false;
                case 1 -> addLoan();
                case 2 -> showAllLoan();
                case 3 -> modifyLoan();
                case 4 -> removeLoan();
                default -> throw new AssertionError();
            }
        }
    }
    private void loadLoanData() {
        Scanner scl;
        final String loanDataPath = "E:\\OneDrive\\CreditManagement\\src\\LoanData.txt";
        try {
            scl = new Scanner(new File(loanDataPath));
            while (scl.hasNextLine()) {
                String line = scl.nextLine();
                String[] inf = line.split(",");
                double loanAmount = Double.parseDouble(inf[0]);
                double annualInterestRate = Double.parseDouble(inf[1]);
                int numberOfYears = Integer.parseInt(inf[2]);
                String loanDate = inf[3];
                int clientID = Integer.parseInt(inf[4]);
                Loan l = new Loan(annualInterestRate, numberOfYears, loanAmount, loanDate, clientID);
                m.addLoan(l);
            }
            scl.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private int loanMenu(){
        System.out.println(".______________________.");
        System.out.println("|=======Loan Menu======|");
        System.out.println("| 1. Add New Loan      |");
        System.out.println("| 2. Show All Loan     |");
        System.out.println("| 3. Modify Loan       |");
        System.out.println("| 4. Remove Loan       |");
        System.out.println("| 0. Back to Main Menu |");
        System.out.println("|______________________|");
        System.out.print("Enter your choice: ");
        return readInt(0, 4);
    }

    private void addLoan(){
        showAllClient();
        m.addLoan();
    }

    private void showAllLoan(){
        System.out.println("=================================== ALL LOANS ===============================================");
        System.out.println("No | Client's Name | Loan Amount | AIR | NoY | Monthly Payment | Total Payment | Loan Date");
        for (int i = 0; i < m.loanCount(); i++) {
            Loan loan = m.getLoan(i);
            Client client = m.getClientByID(loan.getClientID()); String nameText = client.getName().getText();
            System.out.println((i+1) + " | " + nameText + " | " + loan.getLoanAmount() + " | " + loan.getAnnualInterestRate()
                    + " | " + loan.getNumberOfYears() + " | "+ (double) Math.round(loan.monthlyPayment()*100)/100 + " | " + (double) Math.round(loan.totalPayment()*100)/100 + " | " + loan.getLoanDate());
        }
        System.out.println("* AIR = Annual Interest Rate, NoY = Number of Year ");
        System.out.println("Result was rounded in 2 digits");
        System.out.println("==============================================================================================");
    }

    private void removeLoan(){
        showAllLoan();
        System.out.print("-> Enter the No of Loan to remove: ");
        int index = readInt(1,Integer.MAX_VALUE);
        boolean result = this.m.removeLoan(index - 1);
        if (result) System.out.println("Loan was removed!");
        else System.out.println("Loan not found!");
    }

    private void modifyLoan(){
        showAllLoan();
        System.out.print("-> Enter the No of Loan to modify: ");
        int index = readInt(1,Integer.MAX_VALUE);
        boolean result = this.m.modifyLoan(index - 1);
        if (result) System.out.println("Loan was modified!");
        else System.out.println("Loan not found!");
    }

    private void writeLoanData(){
        final String loanDataPath = "E:\\OneDrive\\CreditManagement\\src\\LoanData.txt";
        try{
            FileWriter myWriter = new FileWriter(loanDataPath);
            for (int i=0; i < m.loanCount(); i++){
                Loan loan = m.getLoan(i);
                myWriter.write(loan.getLoanAmount() +"," + loan.getAnnualInterestRate() + "," + loan.getNumberOfYears() + "," + loan.getLoanDate() + "," + loan.getClientID()+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote Loan data to LoanData.txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //---------------------------------- Predict Profit --------------------------------
    private void PredictProfit(){
        System.out.println("==================== ALL LOANS ===================");
        System.out.println("No | Loan Amount | Monthly Payment | Loan Date | NoY");
        double profit = 0;
        for (int i = 0; i < m.loanCount(); i++) {
            Loan loan = m.getLoan(i);
            System.out.println((i+1) + " | " + loan.getLoanAmount() + " | " +
                    (double) Math.round(loan.monthlyPayment()*100)/100 + " | " + loan.getLoanDate() + " | " + loan.getNumberOfYears());
            profit += loan.monthlyPayment();
        }
        System.out.println("TOTAL PROFIT THIS MONTH: " + (double) Math.round(profit*100)/100);
        System.out.println("Result was rounded in 2 digits");
        System.out.println("===================================================");
    }
}
