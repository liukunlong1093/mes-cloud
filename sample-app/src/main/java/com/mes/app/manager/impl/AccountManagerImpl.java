package com.mes.app.manager.impl;

import com.mes.app.manager.AccountManager;
import com.mes.app.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * AccountManagerImpl
 *
 * @author Administrator
 * @date 2020/4/22
 */
@Service("accountManager")
public class AccountManagerImpl implements AccountManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagerImpl.class);
    @Resource
    private AccountService accountService;
    @Override
    public void decrease(Long userId, BigDecimal money) {
        LOGGER.info("------->account-service中扣减账户余额开始");
        //模拟超时异常，全局事务回滚
        //try {
        //   Thread.sleep(30*1000);
        //} catch (InterruptedException e) {
        //  e.printStackTrace();
        //}
        accountService.decrease(userId,money);
        LOGGER.info("------->account-service中扣减账户余额结束");
    }
}
