package simulator.instructions.datatransfer;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// MVI reg, data  →  reg = data (immediate)
public class MVI implements Instruction {
    private final String register;
    private final int    data;

    public MVI(String register, int data) {
        this.register = register;
        this.data     = data & 0xFF;
    }

    @Override
    public void execute(CPU cpu) {
        if (register.equals("M")) {
            cpu.memory.write(cpu.registers.getHL(), data);
        } else {
            cpu.registers.set(register, data);
        }
    }
}
