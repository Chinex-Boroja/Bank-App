package com.chinexboroja.bankapp.service;

import com.chinexboroja.bankapp.dto.BankResponse;
import com.chinexboroja.bankapp.dto.CreditDebitRequest;
import com.chinexboroja.bankapp.dto.CustomerRequest;
import com.chinexboroja.bankapp.dto.EnquiryRequest;

public interface CustomerService {

    BankResponse createAccount(CustomerRequest customerRequest);

    BankResponse getAccountBalance(EnquiryRequest enquiryRequest);

    String getAccountName(EnquiryRequest enquiryRequest);

    BankResponse creditAccount(CreditDebitRequest creditRequest);

    BankResponse debitAccount(CreditDebitRequest debitRequest);
}
