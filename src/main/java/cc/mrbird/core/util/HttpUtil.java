package cc.mrbird.core.util;

import cc.mrbird.core.http.entity.HttpResult;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author leo on 22/11/2017.
 */
public class HttpUtil {
    public static HttpResult convert(HttpResponse httpResponse) throws IOException {
        if (httpResponse == null) {
            return null;
        }
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(httpResponse.getStatusLine().getStatusCode());
        httpResult.setContent(EntityUtils.toString(httpResponse.getEntity()));
        return httpResult;
    }
}
