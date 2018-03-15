package com.annie.baselibrary.utils.NetUtils.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * Created by Mark.Han on 2017/8/22.
 */

public class JsonArrayRequest extends RestRequest<JSONArray> {

    public JsonArrayRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public JsonArrayRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public JSONArray parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String response = StringRequest.parseResponseString(responseHeaders, responseBody);

        return JSON.parseArray(response);
    }
}
