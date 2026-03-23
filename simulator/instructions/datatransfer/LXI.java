package simulator.instructions.datatransfer;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// LXI rp, data16  →  register pair = 16-bit immediate
public class LXI implements Instruction {
    private final String pair;
    private final int    data;

    public LXI(String pair, int data) {
        this.pair = pair.toUpperCase();
        this.data = data & 0xFFFF;
    }

    @Override
    public void execute(CPU cpu) {
        switch (pair) {
            case "B": cpu.registers.setBC(data); break;
            case "D": cpu.registers.setDE(data); break;
            case "H": cpu.registers.setHL(data); break;
            case "SP": cpu.sp.set(data); break;
            default: throw new IllegalArgumentException("LXI: unknown pair " + pair);
        }
    }
}
