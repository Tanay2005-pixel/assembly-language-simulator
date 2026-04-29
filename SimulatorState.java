import simulator.cpu.CPU;

public class SimulatorState {
    
    private static CPU cpu = new CPU();
    
    public static CPU getCPU() {
        return cpu;
    }
    
    public static void resetCPU() {
        cpu.reset();
    }
}
