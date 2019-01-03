package cc.mrbird.core.domain.manager.db.impl;

import cc.mrbird.core.domain.entity.DomainDO;
import cc.mrbird.core.domain.entity.DomainQuery;
import cc.mrbird.core.domain.entity.DomainStatus;
import cc.mrbird.core.domain.manager.db.DomainManager;
import cc.mrbird.system.dao.DomainMapper;
import cc.mrbird.core.validator.CommonValidator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author leo on 16/01/2018.
 */
@Component
public class DomainManagerImpl implements DomainManager {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DomainMapper domainMapper;

    @Autowired
    private CommonValidator commonValidator;

    @Override
    public void insert(DomainDO domain) {
        domainMapper.insert(domain);
    }

    @Override
    public void insert(String domain, Integer price, Integer productType, Date endTime, DomainStatus domainStatus, Integer prefixLength, Integer suffixLength) {
        commonValidator.notNull(domain, "invalid domain");
        commonValidator.notNull(price, "invalid price");
        commonValidator.notNull(productType, "invalid productType");
        commonValidator.notNull(endTime, "invalid endTime");
        commonValidator.notNull(domainStatus, "invalid domainStatus");
        commonValidator.notNull(prefixLength, "invalid prefixLength");
        commonValidator.notNull(suffixLength, "invalid suffixLength");
        DomainDO domainDO = new DomainDO();
        domainDO.setDomain(domain);
        domainDO.setPrice(price);
        domainDO.setProductType(productType);
        domainDO.setEndTime(endTime);
        Date now = new Date();
        domainDO.setGmtCreate(now);
        domainDO.setGmtModified(now);
        domainDO.setStatus(domainStatus.getCode());
        domainDO.setPrefixLength(prefixLength);
        domainDO.setSuffixLength(suffixLength);
        insert(domainDO);
    }

    @Override
    public void update(DomainDO domain) {
        domainMapper.update(domain);
    }

    @Override
    public void update(String domain, Integer price, Integer productType, Date endTime) {
        commonValidator.notNull(domain, "invalid domain");
        commonValidator.notNull(price, "invalid price");
        commonValidator.notNull(productType, "invalid productType");
        commonValidator.notNull(endTime, "invalid endTime");
        DomainDO domainDO = new DomainDO();
        domainDO.setDomain(domain);
        domainDO.setPrice(price);
        domainDO.setProductType(productType);
        domainDO.setEndTime(endTime);
        update(domainDO);
    }

    @Override
    public void update(String domain, Integer price, Integer productType, Date endTime, DomainStatus domainStatus) {
        commonValidator.notNull(domain, "invalid domain");
        commonValidator.notNull(price, "invalid price");
        commonValidator.notNull(productType, "invalid productType");
        commonValidator.notNull(endTime, "invalid endTime");
        commonValidator.notNull(domainStatus, "invalid domainStatus");
        DomainDO domainDO = new DomainDO();
        domainDO.setDomain(domain);
        domainDO.setPrice(price);
        domainDO.setProductType(productType);
        domainDO.setStatus(domainStatus.getCode());
        domainDO.setEndTime(endTime);
        update(domainDO);
    }

    @Override
    public void insertOrUpdate(String domain, Integer price, Integer productType, Date endTime, DomainStatus domainStatus) {
        commonValidator.notNull(domain, "invalid domain");
        commonValidator.notNull(productType, "invalid productType");
        commonValidator.notNull(endTime, "invalid endTime");
        commonValidator.notNull(price, "invalid price");
        select(domain);
        if (select(domain) != null) {
            update(domain, price, productType, endTime, domainStatus);
        } else {
            int i = domain.indexOf(".");
            commonValidator.gtZero(i, ". does not exist. i:%s", i);
            insert(domain, price, productType, endTime, domainStatus, i, domain.length() - (i + 1));
        }
    }

    @Override
    public List<DomainDO> select(DomainQuery domainQuery) {
        if (domainQuery.getPageNo() != null) {
            domainQuery.setOffset((domainQuery.getPageNo() - 1) * domainQuery.getPageSize());
        }
        return domainMapper.select(domainQuery);
    }

    @Override
    public DomainDO select(String domain) {
        commonValidator.notNull(domain, "invalid domain");
        DomainQuery domainQuery = new DomainQuery();
        domainQuery.setDomain(domain);
        List<DomainDO> domains = select(domainQuery);
        return CollectionUtils.isEmpty(domains) ? null : domains.get(0);
    }

    @Override
    public Integer count(DomainQuery domainQuery) {
        return domainMapper.count(domainQuery);
    }

    @Override
    public Integer count(String domain) {
        commonValidator.notNull(domain, "invalid domain");
        DomainQuery domainDO = new DomainQuery();
        domainDO.setDomain(domain);
        return count(domainDO);
    }
}
