package com.chinexboroja.bankapp.service;

import com.chinexboroja.bankapp.dto.*;
import com.chinexboroja.bankapp.model.Customer;
import com.chinexboroja.bankapp.repository.CustomerRepository;
import com.chinexboroja.bankapp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public BankResponse createAccount(CustomerRequest customerRequest) {

        /**
         * Save the new user into the Database
         * Validate if a customer already exist with an email ID
         */
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        Customer newCustomer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .otherName(customerRequest.getOtherName())
                .gender(customerRequest.getGender())
                .address(customerRequest.getAddress())
                .stateOfOrigin(customerRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(customerRequest.getEmail())
                .phoneNumber(customerRequest.getPhoneNumber())
                .alternativePhoneNumber(customerRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        Customer savedCustomer = customerRepository.save(newCustomer);

        /*
        Send Email alert when a new bank customer is created
         */
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedCustomer.getEmail())
                .subject(AccountUtils.EMAIL_SUBJECT_MESSAGE)
                .messageBody(AccountUtils.EMAIL_MESSAGE_BODY + "\nYOUR ACCOUNT DETAILS: \nAccount Name: " +
                        savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " " + savedCustomer.getLastName() +
                "\nAccount Number: " + savedCustomer.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedCustomer.getAccountBalance())
                        .accountNumber(savedCustomer.getAccountNumber())
                        .accountName(savedCustomer.getFirstName() + " " + savedCustomer.getLastName()
                                + " " + savedCustomer.getOtherName())
                        .build())
                .build();

    }

    @Override
    public BankResponse getAccountBalance(EnquiryRequest enquiryRequest) {
        boolean isAccountExist = customerRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExist) {
            return  BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer foundCustomer = customerRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundCustomer.getAccountBalance())
//                        .accountNumber(foundCustomer.getAccountNumber())
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .accountName(foundCustomer.getFirstName() + " " + foundCustomer.getLastName()
                                + " " + foundCustomer.getOtherName())
                        .build())
                .build();
    }

    @Override
    public String getAccountName(EnquiryRequest enquiryRequest) {
        boolean isAccountExist = customerRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExist) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        Customer foundCustomer = customerRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundCustomer.getFirstName() + " " + foundCustomer.getLastName() + " " + foundCustomer.getOtherName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditRequest) {

        boolean isAccountExist = customerRepository.existsByAccountNumber(creditRequest.getAccountNumber());
        if (!isAccountExist) {
            return  BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer creditCustomer = customerRepository.findByAccountNumber(creditRequest.getAccountNumber());
        creditCustomer.setAccountBalance(creditCustomer.getAccountBalance().add(creditRequest.getAmount()));
        customerRepository.save(creditCustomer);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(creditRequest.getAccountNumber())
                        .accountName(creditCustomer.getFirstName() + " " + creditCustomer.getLastName() + " " + creditCustomer.getOtherName())
                        .accountBalance(creditCustomer.getAccountBalance())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest debitRequest) {
        boolean isAccountExist = customerRepository.existsByAccountNumber(debitRequest.getAccountNumber());
        if (!isAccountExist) {
            return  BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // Add email services for both debit and credit

        Customer debitCustomer = customerRepository.findByAccountNumber(debitRequest.getAccountNumber());

        // Alternative approach
        // BigInteger availableBalance = debitCustomer.getAccountBalance().toBigInteger;
        // BigInteger debitAmount = debitRequest.getAmount().toBigInteger;
        // if (availableBalance.intValue() < debitAmount.intValue())

        if (debitRequest.getAmount().compareTo(debitCustomer.getAccountBalance()) == 1) {
            return  BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        else {
            debitCustomer.setAccountBalance(debitCustomer.getAccountBalance().subtract(debitRequest.getAmount()));
            customerRepository.save(debitCustomer);
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(debitCustomer.getFirstName() + " " + debitCustomer.getLastName() + " " + debitCustomer.getOtherName())
                            .accountNumber(debitRequest.getAccountNumber())
                            .accountBalance(debitCustomer.getAccountBalance())
                            .build())
                    .build();
        }
    }
}
