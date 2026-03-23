package simulator.instructions.logical;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// ANA reg  →  A = A & reg
public class ANA implements Instruction {
    private final String register;
    public ANA(String register) { this.register = register; }

    @Override
    public void execute(CPU cpu) {
        int val = register.equals("M")
                ? cpu.memory.read(cpu.registers.getHL())
                : cpu.registers.get(register);
        int result = cpu.registers.get("A") & val;
        cpu.flags.updateFlags(result);
        cpu.flags.carry = false;
        cpu.registers.set("A", result);
    }
}
