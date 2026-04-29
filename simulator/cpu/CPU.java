package simulator.cpu;

import simulator.memory.Memory;
import simulator.registers.*;

public class CPU {
    public Registers registers;
    public Memory memory;
    public Flags flags;
    public ProgramCounter programCounter;
    public StackPointer stackPointer;
    public boolean halted;

    public CPU() {
        registers = new Registers();
        memory    = new Memory();
        flags     = new Flags();
        programCounter        = new ProgramCounter();
        stackPointer        = new StackPointer();
        halted    = false;
    }

    public void reset() {
        registers.reset();
        memory.reset();
        flags.reset();
        programCounter.set(0);
        stackPointer.set(0xFFFF);
        halted = false;
    }
    
    public void softReset() {
        registers.reset();
        flags.reset();
        programCounter.set(0);
        stackPointer.set(0xFFFF);
        halted = false;
    }

    public void printState() {
        System.out.println("Registers: " + registers);
        System.out.println("Flags:     " + flags);
        System.out.println("" + programCounter + "  " + stackPointer);
        System.out.println("Halted:    " + halted);
    }
}
