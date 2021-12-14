package com.example.spring.domain.account.application.service;

import com.example.spring.domain.account.application.port.inbound.SendMoneyUseCase;
import com.example.spring.domain.account.application.port.inbound.dto.SendMoneyRequest;
import com.example.spring.domain.account.application.port.outbound.LoadAccountPort;
import com.example.spring.domain.account.application.port.outbound.UpdateAccountStatePort;
import com.example.spring.domain.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyRequest command) {

        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId(), baselineDate);
        Account targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId(), baselineDate);

        Long sourceAccountId = sourceAccount.getId();
        Long targetAccountId = targetAccount.getId();

        if(!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
            throw new IllegalStateException();
        }

        if(!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
            throw new IllegalStateException();
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        return true;
    }
}
