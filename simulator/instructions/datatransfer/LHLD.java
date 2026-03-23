package simulator.instructions.datatransfer;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// LHLD addr  →  L = mem[addr], H = mem[addr+1]
public class LHLD implements Instruction {
    private final int address;
    public LHLD(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        cpu.registers.set("L", cpu.memory.read(address));
        cpu.registers.set("H", cpu.memory.read(address + 1));
    }
}
