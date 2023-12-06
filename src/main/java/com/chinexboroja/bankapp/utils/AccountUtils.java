package com.chinexboroja.bankapp.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "Sorry!! Customer already exists";
    public static final String ACCOUNT_CREATION_CODE = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been successfully created";
    public static final String EMAIL_SUBJECT_MESSAGE = "ACCOUNT CREATION";
    public static final String EMAIL_MESSAGE_BODY = "Congratulations! Your Account has been created successfully";
    public static final String ACCOUNT_NOT_EXIST_CODE = "003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "Customer with the provided Account number does not exist";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_SUCCESS_MESSAGE = "Customer Account Found";
    public static final String ACCOUNT_CREDITED_SUCCESS_CODE = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Customer Account has been credited successfully!";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Sorry! Insufficient Balance!";

    public static final String ACCOUNT_DEBITED_SUCCESS_CODE  = "007";
    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "Customer Account has been debited successfully";



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
