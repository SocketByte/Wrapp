package pl.socketbyte.wrapp.tool;

public class TestClass3 extends AbstractTestClass {

    private int otherNumber = 100;

    public TestClass3() {

    }

    public TestClass3(boolean different) {
        this.otherNumber = 200;
        this.setAbstractNumber(850);
    }

    public int getOtherNumber() {
        return otherNumber;
    }

}
