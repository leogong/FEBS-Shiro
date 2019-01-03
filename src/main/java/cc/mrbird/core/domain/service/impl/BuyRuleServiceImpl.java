package cc.mrbird.core.domain.service.impl;

import cc.mrbird.core.domain.constants.DomainConstants;
import cc.mrbird.core.domain.entity.DomainDO;
import cc.mrbird.core.domain.entity.DomainStatus;
import cc.mrbird.core.domain.manager.db.DomainManager;
import cc.mrbird.core.domain.service.BuyRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leo on 18/01/2018.
 */
@Service
public class BuyRuleServiceImpl implements BuyRuleService {
    private final static Pattern COMMON_DOMAIN_PATTERN = Pattern.compile("^[A-Za-z0-9]+\\.\\w+$");
    @Autowired
    private DomainManager domainManager;

    @Override
    public DomainStatus getBuyIntention(String domain, Integer price) {
        Assert.hasText(domain, "invalid domain");
        if (price > 10000) {
            return DomainStatus.NO;
        }
        if (domain.split(DomainConstants.DOT).length > DomainConstants.MAX_DOT_SPLIT_SIZE) {
            return DomainStatus.NO;
        }
        Matcher matcher = COMMON_DOMAIN_PATTERN.matcher(domain);
        if (!matcher.find()) {
            return DomainStatus.NO;
        }
        if (price == 0) {
            return DomainStatus.NO;
        }
        DomainDO domainDO = domainManager.select(domain);
        if (domainDO != null) {
            DomainStatus domainStatus = DomainStatus.of(domainDO.getStatus());
            // 降价大于10%将域名重新放回waiting
            if (domainStatus == DomainStatus.EXPENSIVE) {
                Integer oldPrice = domainDO.getPrice();
                // 不变
                if (price.equals(oldPrice)) {
                    return domainStatus;
                }
                // 降价超过10%，重新修改为waiting
                if (price < oldPrice && (oldPrice.doubleValue() - price.doubleValue()) / oldPrice.doubleValue() > 0.1) {
                    return DomainStatus.WAIT;
                }
            }
            if (domainStatus != DomainStatus.WAIT) {
                return domainStatus;
            }
        }
        return DomainStatus.WAIT;
    }
}
