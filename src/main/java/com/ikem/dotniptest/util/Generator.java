package com.ikem.dotniptest.util;

public class Generator {
    private static final int ACCOUNT_LENGTH = 11;
    public static String  accountNumber(String lastAccountNumber){
        var parsedAccountNumber = Integer.parseInt(lastAccountNumber);
        parsedAccountNumber++;
        var length = ACCOUNT_LENGTH - String.valueOf(parsedAccountNumber).length();
        if(length <= 0){
            return String.valueOf(parsedAccountNumber);
        }
        return String.format("%0" + length + "d", parsedAccountNumber);
    }
}
