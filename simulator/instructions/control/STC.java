package simulator.instructions.control;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// STC  →  set carry flag to 1
public class STC implements Instruction {
    @Override
    public void execute(CPU cpu) {
        cpu.flags.carry = true;
    }
}
