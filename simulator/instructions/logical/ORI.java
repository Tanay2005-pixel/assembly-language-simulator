package simulator.instructions.logical;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// ORI data  →  A = A | data (immediate)
public class ORI implements Instruction {
    private final int data;
    public ORI(int data) { this.data = data & 0xFF; }

    @Override
    public void execute(CPU cpu) {
        int result = cpu.registers.get("A") | data;
        cpu.flags.updateFlags(result);
        cpu.flags.carry = false;
        cpu.registers.set("A", result);
    }
}
