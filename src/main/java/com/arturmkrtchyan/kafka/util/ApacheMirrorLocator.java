package com.arturmkrtchyan.kafka.util;


import com.eclipsesource.json.JsonObject;
import com.github.kevinsawicki.http.HttpRequest;

public class ApacheMirrorLocator {

    private static final String APACHE_URL = "https://www.apache.org/dyn/closer.cgi?as_json=1";


    public String locate() {
        String response = HttpRequest.get(APACHE_URL).body();
        JsonObject json = JsonObject.readFrom(response);
        return json.getString("preferred", null);
    }
}
