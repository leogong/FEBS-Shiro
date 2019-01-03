package cc.mrbird.system.dao;


import cc.mrbird.core.domain.entity.DomainDO;
import cc.mrbird.core.domain.entity.DomainQuery;

import java.util.List;

/**
 * @author leo on 16/01/2018.
 */
public interface DomainMapper /*extends MyMapper<DomainDO>*/ {
    /**
     * insert
     *
     * @param domain domain
     */
    int insert(DomainDO domain);

    /**
     * update
     *
     * @param domain domain
     */
    void update(DomainDO domain);

    /**
     * select
     *
     * @param domainQuery domain query object
     * @return domainList
     */
    List<DomainDO> select(DomainQuery domainQuery);

    /**
     * count
     *
     * @param domainQuery domain query object
     * @return count count
     */
    Integer count(DomainQuery domainQuery);
}
