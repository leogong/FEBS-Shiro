package cc.mrbird.core.domain.manager.remote;

import cc.mrbird.core.domain.entity.DomainDO;
import cc.mrbird.core.entity.PageList;

/**
 * @author leo on 14/01/2018.
 */
public interface DomainRemoteManager {
    Object query(Integer currentPage, Integer pageSize, Integer minLength, Integer maxLength, String suffix);

    PageList<DomainDO> queryList(Integer currentPage, Integer pageSize, Integer minLength, Integer maxLength, String suffix);

    PageList<DomainDO> queryList(Integer currentPage, Integer pageSize, Integer minLength, Integer maxLength);

    PageList<DomainDO> queryList(Integer currentPage, Integer pageSize, String suffix);

    PageList<DomainDO> queryList(Integer currentPage, Integer pageSize);
}
