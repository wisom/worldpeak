package com.worldpeak.chnsmilead.net;

public class KV {
    public String key;
    public String value;

    public KV(String key, Object value) {
        this.key = key;
        this.value = value.toString();
    }
}
