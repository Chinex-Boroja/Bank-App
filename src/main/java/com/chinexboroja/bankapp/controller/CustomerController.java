package com.chinexboroja.bankapp.controller;

import com.chinexboroja.bankapp.dto.BankResponse;
import com.chinexboroja.bankapp.dto.CustomerRequest;
import com.chinexboroja.bankapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer")
    public BankResponse createAccount(@RequestBody CustomerRequest customerRequest) {
        return customerService.createAccount(customerRequest);
    }
}
