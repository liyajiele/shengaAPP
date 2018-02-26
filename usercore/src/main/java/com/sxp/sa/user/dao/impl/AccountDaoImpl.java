package com.sxp.sa.user.dao.impl;

import com.sxp.sa.basic.entity.BaseDao;
import com.sxp.sa.user.dao.AccountDao;
import com.sxp.sa.user.entity.Account;
import com.sxp.sa.user.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2017/7/18.
 */

@Component
public class AccountDaoImpl extends BaseDao implements AccountDao {

    @Autowired
    private AccountRepository accountRepository;


    /**
     * 获取账户信息
     * @param uid
     * @return
     */
    @Override
    public Account getAccountInfo(Long uid) {
        return accountRepository.findByUser(uid);
    }


    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
