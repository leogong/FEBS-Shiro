package cc.mrbird.core.domain.service.impl;

import cc.mrbird.core.domain.entity.DomainDO;
import cc.mrbird.core.domain.entity.DomainQuery;
import cc.mrbird.core.domain.entity.DomainStatus;
import cc.mrbird.core.domain.manager.db.DomainManager;
import cc.mrbird.core.domain.manager.remote.DomainRemoteManager;
import cc.mrbird.core.domain.service.BuyRuleService;
import cc.mrbird.core.domain.service.DomainMgrService;
import cc.mrbird.core.entity.Direction;
import cc.mrbird.core.entity.PageList;
import cc.mrbird.core.enums.ErrorCode;
import cc.mrbird.core.exception.BusinessException;
import cc.mrbird.core.validator.CommonValidator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author leo on 14/01/2018.
 */
@Component
public class DomainMgrServiceImpl implements DomainMgrService {
    private static final Integer PAGE_SIZE = 500;
    public static final List<String> SUFFIX_LIST = Arrays.asList("com", "cn", "net", "top", "cc", "biz");
    @Autowired
    private DomainRemoteManager domainRemoteManager;
    @Autowired
    private BuyRuleService buyRuleService;
    @Autowired
    private DomainManager domainManager;
    @Autowired
    private CommonValidator commonValidator;

    private static final Integer MAX_DOMAIN_LENGTH = 5;

    @Override
    public void loadDomain() {
        loadCommonLength();
        loadCommonSuffix();
    }

    @Override
    public void loadCommonLength() {
        for (int i = 1; i <= MAX_DOMAIN_LENGTH; i++) {
            loadTotalPage(i, i);
        }
    }

    @Override
    public void loadCommonSuffix() {
        for (String suffix : SUFFIX_LIST) {
            loadDomain(suffix);
        }
    }

    @Override
    public void loadDomain(String suffix) {
        for (int i = 1; i <= MAX_DOMAIN_LENGTH; i++) {
            loadTotalPage(i, i, suffix);
        }
    }

    @Override
    public void loadTotalPage(Integer length) {
        loadTotalPage(length, length);
    }

    @Override
    public void loadTotalPage(Integer minLength, Integer maxLength) {
        loadTotalPage(minLength, maxLength, null);
    }

    @Override
    public void loadTotalPage(String suffix) {
        loadTotalPage(null, null, suffix);
    }

    @Override
    public void loadTotalPage(Integer minLength, Integer maxLength, String suffix) {
        int currentPage = 1;
        do {
            Integer total = loadAndGetTotal(currentPage, PAGE_SIZE, minLength, maxLength, suffix);
            if (total <= 0) {
                break;
            }
            if ((currentPage * PAGE_SIZE) >= total) {
                break;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new BusinessException(e, ErrorCode.INTERNAL_ERROR, "minLength:%s, maxLength:%s, suffix:%s", minLength, maxLength, suffix);
            }
            ++currentPage;
        } while (true);
    }

    @Override
    public Integer loadAndGetTotal(Integer currentPage, Integer pageSize, Integer minLength, Integer maxLength, String suffix) {
        PageList<DomainDO> domainPageList = domainRemoteManager.queryList(currentPage, pageSize, minLength, maxLength, suffix);
        Integer total = domainPageList.getTotal();
        if (total == null || total == 0) {
            return 0;
        }
        List<DomainDO> list = domainPageList.getList();
        Integer listSize = CollectionUtils.isEmpty(list) ? 0 : list.size();
        commonValidator.isFalse(currentPage == 1 && total > listSize && listSize < pageSize, "pageSize does not work. listSize:%s, currentPage:%s, pageSize:%s", listSize,
            currentPage, pageSize);
        if (listSize <= 0) {
            return 0;
        }
        list.forEach(domain -> domainManager.insertOrUpdate(domain.getDomain(), domain.getPrice(), domain.getProductType(), domain.getEndTime(),
            buyRuleService.getBuyIntention(domain.getDomain(), domain.getPrice())));
        return total;
    }

    @Override
    public List<DomainDO> queryList(DomainQuery domainQuery) {
        return domainManager.select(domainQuery);
    }

    @Override
    public Integer count() {
        return domainManager.count(new DomainQuery());
    }

    @Override
    public List<DomainDO> queryDomainList(Integer pageNo, Integer pageSize, String suffix, DomainStatus domainStatus, Integer price, Integer prefixLength) {
        commonValidator.notNull(pageNo, "invalid pageNo:%s", pageNo);
        commonValidator.notNull(pageSize, "invalid pageSize:%s", pageSize);
        DomainQuery domainQuery = new DomainQuery();
        domainQuery.setOrderByColumn("price,prefix_length");
        domainQuery.setDirection(Direction.ASC);
        domainQuery.setPageNo(pageNo);
        domainQuery.setPageSize(pageSize);
        domainQuery.setEndTimeFrom(new Date());
        domainQuery.setSuffix(suffix);
        domainQuery.setPrice(price);
        domainQuery.setPrefixLength(prefixLength);
        domainQuery.setStatus(domainStatus == null ? null : domainStatus.getCode());
        return queryList(domainQuery);
    }

    @Override
    public void updateStatus(String domain, DomainStatus domainStatus) {
        commonValidator.notNull(domain, "domain must not be null");
        commonValidator.notNull(domainStatus, "domainStatus must not be null");
        DomainDO oldDomain = domainManager.select(domain);
        commonValidator.notNull(oldDomain, "domain does not exist. domain:", domain);
        if (oldDomain.getStatus().equals(domainStatus.getCode())) {
            return;
        }
        DomainDO domainDO = new DomainDO();
        domainDO.setDomain(domain);
        domainDO.setStatus(domainStatus.getCode());
        domainManager.update(domainDO);
    }
}
