package cc.mrbird.core.domain.entity;

/**
 * @author leo on 18/01/2018.
 */
public enum DomainStatus {
    /**
     * waiting
     */
    WAIT(1),
    /**
     * no
     */
    NO(2),
    /**
     * yes
     */
    YES(3),
    /**
     * expensive
     */
    EXPENSIVE(4),
    /**
     * hold
     */
    HOLD(5),
    /**
     * buy
     */
    BUY(6);
    private Integer code;

    DomainStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static DomainStatus of(Integer status) {
        for (DomainStatus domainStatus : values()) {
            if (domainStatus.getCode().equals(status)) {
                return domainStatus;
            }
        }
        return null;
    }

    public static DomainStatus of(String status) {
        for (DomainStatus domainStatus : values()) {
            if (domainStatus.name().equalsIgnoreCase(status)) {
                return domainStatus;
            }
        }
        return null;
    }
}
