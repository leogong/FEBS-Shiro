package cc.mrbird.core.domain.entity;


import cc.mrbird.core.entity.BaseQuery;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author leo on 14/01/2018.
 */
public class DomainQuery extends BaseQuery {
    private String domain;
    private Integer price;
    private Integer productType;
    private Integer status;
    private Date endTimeFrom;
    private String suffix;
    private Integer prefixLength;

    public Integer getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getEndTimeFrom() {
        return endTimeFrom;
    }

    public void setEndTimeFrom(Date endTimeFrom) {
        this.endTimeFrom = endTimeFrom;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
