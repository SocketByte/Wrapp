package pl.socketbyte.wrapp.tool;

public class TestClass {

    private transient String doNotSerialize = "really not";
    private String serializeMe = "please";
    public int test = 95;
    public final TestClass1 testClass1 = new TestClass1("Something");

    public TestClass() {

    }

    public TestClass(boolean different) {
        this.doNotSerialize = "do not, please";
        this.serializeMe = "please please please";
        this.test = 10492;
        this.testClass1.change();
    }

}
