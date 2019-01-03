package cc.mrbird.core.http;

import cc.mrbird.core.enums.ErrorCode;
import cc.mrbird.core.exception.BusinessException;
import cc.mrbird.core.exception.CallRemoteException;
import cc.mrbird.core.http.entity.BaseRet;
import cc.mrbird.core.http.entity.HttpMethod;
import cc.mrbird.core.http.entity.HttpResult;
import cc.mrbird.core.log.HttpInvokeLog;
import cc.mrbird.core.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author leo on 14/01/2018.
 */
@Component
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseHttpCaller {

    private static final int DEFAULT_TIME_OUT = 10000;
    protected static final int SUCCESS_CODE = 200;
    protected static final String QUESTION_MARK = "?";
    protected static final String AND_MARK = "&";

    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    private static HttpClient DEFAULT_HTTP_CLIENT;
    private static RequestConfig globalConfig;

    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();

    static {
        CONNECTION_MANAGER.setMaxTotal(300);
        CONNECTION_MANAGER.setDefaultMaxPerRoute(100);

        globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setRedirectsEnabled(false).setConnectionRequestTimeout(DEFAULT_TIME_OUT).setConnectTimeout(
            DEFAULT_TIME_OUT).setSocketTimeout(DEFAULT_TIME_OUT).build();
        DEFAULT_HTTP_CLIENT = (HttpClients.custom().setDefaultRequestConfig(globalConfig).setConnectionManager(CONNECTION_MANAGER).setRetryHandler(
            new DefaultHttpRequestRetryHandler(0, false)).build());
    }

    public <R> R doGet(String uri, Map<String, String> param) {
        return doGet(uri, param, null, false);
    }

    public <T, R> R doGet(String uri, Map<String, String> param, TypeReference<T> typeReference, boolean parseData) {
        return doGet(uri, param, DEFAULT_TIME_OUT, typeReference, parseData);
    }

    public <T, R> R doGet(String uri, Map<String, String> param, Integer timeout, TypeReference<T> typeReference, boolean parseData) {
        return execute(uri, param, HttpMethod.GET, timeout, typeReference, parseData);
    }

    private <T, R> R execute(String uri, Map<String, String> param, HttpMethod callMethod, Integer timeout, TypeReference<T> typeReference, boolean parseData) {
        HttpResult httpResult = null;
        try {
            httpResult = doExecute(uri, param, callMethod, timeout);
            String response = checkAndGetContent(httpResult);
            //noinspection unchecked
            return checkAndParseData(response, typeReference, parseData);
        } catch (Exception e) {
            throw new CallRemoteException(e, ErrorCode.INTERNAL_ERROR,
                "executor http call exception, msg: %s, call context : [uri:%s, params:%s, callMethod:%s, timeout:%s, returnType:%s, response:%s]", e.getMessage(), uri,
                JSON.toJSONString(param), callMethod, timeout, JSON.toJSONString(typeReference), JSON.toJSONString(httpResult));
        }
    }

    private HttpResult doExecute(String uri, Map<String, String> param, HttpMethod httpMethod, Integer timeout) {
        JSONObject invokeJson = new JSONObject();
        Date date = new Date();
        HttpResult httpResult;
        String url = getHostDomain() + uri;
        try {
            Assert.isTrue(timeout != null && timeout > 0, "Invalid TimeOut");
            Assert.notNull(httpMethod, "Invalid Http Method");
            Assert.notNull(httpMethod, "http method must not be null");
            url = buildUrl(url, param);
            HttpResponse httpResponse;
            if (httpMethod == HttpMethod.GET) {
                HttpGet request = new HttpGet(url);
                buildHttpRequest(request);
                httpResponse = getHttpExecutor().execute(request);
            } else {
                HttpPost post = new HttpPost(url);
                buildPostEntity(post, param);
                buildHttpRequest(post);
                httpResponse = getHttpExecutor().execute(post);
            }
            httpResult = HttpUtil.convert(httpResponse);
            //invokeJson.put("result", JSON.toJSONString(httpResult));
            return httpResult;
        } catch (Exception e) {
            invokeJson.put("result", "call api error. errorMsg :" + e.getMessage());
            throw new CallRemoteException(e, ErrorCode.INTERNAL_ERROR, "executor http call exception, msg:%s, call context:%s", e.getMessage(), invokeJson);
        } finally {
            invokeJson.fluentPut("url", url).fluentPut("param", param).fluentPut("time", FAST_DATE_FORMAT.format(date)).fluentPut("timeCosts",
                System.currentTimeMillis() - date.getTime());
            HttpInvokeLog.info(invokeJson.toString());
        }
    }

    protected void buildHttpRequest(HttpRequestBase http) {}

    protected void buildPostEntity(HttpPost post, Map<String, String> param) throws UnsupportedEncodingException {
        if (MapUtils.isEmpty(param)) {
            return;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Entry<String, String> entry : param.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    }

    protected String checkAndGetContent(HttpResult httpResult) {
        int statusCode = httpResult.getCode();
        String response = httpResult.getContent();
        if (statusCode != SUCCESS_CODE) {
            throw new CallRemoteException(ErrorCode.INTERNAL_ERROR, "remote server exception, http code:%s, response:%s", statusCode, response);
        }
        return response;
    }

    protected String buildUrl(String url, Map<String, String> param) {
        Assert.hasText(url, "invalid url");
        if (MapUtils.isEmpty(param)) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (!url.contains(QUESTION_MARK)) {
            sb.append(QUESTION_MARK);
        } else if (!url.endsWith(AND_MARK)) {
            sb.append(AND_MARK);
        }
        for (Entry<String, String> entry : param.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * Override this method if you want to parse the data
     *
     * @param response      response of the http
     * @param typeReference json parse type
     * @param parseData     whether parse data or not
     * @param <T>           json parse type
     * @param <R>           return type
     * @return R
     */
    protected <T, R> R checkAndParseData(String response, TypeReference<T> typeReference, boolean parseData) {
        try {
            Assert.hasText(response, "response is empty.");
            if (typeReference == null) {
                //noinspection unchecked
                return (R)response;
            }
            //noinspection unchecked
            T t = JSON.parseObject(response, typeReference);
            if (t == null) {
                return null;
            }
            Assert.isTrue(BaseRet.class.isAssignableFrom(t.getClass()), "Parse data failed, only type of BaseRet can be parsed. class:" + t.getClass());
            BaseRet ret = (BaseRet)t;
            modifyResult(ret);
            if (!parseData) {
                //noinspection unchecked
                return (R)t;
            }
            checkTheCode(ret);
            Object data = ret.getData();
            if (data == null) {
                return null;
            }
            //noinspection unchecked
            return (R)data;
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCode.INTERNAL_ERROR, "response:%s", response);
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected void checkTheCode(BaseRet ret) {
        String code = ret.getCode();
        Assert.hasText(code, "code of response does not exist. response:" + JSON.toJSONString(ret));
        //We can skip some error code, and return it to users.
        Assert.isTrue(StringUtils.equals(code, String.valueOf(SUCCESS_CODE)), "the code of data is not success. response:" + JSON.toJSONString(ret));
    }

    @SuppressWarnings("WeakerAccess")
    protected void modifyResult(BaseRet baseRet) {
        Assert.notNull(baseRet, "baseRet must not be null");
    }

    /**
     * Get domain
     *
     * @return domain
     */
    public abstract String getHostDomain();

    /**
     * @return httpClient
     */
    protected HttpClient getHttpExecutor() {
        return DEFAULT_HTTP_CLIENT;
    }

    public static RequestConfig getGlobalConfig() {
        return globalConfig;
    }

}

