package pl.socketbyte.wrapp;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.*;

public class FieldReflector {

    private final MethodHandles.Lookup lookup;
    private final Map<Class<?>, Map<String, Field>> fieldMap = new LinkedHashMap<>();

    public FieldReflector() {
        this.lookup = MethodHandles.lookup();
    }

    public void register(Class<?> clazz) {
        fieldMap.put(clazz, getAllFields(clazz));
    }

    public Map<Class<?>, Map<String, Field>> getFieldMap() {
        return Collections.unmodifiableMap(fieldMap);
    }

    private <C> Map<Field, Object> invokeFields(Map<String, Field> fields, Class<? extends C> clazz, C instance) {
        Map<Field, Object> map = new LinkedHashMap<>();

        for (Field field : fields.values()) {
            field.setAccessible(true);
            Object value = getField(
                    clazz,
                    instance,
                    field.getName(),
                    field.getType());

            map.put(field, value);
        }

        return Collections.unmodifiableMap(map);
    }

    public <C> Map<Field, Object> getFields(Class<? extends C> clazz, C instance) {
        return invokeFields(getAllFields(clazz), clazz, instance);
    }

    public <C> Map<String, Field> getAllFields(Class<? extends C> clazz) {
        return fieldMap.containsKey(clazz) ? fieldMap.get(clazz) : getAllFields(new LinkedHashMap<>(), clazz);
    }

    private Map<String, Field> getAllFields(Map<String, Field> fields, Class<?> type) {
        for (Field field : type.getDeclaredFields())
            fields.put(field.getName(), field);

        if (type.getSuperclass() != null)
            getAllFields(fields, type.getSuperclass());

        return fields;
    }

    @SuppressWarnings("unchecked")
    public <C, T> T getField(Class<? extends C> clazz, C instance, String name, Class<? extends T> type) {
        try {
            Field field = getAllFields(clazz).get(name);
            field.setAccessible(true);
            MethodHandle unreflected = lookup.unreflectGetter(field);
            return (T) unreflected.invoke(instance);
        } catch (Throwable e) {
            throw new InternalError("Error occured during invoking a field", e);
        }
    }

    public <C, T> void setField(Class<? extends C> clazz, C instance, String name, T value) {
        try {
            Field field = getAllFields(clazz).get(name);
            field.setAccessible(true);
            MethodHandle unreflected = lookup.unreflectSetter(field);
            unreflected.invokeWithArguments(instance, value);
        } catch (Throwable e) {
            throw new InternalError("Error occured during invoking a field", e);
        }
    }

}
