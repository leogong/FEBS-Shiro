package cc.mrbird.core.exception;

import cc.mrbird.core.enums.ErrorCode;

/**
 * @author leo on 06/11/2017.
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 149320109019027968L;

    public BusinessException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode, message, args);
    }

    public BusinessException(Throwable t, ErrorCode errorCode, String message, Object... args) {
        super(t, errorCode, message, args);
    }
}
