package simulator.instructions.logical;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// CMA  →  A = ~A  (complement accumulator, no flags affected)
public class CMA implements Instruction {
    @Override
    public void execute(CPU cpu) {
        cpu.registers.set("A", (~cpu.registers.get("A")) & 0xFF);
    }
}
