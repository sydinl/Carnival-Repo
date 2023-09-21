package com.carnival.common.exception.user;

import com.carnival.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author carnival
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
