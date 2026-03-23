package simulator.instructions.arithmetic;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// MUL reg  →  A = A * reg  (extended instruction - not in original 8085)
// Result stored: low byte in A, high byte in B
public class MUL implements Instruction {
    private final String register;
    public MUL(String register) { this.register = register; }

    @Override
    public void execute(CPU cpu) {
        int val = register.equals("M")
                ? cpu.memory.read(cpu.registers.getHL())
                : cpu.registers.get(register);
        int result = cpu.registers.get("A") * val;
        cpu.registers.set("A", result & 0xFF);       // low byte → A
        cpu.registers.set("B", (result >> 8) & 0xFF); // high byte → B
        cpu.flags.updateFlags(result & 0xFF);
    }
}
