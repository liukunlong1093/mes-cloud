package com.mes.app.controller;

import com.mes.app.manager.AccountManager;
import com.mes.app.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 账户控制器
 * @author Administrator
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountManager accountManager;

    /**
     * 扣减账户余额
     */
    @RequestMapping("/decrease")
    public Result decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        accountManager.decrease(userId,money);
        return new Result("扣减账户余额成功！",200);
    }
}
