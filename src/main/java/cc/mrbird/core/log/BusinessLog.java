package cc.mrbird.core.log;

import cc.mrbird.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leo on 06/11/2017.
 */
public class BusinessLog {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessLog.class);

    public static void info(String format, Object... args) {
        LOG.info(StringUtil.format(format, args));
    }
}
