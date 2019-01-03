package cc.mrbird.core.validator;

import cc.mrbird.core.enums.ErrorCode;
import cc.mrbird.core.exception.CheckedException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author leo
 */
@SuppressWarnings("WeakerAccess")
public class BaseValidator {

    public static void throwException(ErrorCode errorCode, String message, Object... args) {
        throw new CheckedException(errorCode, message, args);
    }

    public static <T> void notNull(T o, ErrorCode errorCode, String message, Object... args) {
        if (o == null) {
            throwException(errorCode, message, args);
        }
    }

    public static void notEmpty(String s, ErrorCode errorCode, String message, Object... args) {
        if (StringUtils.isEmpty(s)) {
            throwException(errorCode, message, args);
        }
    }

    public static void empty(String s, ErrorCode errorCode, String message, Object... args) {
        if (StringUtils.isNotEmpty(s)) {
            throwException(errorCode, message, args);
        }
    }

    public static void notEmpty(Collection collection, ErrorCode errorCode, String message, Object... args) {
        if (CollectionUtils.isEmpty(collection)) {
            throwException(errorCode, message, args);
        }
    }

    public static void notEmpty(Map map, ErrorCode errorCode, String message, Object... args) {
        if (MapUtils.isNotEmpty(map)) {
            throwException(errorCode, message, args);
        }
    }

    public static void empty(Map map, ErrorCode errorCode, String message, Object... args) {
        if (MapUtils.isEmpty(map)) {
            throwException(errorCode, message, args);
        }
    }

    public static void empty(Collection collection, ErrorCode errorCode, String message, Object... args) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throwException(errorCode, message, args);
        }
    }

    public static <T extends Number> void gt(T n, T min, ErrorCode errorCode, String message, Object... args) {
        if (n.doubleValue() <= min.doubleValue()) {
            throwException(errorCode, message, args);
        }
    }

    public static <T extends Number> void gte(T n, T min, ErrorCode errorCode, String message, Object... args) {
        if (n.doubleValue() < min.doubleValue()) {
            throwException(errorCode, message, args);
        }
    }

    public static <T extends Number> void lte(T n, T max, ErrorCode errorCode, String message, Object... args) {
        if (n.doubleValue() > max.doubleValue()) {
            throwException(errorCode, message, args);
        }
    }

    public static void isTrue(Boolean b, ErrorCode errorCode, String message, Object... args) {
        if (b == null || !b) {
            throwException(errorCode, message, args);
        }
    }

    public static void eq(Object o1, Object o2, ErrorCode errorCode, String message, Object... args) {
        if (o1 == null || o2 == null || !o1.equals(o2)) {
            throwException(errorCode, message, args);
        }
    }

    public static <T extends Number> void between(T n, T min, T max, ErrorCode errorCode, String message, Object... args) {
        if (n.doubleValue() < min.doubleValue() || n.doubleValue() > max.doubleValue()) {
            throwException(errorCode, message, args);
        }
    }

}
