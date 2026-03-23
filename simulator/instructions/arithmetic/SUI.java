package simulator.instructions.arithmetic;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// SUI data  →  A = A - data (immediate)
public class SUI implements Instruction {
    private final int data;
    public SUI(int data) { this.data = data & 0xFF; }

    @Override
    public void execute(CPU cpu) {
        int result = cpu.registers.get("A") - data;
        cpu.flags.updateFlags(result);
        cpu.registers.set("A", result);
    }
}
