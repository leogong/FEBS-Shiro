package cc.mrbird.core.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author leo on 08/11/2017.
 */
public class PageList<T> implements Serializable {
    private List<T> list;
    private Integer total;

    public PageList() {}

    public PageList(List<T> list, Integer total) {
        this.list = list;
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
