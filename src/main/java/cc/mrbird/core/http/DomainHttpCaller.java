package cc.mrbird.core.http;

import com.alibaba.fastjson.TypeReference;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leo on 14/01/2018.
 */
@Component
public class DomainHttpCaller extends BaseHttpCaller {
    @Value("${net.cn.mi.domain}")
    private String miDomain;

    private final static Pattern JSONP_PATTERN = Pattern.compile("[A-Za-z0-9_]+\\((.*)\\)[;]?");

    @Override
    public String getHostDomain() {
        return miDomain;
    }

    @Override
    protected <T, R> R checkAndParseData(String response, TypeReference<T> typeReference, boolean parseData) {
        Matcher matcher = JSONP_PATTERN.matcher(response);
        Assert.isTrue(matcher.find(), "pattern doest not match.");
        return super.checkAndParseData(matcher.group(1), typeReference, parseData);
    }

    @Override
    protected void buildHttpRequest(HttpRequestBase http) {
        http.addHeader("Referer", "https://mi.aliyun.com/");
        http.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3320.0 Safari/537.36");
        http.addHeader("Accept", "*/*");
        http.addHeader("Accept-Encoding", "gzip, deflate, br");
        http.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7,da;q=0.6,zh-TW;q=0.5,ja;q=0.4,ru;q=0.3");
        http.addHeader("Cache-Control", "no-cache");
        http.addHeader("Dnt", "1");
        http.addHeader("Pragma", "no-cache");
        http.addHeader("Content-Type", "application/javascript;charset=UTF-8");

        BasicCookieStore cookieStore = new BasicCookieStore();
        RequestConfig localConfig = RequestConfig.copy(getGlobalConfig()).setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        BasicClientCookie cookie = new BasicClientCookie("aliyun_choice", "CN");
        cookie.setDomain("");
        cookie.setPath("");
        cookieStore.addCookie(cookie);
        cookie = new BasicClientCookie("cnz", "WAPiEg75njUCAV4veH1PI4sL");
        cookie.setDomain("");
        cookie.setPath("");
        cookieStore.addCookie(cookie);
        http.setConfig(localConfig);
    }
}
