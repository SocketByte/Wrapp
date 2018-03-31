package pl.socketbyte.wrapp;

import org.junit.Test;
import pl.socketbyte.wrapp.tool.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import static org.junit.Assert.*;

public class WrapperFactoryTest {

    @Test
    public void superClassTest() {
        WrapperFactory wrapperFactory = new WrapperFactory();
        wrapperFactory.register(TestClass3.class);
        wrapperFactory.register(AbstractTestClass.class);

        TestClass3 testClass3 = new TestClass3();
        assertEquals(testClass3.getOtherNumber(), 100);
        assertEquals(testClass3.getAbstractNumber(), 65);
        Wrapper<TestClass3> wrapper = wrapperFactory.write(testClass3);

        assertEquals(wrapper.getOriginalClass(), TestClass3.class);
        assertTrue(wrapper.getFields().containsKey("otherNumber"));
        assertTrue(wrapper.getFields().containsKey("abstractNumber"));

        FieldInfo abstractNumberInfo = wrapper.getFields().get("abstractNumber");
        assertEquals(abstractNumberInfo.getModifiers(), Modifier.PRIVATE);
        assertEquals(abstractNumberInfo.getType(), 65);
        assertEquals(abstractNumberInfo.getOriginalClass(), AbstractTestClass.class);
        assertEquals(abstractNumberInfo.getName(), "abstractNumber");

        TestClass3 different = new TestClass3(true);
        assertEquals(different.getOtherNumber(), 200);
        assertEquals(different.getAbstractNumber(), 850);

        wrapperFactory.read(wrapper, different);

        assertEquals(different.getOtherNumber(), 100);
        assertEquals(different.getAbstractNumber(), 65);
    }

    @Test
    public void registerTest() {
        WrapperFactory wrapperFactory = new WrapperFactory();
        wrapperFactory.register(TestClass1.class);
        Map<String, Field> fieldMap = wrapperFactory.getReflector().getFieldMap().get(TestClass1.class);
        assertNotNull(fieldMap);

        assertEquals(fieldMap.get("testString").getName(), "testString");
        assertEquals(fieldMap.get("testString").getType(), String.class);
        assertEquals(fieldMap.get("testClass2").getName(), "testClass2");
        assertEquals(fieldMap.get("testClass2").getType(), TestClass2.class);
    }

    @Test
    public void writeAndReadTest() {
        WrapperFactory wrapperFactory = new WrapperFactory();
        wrapperFactory.register(TestClass.class);
        wrapperFactory.register(TestClass1.class);
        wrapperFactory.register(TestClass2.class);

        TestClass testClass = new TestClass();
        Wrapper<TestClass> wrapper = wrapperFactory.write(testClass);

        assertNotNull(wrapper);
        assertEquals(wrapper.getOriginalClass(), TestClass.class);
        assertFalse(wrapper.getFields().containsKey("doNotSerialize"));
        assertTrue(wrapper.getFields().containsKey("serializeMe"));
        assertEquals(wrapper.getFields().get("serializeMe").getName(), "serializeMe");
        assertEquals(wrapper.getFields().get("serializeMe").getModifiers(), Modifier.PRIVATE);
        assertEquals(wrapper.getFields().get("serializeMe").getOriginalClass(), TestClass.class);
        assertEquals(wrapper.getFields().get("serializeMe").getType(), "please");
        assertEquals(wrapper.getFields().get("serializeMe").getType().getClass(), String.class);

        assertTrue(wrapper.getFields().containsKey("test"));
        assertEquals(wrapper.getFields().get("test").getName(), "test");
        assertEquals(wrapper.getFields().get("test").getModifiers(), Modifier.PUBLIC);
        assertEquals(wrapper.getFields().get("test").getOriginalClass(), TestClass.class);
        assertEquals(wrapper.getFields().get("test").getType(), 95);
        assertEquals(wrapper.getFields().get("test").getType().getClass(), Integer.class);

        assertTrue(wrapper.getFields().containsKey("testClass1"));
        assertEquals(wrapper.getFields().get("testClass1").getName(), "testClass1");
        assertEquals(wrapper.getFields().get("testClass1").getModifiers(), Modifier.PUBLIC + Modifier.FINAL);
        assertEquals(wrapper.getFields().get("testClass1").getOriginalClass(), TestClass.class);
        assertEquals(wrapper.getFields().get("testClass1").getType().getClass(), Wrapper.class);

        Wrapper deep = (Wrapper) wrapper.getFields().get("testClass1").getType();
        assertNotNull(deep);

        assertEquals(deep.getOriginalClass(), TestClass1.class);

        FieldInfo testStringInfo = (FieldInfo) deep.getFields().get("testString");
        assertNotNull(testStringInfo);

        assertEquals(testStringInfo.getOriginalClass(), TestClass1.class);
        assertEquals(testStringInfo.getType().getClass(), String.class);
        assertEquals(testStringInfo.getType(), "Something");

        FieldInfo testClass2Info = (FieldInfo) deep.getFields().get("testClass2");
        assertNotNull(testClass2Info);

        assertEquals(testClass2Info.getType().getClass(), Wrapper.class);
        assertEquals(testClass2Info.getName(), "testClass2");
        assertEquals(testClass2Info.getOriginalClass(), TestClass1.class);
        assertEquals(testClass2Info.getModifiers(), Modifier.PRIVATE);

        deep = (Wrapper) ((FieldInfo) deep.getFields().get("testClass2")).getType();
        assertNotNull(deep);

        FieldInfo numberInfo = (FieldInfo) deep.getFields().get("number");
        assertNotNull(numberInfo);

        assertEquals(numberInfo.getType().getClass(), Integer.class);
        assertEquals(numberInfo.getType(), 666);
        assertEquals(numberInfo.getName(), "number");
        assertEquals(numberInfo.getOriginalClass(), TestClass2.class);
        assertEquals(numberInfo.getModifiers(), Modifier.PRIVATE);

        assertFalse(deep.getFields().containsKey("value"));

        TestClass differentClass = new TestClass(true);
        assertEquals(differentClass.test, 10492);
        assertEquals(differentClass.testClass1.getTestString(), "something, not null");
        assertNotNull(differentClass.testClass1.getTestClass2());
        assertEquals(differentClass.testClass1.getTestClass2().getNumber(), 1666);

        wrapperFactory.read(wrapper, differentClass);

        assertEquals(differentClass.test, 95);
        assertEquals(differentClass.testClass1.getTestString(), "Something");
        assertNotNull(differentClass.testClass1.getTestClass2());
        assertEquals(differentClass.testClass1.getTestClass2().getNumber(), 666);
    }

}
