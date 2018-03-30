package pl.socketbyte.wrapp.tool;

public class TestClass2 {

    private int number = 666;
    private transient final double value = 32.823;

    public TestClass2() {

    }

    public int getNumber() {
        return number;
    }

    public TestClass2(boolean different) {
        this.number = 1666;
    }
}
