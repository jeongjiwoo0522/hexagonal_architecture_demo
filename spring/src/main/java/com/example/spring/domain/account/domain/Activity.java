package com.example.spring.domain.account.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
public class Activity {

    @Getter
    private Long id;

    @Getter
    @NonNull
    private final Long ownerAccountId;

    @Getter
    @NonNull
    private final Long sourceAccountId;

    @Getter
    @NonNull
    private final Long targetAccountId;

    @Getter
    @NonNull
    private final LocalDateTime timestamp;

    @Getter
    @NonNull
    private final Money money;
}
