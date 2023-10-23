package cn.hubbo.common.exception.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 张晓华
 * @date 2023-10-23 09:40
 * @usage 当前类的用途描述
 */
public class CaptchaNotValidException extends AuthenticationException {
    
    public CaptchaNotValidException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CaptchaNotValidException(String msg) {
        super(msg);
    }
    
    
}
