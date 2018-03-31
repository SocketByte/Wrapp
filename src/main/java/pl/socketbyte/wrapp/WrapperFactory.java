package pl.socketbyte.wrapp;

import java.lang.reflect.Field;
import java.util.Map;

public class WrapperFactory {

    private final FieldReflector reflector;

    public WrapperFactory() {
        this.reflector = new FieldReflector();
    }

    public void register(Class<?> clazz) {
        reflector.register(clazz);
    }

    public FieldReflector getReflector() {
        return reflector;
    }

    @SuppressWarnings("unchecked")
    public <T> Wrapper<T> write(T instance) {
        Class<? extends T> clazz = (Class<? extends T>) instance.getClass();
        Wrapper<T> wrapper = new Wrapper<>(clazz);
        Map<Field, Object> declaredFields = reflector.getFields(clazz, instance);

        for (Map.Entry<Field, Object> entry : declaredFields.entrySet()) {
            Field field = entry.getKey();
            Object value = entry.getValue();

            FieldInfo info = new FieldInfo(this, field, field.getDeclaringClass(), value);
            if (!info.isSerializable())
                continue;

            wrapper.getFields().put(field.getName(), info);
        }

        return wrapper;
    }

    @SuppressWarnings("unchecked")
    public <T> T read(Wrapper<T> wrapper, T instance) {
        Class<? extends T> original = wrapper.getOriginalClass();
        for (Map.Entry<String, FieldInfo> entry : wrapper.getFields().entrySet()) {
            String fieldName = entry.getKey();
            FieldInfo info = entry.getValue();

            if (info.getType().getClass().isAssignableFrom(Wrapper.class)) {
                Wrapper deepWrapper = (Wrapper) info.getType();
                Map<Field, Object> fields = reflector.getFields(instance.getClass(), instance);
                Object deepInstance = null;
                for (Map.Entry<Field, Object> field : fields.entrySet()) {
                    if (deepWrapper.getOriginalClass()
                            .isAssignableFrom(field.getKey().getType()))
                        deepInstance = field.getValue();
                }
                read(deepWrapper, deepInstance);
                continue;
            }
            reflector.setField(original, instance, fieldName, info.getType());
        }
        return instance;
    }

}
