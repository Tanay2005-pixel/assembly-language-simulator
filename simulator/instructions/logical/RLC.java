package simulator.instructions.logical;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// RLC  →  rotate A left, bit 7 goes to carry and bit 0
public class RLC implements Instruction {
    @Override
    public void execute(CPU cpu) {
        int a = cpu.registers.get("A");
        cpu.flags.carry = (a & 0x80) != 0;
        cpu.registers.set("A", ((a << 1) | (cpu.flags.carry ? 1 : 0)) & 0xFF);
    }
}
