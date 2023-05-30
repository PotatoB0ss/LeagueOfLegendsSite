package com.example.demo.accounts;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

@Repository
public interface AccountRepository
        extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountCategory(AccountCategory accountCategory);

    @Cacheable(value = "accountsCache", key = "'accountsByRegionAndCategory_' + #region + '_' + #category")
    @Query("SELECT Count(a) FROM Account a WHERE a.accountRegion = ?1 AND a.accountCategory = ?2")
    int findByAccountRegionAndAccountCategory(AccountRegion region, AccountCategory category);


}
