package com.sxp.sa.user.dao;

import com.sxp.sa.user.entity.Account;

/**
 * Created by dell on 2017/7/18.
 */
public interface AccountDao {


    Account getAccountInfo(Long uid);

    Account save(Account account);
}
