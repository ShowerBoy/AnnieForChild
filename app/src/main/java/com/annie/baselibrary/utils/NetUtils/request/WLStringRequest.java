package com.annie.baselibrary.utils.NetUtils.request;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * Created by wanglei on 2018/10/23.
 */

public class WLStringRequest extends StringRequest {

    public WLStringRequest(String url) {
        super(url);
    }

    public WLStringRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public String parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String result = StringRequest.parseResponseString(responseHeaders, responseBody);
        return result;
    }
}
