package com.company.TerminalExceptions;

import com.company.TERMINAL_MESSAGES;

public class BadWithdrawAmount extends TerminalException{
    public BadWithdrawAmount(String message){
        super(message);
    }
}
