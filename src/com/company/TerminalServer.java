package com.company;

import com.company.TerminalExceptions.BadWithdrawAmount;

import java.util.Random;

public class TerminalServer {
    private double balance;

    public TerminalServer() {
        balance= (int)(Math.random()*6000)+1;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount){
        balance += amount;
    }

    public void withdraw(double amount) throws BadWithdrawAmount {
        if(amount > balance) {
            throw new BadWithdrawAmount(TERMINAL_MESSAGES.NOT_ANOUTH_MONEY);
        }
        balance -= amount;
    }

}