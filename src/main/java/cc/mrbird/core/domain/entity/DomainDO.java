package cc.mrbird.core.domain.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author leo on 14/01/2018.
 */
public class DomainDO {
    private Integer id;
    private Date gmtCreate;
    private Date gmtModified;
    private String domain;
    private Integer price;
    private Integer productType;
    private Date endTime;
    private Integer status;
    private Integer prefixLength;
    private Integer suffixLength;

    public Integer getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
    }

    public Integer getSuffixLength() {
        return suffixLength;
    }

    public void setSuffixLength(Integer suffixLength) {
        this.suffixLength = suffixLength;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    @Override
    public String toString() {
        return "DomainDO{" + "domain='" + domain + '\'' + ", price=" + price + ", productType=" + productType + ", endTime=" + endTime + '}';
    }
}
