package com.example.spring.domain.account.adaptor.inbound.web;

import com.example.spring.domain.account.application.port.inbound.SendMoneyUseCase;
import com.example.spring.domain.account.application.port.inbound.dto.SendMoneyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping("/accounts")
    void sendMoney(@Valid @RequestBody SendMoneyRequest request) {
        sendMoneyUseCase.sendMoney(request);
    }
}
