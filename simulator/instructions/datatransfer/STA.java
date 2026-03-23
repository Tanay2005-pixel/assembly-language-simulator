package simulator.instructions.datatransfer;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// STA addr  →  memory[addr] = A
public class STA implements Instruction {
    private final int address;

    public STA(int address) { this.address = address; }

    @Override
    public void execute(CPU cpu) {
        cpu.memory.write(address, cpu.registers.get("A"));
    }
}
