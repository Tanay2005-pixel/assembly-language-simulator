package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// JPE addr  →  if Parity flag set (even parity), PC = addr
public class JPE implements Instruction {
    private final int address;
    public JPE(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        if (cpu.flags.parity) cpu.pc.jump(address);
    }
}
