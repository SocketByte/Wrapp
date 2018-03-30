package pl.socketbyte.wrapp;

public class FieldDetector {

    public static boolean isStandard(Class<?> type, Object instance) {
        if (type.isPrimitive())
            return true;
        Class<?> instanceClass = instance.getClass();

        return instanceClass.isAssignableFrom(Boolean.class)
                || instanceClass.isAssignableFrom(Character.class)
                || instanceClass.isAssignableFrom(Byte.class)
                || instanceClass.isAssignableFrom(Float.class)
                || instanceClass.isAssignableFrom(Double.class)
                || instanceClass.isAssignableFrom(Integer.class)
                || instanceClass.isAssignableFrom(Short.class)
                || instanceClass.isAssignableFrom(Long.class)
                || instanceClass.isAssignableFrom(String.class);
    }

}
