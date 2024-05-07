package com.example.englishforkids.model;

public class RememberLogin {
    private String idAccount;
    private String macAddress;

    public RememberLogin(String idAccount, String macAddress) {
        this.idAccount = idAccount;
        this.macAddress = macAddress;
    }

    public RememberLogin() {
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
