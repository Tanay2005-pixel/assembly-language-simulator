package simulator.instructions.logical;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// CPI data  →  A - data  (flags set, A unchanged, immediate)
public class CPI implements Instruction {
    private final int data;
    public CPI(int data) { this.data = data & 0xFF; }

    @Override
    public void execute(CPU cpu) {
        int result = cpu.registers.get("A") - data;
        cpu.flags.updateFlags(result);
    }
}
