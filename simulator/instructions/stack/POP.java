package simulator.instructions.stack;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// POP rp  →  pop register pair from stack
public class POP implements Instruction {
    private final String pair;
    public POP(String pair) { this.pair = pair.toUpperCase(); }

    @Override
    public void execute(CPU cpu) {
        int low  = cpu.memory.read(cpu.sp.get()); cpu.sp.increment();
        int high = cpu.memory.read(cpu.sp.get()); cpu.sp.increment();

        switch (pair) {
            case "B":
                cpu.registers.set("B", high);
                cpu.registers.set("C", low);
                break;
            case "D":
                cpu.registers.set("D", high);
                cpu.registers.set("E", low);
                break;
            case "H":
                cpu.registers.set("H", high);
                cpu.registers.set("L", low);
                break;
            case "PSW":
                cpu.registers.set("A", high);
                cpu.flags.sign          = (low & 0x80) != 0;
                cpu.flags.zero          = (low & 0x40) != 0;
                cpu.flags.auxiliaryCarry = (low & 0x10) != 0;
                cpu.flags.parity        = (low & 0x04) != 0;
                cpu.flags.carry         = (low & 0x01) != 0;
                break;
            default:
                throw new IllegalArgumentException("POP: unknown pair " + pair);
        }
    }
}
