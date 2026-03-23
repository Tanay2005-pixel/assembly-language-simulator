package simulator.instructions.datatransfer;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// LDA addr  →  A = memory[addr]
public class LDA implements Instruction {
    private final int address;

    public LDA(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        cpu.registers.set("A", cpu.memory.read(address));
    }
}
