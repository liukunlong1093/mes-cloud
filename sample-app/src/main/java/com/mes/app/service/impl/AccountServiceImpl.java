package com.mes.app.service.impl;

import com.mes.app.dao.AccountMapper;
import com.mes.app.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Description: 账户业务实现类
 * 
 * @author JourWon
 * @date 2019/12/25 17:29
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private AccountMapper accountDAO;

    /**
     * 扣减账户余额
     */
    @Override
    public void decrease(Long userId, BigDecimal money) {
        LOGGER.info("------->account-service中扣减账户余额开始");
        //模拟超时异常，全局事务回滚
        //try {
         //   Thread.sleep(30*1000);
       //} catch (InterruptedException e) {
         //  e.printStackTrace();
        //}
        accountDAO.decrease(userId,money);
        LOGGER.info("------->account-service中扣减账户余额结束");
    }
}
