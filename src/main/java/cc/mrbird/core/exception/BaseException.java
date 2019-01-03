package cc.mrbird.core.exception;

import cc.mrbird.core.enums.ErrorCode;
import cc.mrbird.core.util.StringUtil;

/**
 * @author leo on 06/11/2017.
 */
@SuppressWarnings("WeakerAccess")
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;
    private static final long serialVersionUID = 122100638924808330L;

    public BaseException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode.getMessage() + " " + StringUtil.format(message, args));
        this.errorCode = errorCode;
    }

    public BaseException(Throwable t, ErrorCode errorCode, String message, Object... args) {
        super(errorCode.getMessage() + " " + StringUtil.format(message, args), t);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return s + ": errorCode: " + getErrorCode().getCode() + ", message: " + message;
    }
}
