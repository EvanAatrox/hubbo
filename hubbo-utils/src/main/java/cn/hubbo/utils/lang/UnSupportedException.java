package cn.hubbo.utils.lang;

/**
 * @author 张晓华
 * @date 2023-10-20 10:01
 * @usage 当前类的用途描述
 */
public class UnSupportedException extends IllegalArgumentException{
    
    public UnSupportedException(String s) {
        super(s);
    }

    public UnSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnSupportedException(Throwable cause) {
        super(cause);
    }
    
}
