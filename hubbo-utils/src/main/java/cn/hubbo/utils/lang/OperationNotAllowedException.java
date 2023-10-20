package cn.hubbo.utils.lang;

/**
 * @author 张晓华
 * @date 2023-10-20 09:58
 * @usage 当前类的用途描述
 */
public class OperationNotAllowedException extends UnSupportedException{

    public OperationNotAllowedException(String s) {
        super(s);
    }

    public OperationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationNotAllowedException(Throwable cause) {
        super(cause);
    }
    
    
}
