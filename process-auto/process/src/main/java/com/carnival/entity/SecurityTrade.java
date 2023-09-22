package com.carnival.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2023-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_trade")
public class SecurityTrade implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "my_row_id", type = IdType.AUTO)
    private Long myRowId;

    private String tradeId;

    private BigDecimal tradeAmount;

    private String security;

    private String clientName;

    private LocalDate tradeDate;


}
