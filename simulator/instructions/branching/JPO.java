package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// JPO addr  →  if Parity flag NOT set (odd parity), PC = addr
public class JPO implements Instruction {
    private final int address;
    public JPO(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        if (!cpu.flags.parity) cpu.pc.jump(address);
    }
}
