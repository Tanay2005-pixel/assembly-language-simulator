package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// JZ addr  →  if Zero flag set, PC = addr
public class JZ implements Instruction {
    private final int address;
    public JZ(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        if (cpu.flags.zero) cpu.pc.jump(address);
    }
}
