package com.example.spring.domain.account.application.port.inbound.dto;

import com.example.spring.domain.account.domain.Money;
import lombok.*;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendMoneyRequest {

    @NotNull
    private Long sourceAccountId;

    @NotNull
    private Long targetAccountId;

    @NotNull
    private Money money;
}
