package com.mes.app.dto;

import java.math.BigDecimal;

/**
 * AccountDTO
 *
 * @author Administrator
 * @date 2020/4/22
 */
public class AccountDTO {
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 总额度
     */
    private BigDecimal total;

    /**
     * 已用额度
     */
    private BigDecimal used;

    /**
     * 剩余额度
     */
    private BigDecimal residue;
}
