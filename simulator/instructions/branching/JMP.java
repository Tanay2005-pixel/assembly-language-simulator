package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// JMP addr  →  PC = addr (unconditional)
public class JMP implements Instruction {
    private final int address;
    public JMP(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        cpu.pc.jump(address);
    }
}
