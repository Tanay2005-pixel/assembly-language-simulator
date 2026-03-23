package simulator.instructions.logical;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// RRC  →  rotate A right, bit 0 goes to carry and bit 7
public class RRC implements Instruction {
    @Override
    public void execute(CPU cpu) {
        int a = cpu.registers.get("A");
        cpu.flags.carry = (a & 0x01) != 0;
        cpu.registers.set("A", ((a >> 1) | (cpu.flags.carry ? 0x80 : 0)) & 0xFF);
    }
}
