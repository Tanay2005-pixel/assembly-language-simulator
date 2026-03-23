package simulator.instructions.control;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// HLT  →  halt the processor
public class HLT implements Instruction {
    @Override
    public void execute(CPU cpu) {
        cpu.halted = true;
        System.out.println("[HLT] Processor halted.");
    }
}
