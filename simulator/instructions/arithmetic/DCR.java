package simulator.instructions.arithmetic;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// DCR reg  ->  reg = reg - 1  (carry NOT affected)
public class DCR implements Instruction {
    private final String register;
    public DCR(String register) { this.register = register; }

    @Override
    public void execute(CPU cpu) {
        boolean savedCarry = cpu.flags.carry;
        if (register.equals("M")) {
            int addr = cpu.registers.getHL();
            int result = cpu.memory.read(addr) - 1;
            cpu.flags.updateFlags(result);
            cpu.memory.write(addr, result);
        } else {
            int result = cpu.registers.get(register) - 1;
            cpu.flags.updateFlags(result);
            cpu.registers.set(register, result);
        }
        cpu.flags.carry = savedCarry; // DCR does not affect carry
    }
}
