package cc.mrbird.core.log;

import cc.mrbird.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leo on 06/11/2017.
 */
public class SystemErrorLog {

    private static final Logger LOG = LoggerFactory.getLogger(SystemErrorLog.class);

    public static void error(String format, Object... args) {
        LOG.error(StringUtil.format(format, args));
    }

    public static void error(Throwable t, String format, Object... args) {
        LOG.error(StringUtil.format(format, args), t);
    }

}
