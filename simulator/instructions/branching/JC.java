package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// JC addr  →  if Carry flag set, PC = addr
public class JC implements Instruction {
    private final int address;
    public JC(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        if (cpu.flags.carry) cpu.pc.jump(address);
    }
}
