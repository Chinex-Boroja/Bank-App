package com.chinexboroja.bankapp.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "Sorry!! Customer already exists";
    public static final String ACCOUNT_CREATION_CODE = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been successfully created";

    /**
     * Begins with the current year, with any random six digits
     */
    public static String generateAccountNumber() {

        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        // Generate a random number between minimum and maximum
        int randomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        // Concatenate the current year with the randomNumber and convert it to a string
       return currentYear + String.valueOf(randomNumber);
    }
}
