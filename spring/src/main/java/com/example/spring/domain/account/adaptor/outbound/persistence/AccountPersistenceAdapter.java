package com.example.spring.domain.account.adaptor.outbound.persistence;

import com.example.spring.domain.account.application.port.outbound.LoadAccountPort;
import com.example.spring.domain.account.application.port.outbound.UpdateAccountStatePort;
import com.example.spring.domain.account.domain.Account;
import com.example.spring.domain.account.domain.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(Long accountId, LocalDateTime baselineDate) {
        AccountJpaEntity account = accountRepository.findById(accountId)
                        .orElseThrow(EntityNotFoundException::new);

        List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(accountId, baselineDate);

        Long withdrawalBalance = orZero(activityRepository.getWithdrawalBalanceUntil(accountId, baselineDate));

        Long depositBalance = orZero(activityRepository.getDepositBalanceUntil(accountId, baselineDate));

        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance);
    }

    @Override
    public void updateActivities(Account account) {
        for (Activity activity : account.getActivityWindow().getActivities()) {
            if (activity.getId() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }

    private Long orZero(Long value){
        return value == null ? 0L : value;
    }
}
