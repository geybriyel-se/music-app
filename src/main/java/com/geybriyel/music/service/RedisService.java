package com.geybriyel.music.service;

public interface RedisService {

    public void setValue(String key, Object value);

    public Object getValue(String key);

    public boolean keyExists(String key);

    public void deleteKey(String key);
}
