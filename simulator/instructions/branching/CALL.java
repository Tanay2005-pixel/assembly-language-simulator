package simulator.instructions.branching;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// CALL addr  →  push PC onto stack, PC = addr
public class CALL implements Instruction {
    private final int address;
    public CALL(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        int returnAddr = cpu.pc.get();
        // push high byte then low byte
        cpu.sp.decrement();
        cpu.memory.write(cpu.sp.get(), (returnAddr >> 8) & 0xFF);
        cpu.sp.decrement();
        cpu.memory.write(cpu.sp.get(), returnAddr & 0xFF);
        cpu.pc.jump(address);
    }
}
