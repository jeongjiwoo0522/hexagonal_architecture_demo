package com.example.spring.domain.account.adaptor.outbound.persistence;

import com.example.spring.domain.account.domain.Account;
import com.example.spring.domain.account.domain.Activity;
import com.example.spring.domain.account.domain.ActivityWindow;
import com.example.spring.domain.account.domain.Money;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class AccountMapper {

    Account mapToDomainEntity(
            AccountJpaEntity account,
            List<ActivityJpaEntity> activities,
            Long withdrawalBalance,
            Long depositBalance) {

        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance));

        return Account.withId(
                account.getId(),
                baselineBalance,
                mapToActivityWindow(activities));
    }

    ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {
        List<Activity> mappedActivities = activities.stream()
                .map(activity -> Activity.builder()
                        .id(activity.getId())
                        .ownerAccountId(activity.getOwnerAccountId())
                        .sourceAccountId(activity.getSourceAccountId())
                        .targetAccountId(activity.getTargetAccountId())
                        .timestamp(activity.getTimestamp())
                        .money(Money.of(activity.getAmount()))
                        .build())
                .collect(Collectors.toList());

        return new ActivityWindow(mappedActivities);
    }

    ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return ActivityJpaEntity.builder()
                .id(activity.getId())
                .timestamp(activity.getTimestamp())
                .ownerAccountId(activity.getOwnerAccountId())
                .sourceAccountId(activity.getSourceAccountId())
                .targetAccountId(activity.getTargetAccountId())
                .amount(activity.getMoney().getAmount().longValue())
                .build();
    }
}
