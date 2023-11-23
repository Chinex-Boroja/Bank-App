package com.chinexboroja.bankapp.service;

import com.chinexboroja.bankapp.dto.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
}
