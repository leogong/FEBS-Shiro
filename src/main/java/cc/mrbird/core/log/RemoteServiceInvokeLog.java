package cc.mrbird.core.log;

import cc.mrbird.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RemoteServiceInvokeLog
 *
 * @author leo
 */
public class RemoteServiceInvokeLog {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteServiceInvokeLog.class);

    public static void info(String format, Object... args) {
        LOG.info(StringUtil.format(format, args));
    }

    public static void error(String format, Object... args) {
        LOG.error(StringUtil.format(format, args));
    }

    public static void error(Throwable t, String format, Object... args) {
        LOG.error(StringUtil.format(format, args), t);
    }

}
