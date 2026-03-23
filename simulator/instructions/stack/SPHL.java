package simulator.instructions.stack;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// SPHL  →  SP = HL
public class SPHL implements Instruction {
    @Override
    public void execute(CPU cpu) {
        cpu.sp.set(cpu.registers.getHL());
    }
}
