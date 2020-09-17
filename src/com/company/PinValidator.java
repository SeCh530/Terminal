package com.company;

import com.company.TerminalExceptions.BadPinException;
import com.company.TerminalExceptions.TerminalException;
import com.company.TerminalExceptions.TerminalIsBlockedException;

import java.time.Instant;

public class PinValidator {
  private static final int MAX_ATTEMPTS_COUNT = 3;
  private static final int BLOCK_TIME_SEC = 10;

  private int currAttempts;
  private boolean isLocked;
  private Instant lockTime;
  private boolean isAuthenticated;
  private String currUserPin;

  public PinValidator() {
    currAttempts = 0;
    isAuthenticated = false;
    isLocked = false;
    currUserPin = "";
  }

  private String getPinFromProvider() {
    return "0123";
  }

  private boolean isCorrectPin(String pin) {
    return getPinFromProvider().equals(pin);
  }

  private void unblock() {
    isLocked = false;
    currAttempts = 0;
  }

  private void checkLock() throws TerminalIsBlockedException {
    if (isLocked) {
      final long timePassed = Instant.now().getEpochSecond() - lockTime.getEpochSecond();
      if (timePassed < BLOCK_TIME_SEC)
        throw new TerminalIsBlockedException(
            TERMINAL_MESSAGES.BLOCK_MESSAGE + (BLOCK_TIME_SEC - timePassed));
      else {
        unblock();
      }
    }
  }

  private void isTimeForBlock() {
    currAttempts++;
    if (currAttempts == MAX_ATTEMPTS_COUNT) {
      this.isLocked = true;
      lockTime = Instant.now();
    }
  }

  public void isUnlocked() throws TerminalException {
    if (!isAuthenticated) {
      throw new TerminalException(TERMINAL_MESSAGES.NOT_AUTH);
    }
  }

  private boolean isNumericString(String str){
    return str.chars().allMatch( Character::isDigit );
  }

  public void nextPinInpserted(String inputPin) throws TerminalException {
    if (isNumericString(inputPin)) {
      currUserPin = inputPin;
      if (currUserPin.length() == 4) {
        validator();
      }
    } else {
      throw new TerminalException(TERMINAL_MESSAGES.BAD_PIN_CHAR);
    }
  }

  private void clearPin() {
    isAuthenticated = false;
    currUserPin = "";
  }

  private void validator() throws TerminalIsBlockedException, BadPinException {
    checkLock();
    if (isCorrectPin(currUserPin)) {
      isAuthenticated = true;
      currAttempts = 0;
    } else {
      clearPin();
      isTimeForBlock();
      if (!isLocked && currAttempts!=3) throw new BadPinException(TERMINAL_MESSAGES.BAD_PIN);
      else throw new BadPinException(
            TERMINAL_MESSAGES.BAD_PIN + "\n" + TERMINAL_MESSAGES.BLOCK_MESSAGE +" "+ BLOCK_TIME_SEC+" секунды");
    }
  }
}
