package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// RET  →  pop return address from stack, PC = addr
public class RET implements Instruction {
    @Override
    public void execute(CPU cpu) {
        int low  = cpu.memory.read(cpu.sp.get()); cpu.sp.increment();
        int high = cpu.memory.read(cpu.sp.get()); cpu.sp.increment();
        cpu.pc.jump((high << 8) | low);
    }
}
