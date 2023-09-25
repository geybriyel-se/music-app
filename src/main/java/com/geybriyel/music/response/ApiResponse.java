package com.geybriyel.music.response;

import java.util.LinkedHashMap;

public class ApiResponse<T> extends LinkedHashMap<String, Object> {

    public ApiResponse(Integer status, T body) {
        this.put("status", status);
        this.put("body", body);
    }

    public int getStatus() {
        return (int) this.get("status");
    }

    public T getBody() {
        return (T) this.get("body");
    }

}
