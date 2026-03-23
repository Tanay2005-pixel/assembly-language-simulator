package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// JNZ addr  →  if Zero flag NOT set, PC = addr
public class JNZ implements Instruction {
    private final int address;
    public JNZ(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        if (!cpu.flags.zero) cpu.pc.jump(address);
    }
}
