package cc.mrbird.core.http.entity;

/**
 * @author leo on 22/11/2017.
 */
public class HttpResult {
    private Integer code;
    private String content;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
