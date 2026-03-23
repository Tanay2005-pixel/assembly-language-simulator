package simulator.instructions.arithmetic;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// DIV reg  →  A = A / reg, B = A % reg  (extended instruction - not in original 8085)
public class DIV implements Instruction {
    private final String register;
    public DIV(String register) { this.register = register; }

    @Override
    public void execute(CPU cpu) {
        int val = register.equals("M")
                ? cpu.memory.read(cpu.registers.getHL())
                : cpu.registers.get(register);
        if (val == 0) {
            System.out.println("[ERROR] DIV: Division by zero!");
            return;
        }
        int a         = cpu.registers.get("A");
        int quotient  = a / val;
        int remainder = a % val;
        cpu.registers.set("A", quotient);
        cpu.registers.set("B", remainder);
        cpu.flags.updateFlags(quotient);
    }
}
