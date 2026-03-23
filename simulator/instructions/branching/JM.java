package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// JM addr  →  if Sign flag set (result negative), PC = addr
public class JM implements Instruction {
    private final int address;
    public JM(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        if (cpu.flags.sign) cpu.pc.jump(address);
    }
}
