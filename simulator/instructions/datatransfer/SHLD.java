package simulator.instructions.datatransfer;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// SHLD addr  →  mem[addr] = L, mem[addr+1] = H
public class SHLD implements Instruction {
    private final int address;
    public SHLD(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        cpu.memory.write(address,     cpu.registers.get("L"));
        cpu.memory.write(address + 1, cpu.registers.get("H"));
    }
}
