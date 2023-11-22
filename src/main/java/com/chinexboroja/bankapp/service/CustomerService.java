package com.chinexboroja.bankapp.service;

import com.chinexboroja.bankapp.dto.BankResponse;
import com.chinexboroja.bankapp.dto.CustomerRequest;

public interface CustomerService {

    BankResponse createAccount(CustomerRequest customerRequest);
}
