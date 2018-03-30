package pl.socketbyte.wrapp.tool;

public class TestClass1 {

    private String testString;
    private TestClass2 testClass2 = new TestClass2();

    public TestClass1(String testString) {
        this.testString = testString;
    }

    public TestClass2 getTestClass2() {
        return testClass2;
    }

    public String getTestString() {
        return testString;
    }

    public void change() {
        this.testString = "something, not null";
        this.testClass2 = new TestClass2(true);
    }

}
