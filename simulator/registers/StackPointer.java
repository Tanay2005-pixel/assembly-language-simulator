package simulator.registers;

public class StackPointer {
    private int value;

    public StackPointer() { this.value = 0xFFFF; }

    public int get()            { return value; }
    public void set(int addr)   { value = addr & 0xFFFF; }
    public void increment()     { value = (value + 1) & 0xFFFF; }
    public void decrement()     { value = (value - 1) & 0xFFFF; }

    @Override
    public String toString() { return String.format("SP=%04X", value); }
}
