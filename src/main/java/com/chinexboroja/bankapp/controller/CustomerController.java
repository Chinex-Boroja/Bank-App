package com.chinexboroja.bankapp.controller;

import com.chinexboroja.bankapp.dto.BankResponse;
import com.chinexboroja.bankapp.dto.CreditDebitRequest;
import com.chinexboroja.bankapp.dto.CustomerRequest;
import com.chinexboroja.bankapp.dto.EnquiryRequest;
import com.chinexboroja.bankapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer")
    public BankResponse createAccount(@RequestBody CustomerRequest customerRequest) {
        return customerService.createAccount(customerRequest);
    }

    @GetMapping("/customer-balance")
    public BankResponse getAccountBalance(@RequestBody EnquiryRequest request) {
        return customerService.getAccountBalance(request);
    }

    @GetMapping("/customer-name")
    public String getCustomerName(@RequestBody EnquiryRequest request) {
        return customerService.getAccountName(request);
    }

    @PostMapping("/customer-credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request) {
        return customerService.creditAccount(request);
    }

    @PostMapping("/customer-debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request) {
        return customerService.debitAccount(request);
    }
}
