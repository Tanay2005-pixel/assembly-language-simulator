package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// JP addr  →  if Sign flag NOT set (result positive), PC = addr
public class JP implements Instruction {
    private final int address;
    public JP(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        if (!cpu.flags.sign) cpu.pc.jump(address);
    }
}
