package cc.mrbird.core.domain.manager.remote.impl;

import cc.mrbird.core.domain.entity.DomainDO;
import cc.mrbird.core.domain.manager.remote.DomainRemoteManager;
import cc.mrbird.core.entity.PageList;
import cc.mrbird.core.enums.ErrorCode;
import cc.mrbird.core.exception.BusinessException;
import cc.mrbird.core.http.DomainHttpCaller;
import cc.mrbird.core.http.entity.BaseRet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author leo on 14/01/2018.
 */
@Service
public class DomainRemoteManagerImpl implements DomainRemoteManager {
    @Autowired
    private DomainHttpCaller domainHttpCaller;

    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    private static final String PAGE_RESULT = "pageResult";
    private static final String TOTAL_ITEM_NUM = "totalItemNum";
    private static final String DATA = "data";
    private static final String PRODUCT_TYPE = "productType";
    private static final String DOMAIN_NAME = "domainName";
    private static final String PRICE = "price";
    private static final String END_TIME = "endTime";

    @Override
    public Object query(Integer currentPage, Integer pageSize, Integer minLength, Integer maxLength, String suffix) {
        HashMap<String, String> param = new HashMap<>(2);
        Assert.notNull(currentPage, "invalid currentPage");
        Assert.notNull(pageSize, "invalid pageSize");
        param.put("currentPage", String.valueOf(currentPage));
        param.put("pageSize", String.valueOf(pageSize));
        if (minLength != null) {
            param.put("minLength", String.valueOf(minLength));
        }
        if (maxLength != null) {
            param.put("maxLength", String.valueOf(maxLength));
        }
        if (StringUtils.isNotEmpty(suffix)) {
            param.put("suffix", suffix);
        }
        return domainHttpCaller.doGet("", param, new TypeReference<BaseRet<Object>>() {}, true);
    }

    @Override
    public PageList<DomainDO> queryList(Integer currentPage, Integer pageSize, Integer minLength, Integer maxLength, String suffix) {
        JSONObject jsonObject = (JSONObject)query(currentPage, pageSize, minLength, maxLength, suffix);
        PageList<DomainDO> pageList = new PageList<>(null, 0);
        JSONObject pageResult = jsonObject.getJSONObject(PAGE_RESULT);
        if (pageResult == null) {
            return pageList;
        }
        Integer total = pageResult.getInteger(TOTAL_ITEM_NUM);
        pageList.setTotal(total);
        if (total <= 0) {
            return pageList;
        }
        JSONArray jsonArray = pageResult.getJSONArray(DATA);
        if (jsonArray == null || jsonArray.size() <= 0) {
            return new PageList<>(null, total);
        }
        List<DomainDO> domainList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject domainObject = jsonArray.getJSONObject(i);
            DomainDO domainDO = new DomainDO();
            domainDO.setDomain(domainObject.getString(DOMAIN_NAME));
            domainDO.setProductType(domainObject.getInteger(PRODUCT_TYPE));
            domainDO.setPrice(domainObject.getInteger(PRICE));
            if (domainDO.getPrice() == null) {
                domainDO.setPrice(0);
            }
            String dateStr = domainObject.getString(END_TIME);
            if ("长期".equals(dateStr)) {
                domainDO.setEndTime(DateUtils.addDays(new Date(), 10));
            } else {
                try {
                    domainDO.setEndTime(FAST_DATE_FORMAT.parse(dateStr));
                } catch (ParseException e) {
                    throw new BusinessException(e, ErrorCode.INTERNAL_ERROR, "date format error.dateStr:%s", dateStr);
                }
            }
            domainList.add(domainDO);
        }
        pageList.setList(domainList);
        return pageList;
    }

    @Override
    public PageList<DomainDO> queryList(Integer currentPage, Integer pageSize, Integer minLength, Integer maxLength) {
        return queryList(currentPage, pageSize, minLength, maxLength, null);
    }

    @Override
    public PageList<DomainDO> queryList(Integer currentPage, Integer pageSize, String suffix) {
        return queryList(currentPage, pageSize, null, null, suffix);
    }

    @Override
    public PageList<DomainDO> queryList(Integer currentPage, Integer pageSize) {
        return queryList(currentPage, pageSize, null, null, null);
    }

}
