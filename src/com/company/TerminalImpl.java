package com.company;

import com.company.TerminalExceptions.*;

public class TerminalImpl implements Terminal {
  private final TerminalServer server;
  private final PinValidator pinValidator;

  public TerminalImpl() {
    this.server = new TerminalServer();
    this.pinValidator = new PinValidator();
  }

  public void isUnlocked() throws TerminalException {
    pinValidator.isUnlocked();
  }

  private boolean isBadAmount(double amount) {
    return (amount % 100 != 0) || (amount <= 0);
  }

  public void authUserWithPin(String pin) throws TerminalException {

    this.pinValidator.nextPinInpserted(pin);
  }

  public double showBalance() throws TerminalException {
    pinValidator.isUnlocked();
    return server.getBalance();
  }

  public void checkDepositAmount(double amount) throws BadDepositAmount {
    if (isBadAmount(amount)) {
      throw new BadDepositAmount(TERMINAL_MESSAGES.BAD_DEPOSIT_WITHDRAW_AMOUNT);
    }
  }

  public void checkWithdrawAmount(double amount) throws BadWithdrawAmount {
    if (isBadAmount(amount)) {
      throw new BadWithdrawAmount(TERMINAL_MESSAGES.BAD_DEPOSIT_WITHDRAW_AMOUNT);
    }
  }

  public void deposit(double amount) throws TerminalException {
    pinValidator.isUnlocked();
    checkDepositAmount(amount);
    server.deposit(amount);
  }

  public void withdraw(double amount) throws TerminalException {
    pinValidator.isUnlocked();
    checkWithdrawAmount(amount);
    server.withdraw(amount);
  }
}
