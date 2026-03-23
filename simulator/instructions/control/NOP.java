package simulator.instructions.control;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// NOP  →  no operation
public class NOP implements Instruction {
    @Override
    public void execute(CPU cpu) {
        // intentionally empty
    }
}
