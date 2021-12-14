package com.example.spring.domain.account.application.port.inbound;

import com.example.spring.domain.account.application.port.inbound.dto.SendMoneyRequest;

public interface SendMoneyUseCase {

    boolean sendMoney(SendMoneyRequest command);
}
