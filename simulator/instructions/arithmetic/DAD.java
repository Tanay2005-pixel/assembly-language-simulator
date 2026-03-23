package simulator.instructions.arithmetic;

import simulator.cpu.CPU;
import simulator.instructions.Instruction;

// DAD rp  →  HL = HL + register pair  (only carry affected)
public class DAD implements Instruction {
    private final String pair;
    public DAD(String pair) { this.pair = pair.toUpperCase(); }

    @Override
    public void execute(CPU cpu) {
        int hl  = cpu.registers.getHL();
        int val;
        switch (pair) {
            case "B":  val = cpu.registers.getBC(); break;
            case "D":  val = cpu.registers.getDE(); break;
            case "H":  val = cpu.registers.getHL(); break;
            case "SP": val = cpu.sp.get();          break;
            default: throw new IllegalArgumentException("DAD: unknown pair " + pair);
        }
        int result = hl + val;
        cpu.flags.carry = (result > 0xFFFF);
        cpu.registers.setHL(result & 0xFFFF);
    }
}
