package com.company;

import com.company.TerminalExceptions.*;

public interface Terminal {
    void authUserWithPin(String pin) throws TerminalException;
    double showBalance() throws TerminalException;
    void withdraw(double amount) throws TerminalException;
    void deposit(double amount) throws TerminalException;
    void isUnlocked() throws TerminalException;
}
