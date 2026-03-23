package simulator.instructions.logical;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// CMP reg  →  A - reg  (flags set, A unchanged)
public class CMP implements Instruction {
    private final String register;
    public CMP(String register) { this.register = register; }

    @Override
    public void execute(CPU cpu) {
        int val = register.equals("M")
                ? cpu.memory.read(cpu.registers.getHL())
                : cpu.registers.get(register);
        int result = cpu.registers.get("A") - val;
        cpu.flags.updateFlags(result);
    }
}
