import java.nio.file.*;
import java.util.*;
import simulator.cpu.CPU;
import simulator.executor.InstExecutor;
import simulator.instructions.Instruction;
import simulator.parser.InstructionParser;

public class Main {

    private static final CPU cpu = new CPU();
    private static final InstructionParser parser = new InstructionParser();
    private static       InstExecutor executor = new InstExecutor(cpu, false);
    private static List<Instruction> loadedProgram = new ArrayList<>();
    public static void main(String[] args) throws Exception {

        if (args.length > 0) {
            String filename = args[0];
            System.out.println("Loading file: " + filename);
            String code = new String(Files.readAllBytes(Paths.get(filename)));
            runProgram(code, false);
            return;
        }
        Scanner scanner = new Scanner(System.in);
        StringBuilder programBuffer = new StringBuilder();
        boolean multilineMode = false;
        while (true) {
            System.out.print(multilineMode ? "... " : ">>> ");
            if (!scanner.hasNextLine()) break;
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) continue;
            switch (line.toLowerCase()) {
                case ".begin":
                    programBuffer.setLength(0);
                    multilineMode = true;
                    System.out.println("Multi-line mode ON. Type .end to run the program.");
                    continue;
                case ".end":
                    if (!multilineMode) { System.out.println(" Not in multi-line mode."); continue; }
                    multilineMode = false;
                    runProgram(programBuffer.toString(), false);
                    programBuffer.setLength(0);
                    continue;

                case ".run":
                    if (loadedProgram.isEmpty()) { System.out.println("[WARN] No program loaded."); continue; }
                    cpu.reset();
                    executor.run(loadedProgram);
                    continue;

                case ".step":
                    if (loadedProgram.isEmpty()) { System.out.println("No program loaded."); continue; }
                    executor.step(loadedProgram);
                    continue;

                case ".state":
                    cpu.printState();
                    continue;

                case ".reset":
                    cpu.reset();
                    loadedProgram.clear();
                    System.out.println("CPU reset. Program cleared.");
                    continue;

                case ".mem": {
                    System.out.print("From address (hex): ");
                    int from = Integer.parseInt(scanner.nextLine().trim(), 16);
                    System.out.print("To address   (hex): ");
                    int to   = Integer.parseInt(scanner.nextLine().trim(), 16);
                    cpu.memory.dump(from, to);
                    continue;
                }

                case ".write": {
                    try {
                        System.out.print("Address (hex): ");
                        int addr = Integer.parseInt(scanner.nextLine().trim(), 16);
                        System.out.print("Value   (hex): ");
                        int val = Integer.parseInt(scanner.nextLine().trim(), 16);
                        cpu.memory.write(addr, val);
                        System.out.printf("Wrote %02X to %04X%n", val & 0xFF, addr);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid hex number.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                }

                case ".stepmode on":
                    executor.setStepMode(true);
                    System.out.println(" Step mode ON — CPU state printed after each instruction.");
                    continue;

                case ".stepmode off":
                    executor.setStepMode(false);
                    System.out.println(" Step mode OFF.");
                    continue;

                case ".labels":
                    System.out.println(parser.getLabelMap());
                    continue;

                case ".exit": case ".quit":
                    return;
            }
            if (multilineMode) {
                programBuffer.append(line).append("\n");
                continue;
            }

            runProgram(line, false);
        }
    }
    private static void runProgram(String code, boolean stepMode) {
        executor.setStepMode(stepMode);
        loadedProgram = parser.parse(code);
        if (loadedProgram.isEmpty()) {
            System.out.println("No valid instructions found.");
            return;
        }
        System.out.printf("Parsed %d instruction(s).", loadedProgram.size());
        executor.run(loadedProgram);
    }
}
