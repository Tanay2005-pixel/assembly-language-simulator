package simulator.instructions.stack;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// PUSH rp  →  push register pair onto stack
public class PUSH implements Instruction {
    private final String pair;
    public PUSH(String pair) { this.pair = pair.toUpperCase(); }

    @Override
    public void execute(CPU cpu) {
        int high, low;
        switch (pair) {
            case "B":
                high = cpu.registers.get("B");
                low  = cpu.registers.get("C");
                break;
            case "D":
                high = cpu.registers.get("D");
                low  = cpu.registers.get("E");
                break;
            case "H":
                high = cpu.registers.get("H");
                low  = cpu.registers.get("L");
                break;
            case "PSW":
                high = cpu.registers.get("A");
                // encode flags into a byte
                low  = (cpu.flags.sign         ? 0x80 : 0)
                     | (cpu.flags.zero         ? 0x40 : 0)
                     | (cpu.flags.auxiliaryCarry ? 0x10 : 0)
                     | (cpu.flags.parity       ? 0x04 : 0)
                     | (cpu.flags.carry        ? 0x01 : 0);
                break;
            default:
                throw new IllegalArgumentException("PUSH: unknown pair " + pair);
        }
        cpu.sp.decrement();
        cpu.memory.write(cpu.sp.get(), high);
        cpu.sp.decrement();
        cpu.memory.write(cpu.sp.get(), low);
    }
}
