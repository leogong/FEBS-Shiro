package cc.mrbird.core.domain.manager.db;


import cc.mrbird.core.domain.entity.DomainDO;
import cc.mrbird.core.domain.entity.DomainQuery;
import cc.mrbird.core.domain.entity.DomainStatus;

import java.util.Date;
import java.util.List;

/**
 * @author leo on 16/01/2018.
 */
public interface DomainManager {
    /**
     * insert
     *
     * @param domain domain
     */
    void insert(DomainDO domain);

    void insert(String domain, Integer price, Integer productType, Date endTime, DomainStatus domainStatus, Integer prefixLength,
                Integer suffixLength);

    /**
     * update
     *
     * @param domain domain
     */
    void update(DomainDO domain);

    void update(String domain, Integer price, Integer productType, Date endTime);

    void update(String domain, Integer price, Integer productType, Date endTime, DomainStatus domainStatus);

    void insertOrUpdate(String domain, Integer price, Integer productType, Date endTime, DomainStatus domainStatus);

    /**
     * select
     *
     * @param domainQuery domain query object
     * @return domainList
     */
    List<DomainDO> select(DomainQuery domainQuery);

    DomainDO select(String domain);

    /**
     * count
     *
     * @param domainQuery domain query object
     * @return count count
     */
    Integer count(DomainQuery domainQuery);

    Integer count(String domain);
}
