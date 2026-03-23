package simulator.instructions.datatransfer;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// XCHG  →  HL <-> DE
public class XCHG implements Instruction {
    @Override
    public void execute(CPU cpu) {
        int hl = cpu.registers.getHL();
        int de = cpu.registers.getDE();
        cpu.registers.setHL(de);
        cpu.registers.setDE(hl);
    }
}
