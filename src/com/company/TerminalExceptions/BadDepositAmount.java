package com.company.TerminalExceptions;

import com.company.TERMINAL_MESSAGES;

public class BadDepositAmount extends TerminalException {
    public BadDepositAmount(String message){
        super(message);
    }
}
