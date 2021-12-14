package com.example.spring.domain.account.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    @Getter
    private final Long id;

    private final Money baselineBalance;

    @Getter
    private final ActivityWindow activityWindow;

    public static Account withId(
            Long accountId,
            Money baselineBalance,
            ActivityWindow activityWindow) {
        return new Account(accountId, baselineBalance, activityWindow);
    }

    public Money calculateBalance() {
        return Money.add(
                this.baselineBalance,
                this.activityWindow.calculateBalance(this.id));
    }

    public boolean withdraw(Money money, Long targetAccountId) {

        if(!mayWithdraw(money)) {
            return false;
        }

        Activity withdrawal = Activity.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(this.id)
                .targetAccountId(targetAccountId)
                .timestamp(LocalDateTime.now())
                .money(money)
                .build();
        this.activityWindow.addActivity(withdrawal);
        return true;
    }

    private boolean mayWithdraw(Money money) {
        return Money.add(this.calculateBalance(), money.negate()).isPositiveOrZero();
    }

    public boolean deposit(Money money, Long sourceAccountId) {
        Activity deposit = Activity.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(sourceAccountId)
                .targetAccountId(this.id)
                .timestamp(LocalDateTime.now())
                .money(money)
                .build();

        this.activityWindow.addActivity(deposit);
        return true;
    }
}
