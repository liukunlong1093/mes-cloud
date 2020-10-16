package com.mes.app.manager;

import java.math.BigDecimal;

public interface AccountManager {

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 金额
     */
    void decrease(Long userId, BigDecimal money);
}
