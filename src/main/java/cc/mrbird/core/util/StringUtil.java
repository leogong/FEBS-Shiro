package cc.mrbird.core.util;

import cc.mrbird.core.log.SystemErrorLog;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author leo on 06/11/2017.
 */
public class StringUtil {

    public static String format(String str, Object... args) {
        if (ArrayUtils.isEmpty(args)) {
            return str;
        }
        try {
            return String.format(str, args);
        } catch (Exception e) {
            String argsStr = StringUtils.join(Arrays.asList(args), ",");
            String errMsg = String.format("format str failed, params={str:%s, args:[%s]}", str, argsStr);
            SystemErrorLog.error(e, errMsg);
            return str + ", args:[" + argsStr + "]";
        }
    }
}
