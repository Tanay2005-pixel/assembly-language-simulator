package simulator.executor;
import java.util.List;
import simulator.cpu.CPU;
import simulator.instructions.Instruction;

public class InstExecutor {

    private final CPU cpu;
    private boolean stepMode;

    public InstExecutor(CPU cpu, boolean stepMode) {
        this.cpu      = cpu;
        this.stepMode = stepMode;
    }
    public void run(List<Instruction> instructions) {
        if (instructions.isEmpty()) {
            System.out.println("No instructions to run.");
            return;
        }
        cpu.programCounter.set(0);
        cpu.halted = false;
        int maxCycles = 100_000;
        int cycles    = 0;
        System.out.println("8085 Simulator — Execution Start");
        while (!cpu.halted && cycles < maxCycles) {
            int i = cpu.programCounter.get();
            if (i >= instructions.size()) {
                System.out.println("Reached end of program (no HLT found).");
                break;
            }
            Instruction instr = instructions.get(i);
            cpu.programCounter.increment();
            instr.execute(cpu);

            if (stepMode) {
                System.out.printf("Step %4d  programCounter→%04X  ", cycles + 1, cpu.programCounter.get());
                System.out.println(cpu.registers + "  " + cpu.flags);
            }

            cycles++;
        }

        if (cycles >= maxCycles) {
            System.out.println("Maximum cycle limit reached — possible infinite loop.");
        }
        System.out.println("    Execution Complete");
        System.out.printf ("Total instructions executed: %5d%n", cycles);
        cpu.printState();
    }

    public boolean step(List<Instruction> instructions) {
        if (cpu.halted) { System.out.println("CPU is halteded."); return false; }
        int i = cpu.programCounter.get();
        if (i >= instructions.size()) { System.out.println("End of program."); return false; }
        Instruction instr = instructions.get(i);
        cpu.programCounter.increment();
        instr.execute(cpu);
        cpu.printState();
        return !cpu.halted;
    }
    public void setStepMode(boolean stepMode){   
        this.stepMode = stepMode; 
    }
}
