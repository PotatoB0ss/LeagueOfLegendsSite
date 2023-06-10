package com.example.demo.accounts;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;


import java.util.List;

import java.util.Optional;

@Repository
public interface AccountRepository
        extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountCategory(AccountCategory accountCategory);

    @Cacheable(value = "accountsCache", key = "'accountsByRegionAndCategory_' + #region + '_' + #category")
    @Query("SELECT Count(a) FROM Account a WHERE a.accountRegion = ?1 AND a.accountCategory = ?2 AND a.orderId = null")
    int findByAccountRegionAndAccountCategory(AccountRegion region, AccountCategory category);

    @Transactional
    @Query("SELECT a FROM Account a WHERE a.accountRegion = ?1 AND a.accountCategory = ?2 AND a.orderId = null")
    Page<Account> fAccs(AccountRegion region, AccountCategory category, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.orderId = :orderId WHERE a IN :accounts")
    void updateOrderIdsForAccounts(@Param("accounts") List<Account> accounts, @Param("orderId") Long orderId);

    List<Account> findAccountsByOrderId(Long orderId);


}

