package simulator.instructions.control;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// CMC  →  complement (toggle) carry flag
public class CMC implements Instruction {
    @Override
    public void execute(CPU cpu) {
        cpu.flags.carry = !cpu.flags.carry;
    }
}
