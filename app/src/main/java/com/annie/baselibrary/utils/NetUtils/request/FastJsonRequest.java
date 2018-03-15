package com.annie.baselibrary.utils.NetUtils.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * Created by Mark.Han on 2017/8/15.
 */

public class FastJsonRequest extends RestRequest<JSONObject> {


    public FastJsonRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public FastJsonRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        setAccept(Headers.HEAD_VALUE_CONTENT_TYPE_JSON);
    }

    @Override
    public void onPreExecute() {
        // TODO 这个方法会在真正请求前被调用，在这里可以做一些加密之类的工作。这个方法在子线程被调用。
    }

    /**
     * 解析服务器响应数据为你的泛型T，这里也就是JSONObject。
     *
     * @param responseHeaders 服务器响应头。
     * @param responseBody    服务器响应包体。
     * @return 返回你的泛型对象。
     */
    @Override
    public JSONObject parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String result = StringRequest.parseResponseString(responseHeaders, responseBody);
        return JSON.parseObject(result);
    }
}
