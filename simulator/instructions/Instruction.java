package simulator.instructions;

import simulator.cpu.CPU;

public interface Instruction {
    void execute(CPU cpu);
}
