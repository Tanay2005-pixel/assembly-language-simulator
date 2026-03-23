package simulator.instructions.stack;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// XTHL  →  exchange HL with top of stack
public class XTHL implements Instruction {
    @Override
    public void execute(CPU cpu) {
        int stackLow  = cpu.memory.read(cpu.sp.get());
        int stackHigh = cpu.memory.read(cpu.sp.get() + 1);
        cpu.memory.write(cpu.sp.get(),     cpu.registers.get("L"));
        cpu.memory.write(cpu.sp.get() + 1, cpu.registers.get("H"));
        cpu.registers.set("L", stackLow);
        cpu.registers.set("H", stackHigh);
    }
}
