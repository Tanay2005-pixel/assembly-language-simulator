package simulator.instructions.arithmetic;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// ADD reg  →  A = A + reg
public class ADD implements Instruction {
    private final String register;
    public ADD(String register) { this.register = register; }

    @Override
    public void execute(CPU cpu) {
        int val = register.equals("M")
                ? cpu.memory.read(cpu.registers.getHL())
                : cpu.registers.get(register);
        int result = cpu.registers.get("A") + val;
        cpu.flags.updateFlags(result);
        cpu.registers.set("A", result);
    }
}
