package simulator.memory;
public class Memory {
    private static final int size = 65536;
    private int[] memory;
    public Memory() {
        memory = new int[size];
    }
    public int read(int adrs) {
        validate(adrs);
        return memory[adrs] & 0xFF;
    }
    public void write(int adrs, int value) {
        validate(adrs);
        memory[adrs] = value & 0xFF;
    }
    public void loadProgram(int[] program, int startAddress) {
        for (int i = 0; i < program.length; i++) {
            memory[startAddress + i] = program[i] & 0xFF;
        }
    }
    private void validate(int adrs) {
        if (adrs < 0 || adrs >= size)
            throw new IllegalArgumentException("Memory adrs out of range: " + adrs);
    }
    public void reset() { memory = new int[size]; }
    public void dump(int from, int to) {
        System.out.println("\nMemory Dump" + String.format("%04X", from) +
                " - " + String.format("%04X", to));
        for (int i = from; i <= to; i++) {
            if ((i - from) % 8 == 0) System.out.printf("\n%04X: ", i);
            System.out.printf("%02X ", memory[i]);
        }
        System.out.println();
    }
}
