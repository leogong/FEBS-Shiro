package cc.mrbird.core.domain.service;

import cc.mrbird.core.domain.entity.DomainDO;
import cc.mrbird.core.domain.entity.DomainQuery;
import cc.mrbird.core.domain.entity.DomainStatus;

import java.util.List;

/**
 * @author leo on 17/01/2018.
 */
public interface DomainMgrService {
    void loadDomain();

    void loadCommonLength();

    void loadCommonSuffix();

    void loadDomain(String suffix);

    void loadTotalPage(Integer length);

    void loadTotalPage(Integer minLength, Integer maxLength);

    void loadTotalPage(String suffix);

    void loadTotalPage(Integer minLength, Integer maxLength, String suffix);

    Integer loadAndGetTotal(Integer currentPage, Integer pageSize, Integer minLength, Integer maxLength, String suffix);

    List<DomainDO> queryList(DomainQuery domainQuery);

    Integer count();

    List<DomainDO> queryDomainList(Integer pageNo, Integer pageSize, String suffix, DomainStatus domainStatus, Integer price, Integer prefixLength);

    void updateStatus(String domain, DomainStatus domainStatus);
}
