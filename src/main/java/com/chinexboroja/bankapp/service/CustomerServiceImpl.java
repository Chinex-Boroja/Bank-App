package com.chinexboroja.bankapp.service;

import com.chinexboroja.bankapp.dto.BankResponse;
import com.chinexboroja.bankapp.dto.CustomerRequest;
import com.chinexboroja.bankapp.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Override
    public BankResponse createAccount(CustomerRequest customerRequest) {

        /**
         * Save the new user into the Database
         */
        Customer newCustomer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .otherName(customerRequest.getOtherName())
                .gender(customerRequest.getGender())
                .address(customerRequest.getAddress())
                .stateOfOrigin(customerRequest.getStateOfOrigin())
                .accountNumber()
                .build();
        return ;
    }
}
