package pl.socketbyte.wrapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class FieldDetectorTest {

    @Test
    public void fieldDetectorTest() {
        assertTrue(FieldDetector.isStandard(Integer.class, 5));
        assertTrue(FieldDetector.isStandard(Double.class, 8.2));
        assertTrue(FieldDetector.isStandard(Short.class, (short)3));
        assertFalse(FieldDetector.isStandard(Void.class, void.class));
        assertTrue(FieldDetector.isStandard(Object.class, new Object()));
        assertTrue(FieldDetector.isStandard(int.class, 723));
        assertTrue(FieldDetector.isStandard(float.class, 0.968287f));
        assertTrue(FieldDetector.isStandard(long.class, 5272572458245929L));
        assertTrue(FieldDetector.isStandard(String.class, "str"));
    }

}
