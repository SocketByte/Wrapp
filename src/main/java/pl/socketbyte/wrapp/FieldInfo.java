package pl.socketbyte.wrapp;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldInfo implements Serializable {

    private final transient Field field;
    private final transient WrapperFactory wrapperFactory;
    private final transient String name;
    private final transient boolean accessible;
    private final transient int modifiers;
    private transient boolean serializable;

    private final Class<?> clazz;
    private Class<?> typeClass;
    private Object type;

    public FieldInfo(WrapperFactory wrapperFactory, Field field, Class<?> clazz, Object type) {
        this.wrapperFactory = wrapperFactory;
        this.field = field;
        this.type = type;
        this.typeClass = field.getType();
        this.name = field.getName();
        this.clazz = clazz;
        this.modifiers = field.getModifiers();
        this.accessible = field.isAccessible();
        determine();
    }

    private void determine() {
        if (Modifier.isTransient(modifiers)
                || Modifier.isStatic(modifiers)
                || Modifier.isNative(modifiers)) {
            return;
        }
        if (!FieldDetector.isStandard(field.getType(), type))
            type = wrapperFactory.write(type);
        this.serializable = true;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    public Class<?> getOriginalClass() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public Object getType() {
        return type;
    }

    public int getModifiers() {
        return modifiers;
    }

    public boolean isSerializable() {
        return serializable;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "clazz=" + clazz +
                ", type=" + type +
                '}';
    }
}
