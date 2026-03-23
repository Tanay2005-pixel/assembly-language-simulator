package simulator.instructions.datatransfer;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// MOV dst, src  →  dst = src
public class MOV implements Instruction {
    private final String dst;
    private final String src;

    public MOV(String dst, String src) {
        this.dst = dst;
        this.src = src;
    }

    @Override
    public void execute(CPU cpu) {
        // M means memory at address HL
        int value;
        if (src.equals("M")) {
            value = cpu.memory.read(cpu.registers.getHL());
        } else {
            value = cpu.registers.get(src);
        }
        if (dst.equals("M")) {
            cpu.memory.write(cpu.registers.getHL(), value);
        } else {
            cpu.registers.set(dst, value);
        }
    }
}
