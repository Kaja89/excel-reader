package model;

/**
 * @author Kaja D.
 */

import java.time.LocalDate;

public class Contract {

    private long id;

    private String contractNumber;//id plus year?

    private LocalDate dateFrom;

    private LocalDate dateUntil;

    private double totalCost;

    private String accountType;

    private String accountingPeriod;

    private boolean active;

    private String systemInfo;

    public Contract() {
    }

    public Contract(String contractNumber, LocalDate dateFrom, LocalDate dateUntil, double totalCost, String accountType, String accountingPeriod, boolean active, String systemInfo) {
        this.contractNumber = contractNumber;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.totalCost = totalCost;
        this.accountType = accountType;
        this.accountingPeriod = accountingPeriod;
        this.active = active;
        this.systemInfo = systemInfo;
    }

    public long getId() {
        return id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateUntil() {
        return dateUntil;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAccountingPeriod() {
        return accountingPeriod;
    }

    public boolean isActive() {
        return active;
    }

    public String getSystemInfo() {
        return systemInfo;
    }

    public void deactivate() {
        active = false;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateUntil(LocalDate dateUntil) {
        this.dateUntil = dateUntil;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setAccountingPeriod(String accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSystemInfo(String systemInfo) {
        this.systemInfo = systemInfo;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractNumber='" + contractNumber + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateUntil=" + dateUntil +
                ", totalCost=" + totalCost +
                ", accountType='" + accountType + '\'' +
                ", accountingPeriod='" + accountingPeriod + '\'' +
                ", active=" + active +
                ", systemInfo='" + systemInfo + '\'' +
                '}';
    }
}

