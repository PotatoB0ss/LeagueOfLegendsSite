package com.example.demo.accounts;



import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    @CacheEvict(value = "accountsCache", allEntries = true)
    public void save(Account account){
        accountRepository.save(account);
    }


    public Model getAccountsCount(Model model){
        model.addAttribute("ru_active", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.RU, AccountCategory.ACTIVE));
        model.addAttribute("ru_inactive", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.RU, AccountCategory.INACTIVE));
        model.addAttribute("ru_iron_bronze", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.RU, AccountCategory.IRON_BRONZE));
        model.addAttribute("ru_silver", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.RU, AccountCategory.SILVER));
        model.addAttribute("ru_gold", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.RU, AccountCategory.GOLD));
        model.addAttribute("ru_platinum", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.RU, AccountCategory.PLATINUM));
        model.addAttribute("eu_active", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.EU, AccountCategory.ACTIVE));
        model.addAttribute("eu_inactive", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.EU, AccountCategory.INACTIVE));
        model.addAttribute("eu_iron_bronze", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.EU, AccountCategory.IRON_BRONZE));
        model.addAttribute("eu_silver", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.EU, AccountCategory.SILVER));
        model.addAttribute("eu_gold", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.EU, AccountCategory.GOLD));
        model.addAttribute("eu_platinum", accountRepository.findByAccountRegionAndAccountCategory(AccountRegion.EU, AccountCategory.PLATINUM));

        return model;
    }

    public Page<Account> getAccounts(AccountRegion accountRegion, AccountCategory accountStatus, Pageable pageable){
        return accountRepository.fAccs(accountRegion, accountStatus, pageable);
    }

    public void deleteAccounts(List<Account> accounts){
        accountRepository.deleteAll(accounts);
    }

    public void setAccountsOrder(List<Account> accounts, Long orderId){
        accountRepository.updateOrderIdsForAccounts(accounts, orderId);
    }

    public List<Account> findAccountByOrderId(Long orderId){
        return accountRepository.findAccountsByOrderId(orderId);
    }


}
