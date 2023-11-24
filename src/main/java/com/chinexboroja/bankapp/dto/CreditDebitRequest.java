package com.chinexboroja.bankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDebitRequest {

    private String accountNumber;
    private BigDecimal amount;
//    private String accountName;

}
