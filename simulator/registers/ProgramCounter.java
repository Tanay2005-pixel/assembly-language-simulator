package simulator.registers;

public class ProgramCounter {
    private int value;
    public ProgramCounter() { 
        this.value = 0; 
    }

    public int get(){ 
        return value; 
    }
    public void set(int address){ 
        value = address & 0xFFFF; 
    }
    public void increment(){ 
        value = (value + 1) & 0xFFFF; 
    }
    public void jump(int address) { 
        value = address & 0xFFFF; 
    }

    @Override
    public String toString() { 
        return String.format("PC=%04X", value); 
    }
}
