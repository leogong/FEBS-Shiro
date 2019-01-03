package cc.mrbird.core.enums;

/**
 * @author leo on 06/11/2017.
 */
public enum ErrorCode {

    /**
     * internal error
     */
    INTERNAL_ERROR("DDoSCoo.InternalError", "Internal Error!"),;

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
