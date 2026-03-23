package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// JNC addr  →  if Carry flag NOT set, PC = addr
public class JNC implements Instruction {
    private final int address;
    public JNC(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        if (!cpu.flags.carry) cpu.pc.jump(address);
    }
}
