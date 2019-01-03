package cc.mrbird.telegram.constants;

/**
 * @author leo on 30/01/2018.
 */
@SuppressWarnings("WeakerAccess")
public class MessageConstants {
    // commands

    public static final String START = "/s";
    public static final String COUNT = "/c";
    public static final String LOAD = "/l";
    public static final String REBOOT = "/r";
    public static final String START_RESPONSE = "Hi my lord. \n\n" + START + "\n\n" + COUNT;

    // callback

    public static final String DOMAIN_CALLBACK = "domain_callback";

    public static final String EMPTY = "Empty";
    public static final String DONE = "Done";
    public static final String FAILED = "Failed";
}
