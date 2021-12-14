package com.example.spring.domain.account.application.port.outbound;

import com.example.spring.domain.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);
}
