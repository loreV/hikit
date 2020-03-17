package org.ltrails.common;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonUtil {
    private static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
