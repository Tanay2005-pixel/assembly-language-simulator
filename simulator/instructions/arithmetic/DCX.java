package simulator.instructions.arithmetic;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// DCX rp  →  register pair -= 1  (no flags affected)
public class DCX implements Instruction {
    private final String pair;
    public DCX(String pair) { this.pair = pair.toUpperCase(); }

    @Override
    public void execute(CPU cpu) {
        switch (pair) {
            case "B":  cpu.registers.setBC((cpu.registers.getBC() - 1) & 0xFFFF); break;
            case "D":  cpu.registers.setDE((cpu.registers.getDE() - 1) & 0xFFFF); break;
            case "H":  cpu.registers.setHL((cpu.registers.getHL() - 1) & 0xFFFF); break;
            case "SP": cpu.stackPointer.set((cpu.stackPointer.get() - 1) & 0xFFFF); break;
            default: throw new IllegalArgumentException("DCX: unknown pair " + pair);
        }
    }
}
