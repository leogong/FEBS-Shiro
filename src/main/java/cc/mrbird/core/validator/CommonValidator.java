package cc.mrbird.core.validator;

import cc.mrbird.core.enums.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @author leo on 08/11/2017.
 */
@Component
public class CommonValidator extends BaseValidator {
    /**
     * 仅限内部传参校验使用。对外错误码统一返回INTERNAL_ERROR
     *
     * @param collection str
     * @param message    error message
     */
    public void notEmpty(Collection collection, String message, Object... args) {
        notEmpty(collection, ErrorCode.INTERNAL_ERROR, message, args);
    }

    /**
     * 仅限内部传参校验使用。对外错误码统一返回INTERNAL_ERROR
     *
     * @param map     str
     * @param message error message
     */
    public void notEmpty(Map map, String message, Object... args) {
        notEmpty(map, ErrorCode.INTERNAL_ERROR, message, args);
    }

    /**
     * 内部传参校验使用
     *
     * @param collection str
     * @param message    error message
     */
    public void empty(Collection collection, String message, Object... args) {
        empty(collection, ErrorCode.INTERNAL_ERROR, message, args);
    }

    /**
     * 内部传参校验使用
     *
     * @param str     str
     * @param message error message
     */
    public void notEmpty(String str, String message, Object... args) {
        notEmpty(str, ErrorCode.INTERNAL_ERROR, message, args);
    }

    /**
     * 内部传参校验使用
     *
     * @param o       object
     * @param message error message.
     */
    public void notNull(Object o, String message, Object... args) {
        notNull(o, ErrorCode.INTERNAL_ERROR, message, args);
    }

    /**
     * 内部传参校验使用
     *
     * @param number  number
     * @param message error message.
     */
    public void gtZero(Number number, String message, Object... args) {
        notNull(number, ErrorCode.INTERNAL_ERROR, message, args);
        gt(number, 0, ErrorCode.INTERNAL_ERROR, message, args);
    }

    /**
     * 内部传参校验使用
     *
     * @param b       b
     * @param message error message.
     */
    public void isTrue(Boolean b, String message, Object... args) {
        isTrue(b, ErrorCode.INTERNAL_ERROR, message, args);
    }

    /**
     * 内部传参校验使用
     *
     * @param b       b
     * @param message error message.
     */
    public void isFalse(Boolean b, String message, Object... args) {
        isTrue(!b, ErrorCode.INTERNAL_ERROR, message, args);
    }

    /**
     * 内部传参校验使用
     *
     * @param o1      o1
     * @param o2      o2
     * @param message error message.
     */
    public void eq(Object o1, Object o2, String message, Object... args) {
        eq(o1, o2, ErrorCode.INTERNAL_ERROR, message, args);
    }

    /**
     * 内部传参校验使用
     *
     * @param o1      o1
     * @param o2      o2
     * @param message error message.
     */
    public void lte(Number o1, Number o2, String message, Object... args) {
        notNull(o1, ErrorCode.INTERNAL_ERROR, message, args);
        notNull(o2, ErrorCode.INTERNAL_ERROR, message, args);
        lte(o1, o2, ErrorCode.INTERNAL_ERROR, message, args);
    }
}
