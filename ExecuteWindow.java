import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import simulator.cpu.CPU;
import simulator.executor.InstExecutor;
import simulator.instructions.Instruction;
import simulator.parser.InstructionParser;

public class ExecuteWindow extends JFrame {
    
    private JTextField startAddressField;
    private JTextArea outputArea;
    private SimulatorGUI mainWindow;
    private CPU cpu;
    
    public ExecuteWindow(SimulatorGUI main) {
        this.mainWindow = main;
        this.cpu = SimulatorState.getCPU();
        
        setTitle("Execute Code");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(60, 63, 65));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        
        JLabel titleLabel = new JLabel("Code Execution");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);
        
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(70, 73, 75));
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel label = new JLabel("Starting Address (hex):");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        
        startAddressField = new JTextField(10);
        int savedAddr = CodeWindow.getSavedStartingAddress();
        startAddressField.setText(String.format("%04X", savedAddr));
        startAddressField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        JButton executeButton = new JButton("Execute");
        executeButton.setBackground(new Color(100, 180, 100));
        executeButton.setForeground(Color.WHITE);
        executeButton.setFont(new Font("Arial", Font.BOLD, 12));
        executeButton.setFocusPainted(false);
        
        controlPanel.add(label);
        controlPanel.add(startAddressField);
        controlPanel.add(executeButton);
        
        JPanel combinedTopPanel = new JPanel();
        combinedTopPanel.setLayout(new BorderLayout());
        combinedTopPanel.add(topPanel, BorderLayout.NORTH);
        combinedTopPanel.add(controlPanel, BorderLayout.CENTER);
        
        add(combinedTopPanel, BorderLayout.NORTH);
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputArea.setBackground(new Color(43, 43, 43));
        outputArea.setForeground(new Color(200, 200, 200));
        outputArea.setText("Ready to execute code.\nPress Execute to run the last saved program.\n");
        
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100)), 
            "Execution Log", 
            0, 0, 
            new Font("Arial", Font.BOLD, 12), 
            Color.WHITE));
        
        add(outputScroll, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(60, 63, 65));
        bottomPanel.setLayout(new FlowLayout());
        
        JButton backButton = new JButton("Back (ESC)");
        backButton.setBackground(new Color(150, 80, 80));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);
        
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        executeButton.addActionListener(e -> executeCode());
        backButton.addActionListener(e -> returnToMain());
        
        startAddressField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    executeCode();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    returnToMain();
                }
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                returnToMain();
            }
        });
    }
    
    private void executeCode() {
        String code = CodeWindow.getSavedCode();
        
        if (code == null || code.trim().isEmpty()) {
            outputArea.setText("ERROR: No code found!\nPlease write code first (Press A from main menu).\n");
            return;
        }
        
        try {
            int startAddress = Integer.parseInt(startAddressField.getText().trim(), 16);
            
            outputArea.setText("=== Starting Execution ===\n");
            outputArea.append("Parsing assembly code...\n");
            
            InstructionParser parser = new InstructionParser();
            List<Instruction> instructions = parser.parse(code);
            
            if (instructions.isEmpty()) {
                outputArea.append("\nERROR: No valid instructions found!\n");
                return;
            }
            
            outputArea.append("Found " + instructions.size() + " instruction(s).\n");
            outputArea.append("Starting address: " + String.format("%04X", startAddress) + "\n\n");
            
            cpu.softReset();
            cpu.programCounter.set(startAddress);
            
            outputArea.append("Executing...\n");
            
            InstExecutor executor = new InstExecutor(cpu, false);
            executor.run(instructions);
            
            outputArea.append("\n=== Execution Complete ===\n");
            outputArea.append("Status: " + (cpu.halted ? "HALTED" : "STOPPED") + "\n");
            outputArea.append("Final PC: " + String.format("%04X", cpu.programCounter.get()) + "\n");
            outputArea.append("\nFinal Register State:\n");
            outputArea.append(cpu.registers.toString() + "\n");
            outputArea.append(cpu.flags.toString() + "\n");
            outputArea.append("PC: " + String.format("%04X", cpu.programCounter.get()) + "\n");
            outputArea.append("SP: " + String.format("%04X", cpu.stackPointer.get()) + "\n");
            outputArea.append("Halted: " + cpu.halted + "\n");
            
        } catch (NumberFormatException ex) {
            outputArea.setText("ERROR: Invalid starting address!\nPlease enter a valid hexadecimal value.\n");
        } catch (Exception ex) {
            outputArea.append("\nERROR during execution:\n" + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }
    
    private void returnToMain() {
        mainWindow.setVisible(true);
        dispose();
    }
}
