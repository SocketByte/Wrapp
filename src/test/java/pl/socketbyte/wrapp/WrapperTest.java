package pl.socketbyte.wrapp;

import org.junit.Test;
import pl.socketbyte.wrapp.tool.Serializer;
import pl.socketbyte.wrapp.tool.TestClass;
import pl.socketbyte.wrapp.tool.TestClass2;
import pl.socketbyte.wrapp.tool.TestClass3;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WrapperTest {

    @SuppressWarnings("unchecked")
    @Test
    public void serializeTest() throws IOException {
        WrapperFactory wrapperFactory = new WrapperFactory();
        wrapperFactory.register(TestClass.class);
        wrapperFactory.register(TestClass2.class);
        wrapperFactory.register(TestClass3.class);

        TestClass testClass = new TestClass();
        Wrapper<TestClass> wrapper = wrapperFactory.write(testClass);

        String serialized = Serializer.serialize(wrapper);
        assertNotNull(serialized);

        Wrapper<TestClass> deserialized = (Wrapper<TestClass>) Serializer.deserialize(serialized);
        assertEquals(deserialized.toString(), wrapper.toString());
    }
}
