package com.example.spring.domain.account.application.port.outbound;

import com.example.spring.domain.account.domain.Account;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(Long accountId, LocalDateTime baselineDate);
}
