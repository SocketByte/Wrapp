package pl.socketbyte.wrapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Wrapper<T> implements Serializable {

    private Class<? extends T> original;
    private Map<String, FieldInfo> fields = new HashMap<>();

    public Wrapper(Class<? extends T> original) {
        this.original = original;
    }

    public Class<? extends T> getOriginalClass() {
        return original;
    }

    public Map<String, FieldInfo> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "fields=" + fields +
                '}';
    }
}
