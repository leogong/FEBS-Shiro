package cc.mrbird.core.domain.service;

import cc.mrbird.core.domain.entity.DomainStatus;

/**
 * @author leo on 18/01/2018.
 */
public interface BuyRuleService {

    DomainStatus getBuyIntention(String domain, Integer price);
}
