package pl.socketbyte.wrapp;

import org.junit.Test;
import pl.socketbyte.wrapp.tool.TestClass;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FieldReflectorTest {

    @Test
    public void invokeSingleTest() {
        FieldReflector reflector = new FieldReflector();

        TestClass testClass = new TestClass();
        assertEquals(testClass.test, 95);

        int value = reflector.getField(TestClass.class, testClass, "test", int.class);

        assertEquals(value, testClass.test);

        reflector.setField(TestClass.class, testClass, "test", 100);

        assertEquals(testClass.test, 100);
    }

    @Test
    public void invokeAllTest() {
        FieldReflector reflector = new FieldReflector();
        TestClass testClass = new TestClass();

        Map<Field, Object> invokedFields = reflector.getFields(TestClass.class, testClass);
        assertNotNull(invokedFields);

        assertTrue(invokedFields.containsValue(95));
        assertTrue(invokedFields.containsValue("please"));
        assertTrue(invokedFields.containsValue("really not"));
    }

    @Test
    public void fieldListingTest() {
        FieldReflector reflector = new FieldReflector();
        TestClass testClass = new TestClass();

        Map<String, Field> fieldMap = reflector.getAllFields(TestClass.class);
        assertNotNull(fieldMap);
        assertTrue(fieldMap.containsKey("test"));

        reflector.register(TestClass.class);

        assertEquals(fieldMap, reflector.getAllFields(TestClass.class));
    }

}
