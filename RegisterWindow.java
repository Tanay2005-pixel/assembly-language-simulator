import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import simulator.cpu.CPU;

public class RegisterWindow extends JFrame {
    
    private SimulatorGUI mainWindow;
    private CPU cpu;
    private JPanel registerPanel;
    
    public RegisterWindow(SimulatorGUI main) {
        this.mainWindow = main;
        this.cpu = SimulatorState.getCPU();
        
        setTitle("Register Status");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(60, 63, 65));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        
        JLabel titleLabel = new JLabel("8085 Register Status");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        
        add(topPanel, BorderLayout.NORTH);
        
        registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(new Color(43, 43, 43));
        
        JScrollPane scrollPane = new JScrollPane(registerPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(60, 63, 65));
        bottomPanel.setLayout(new FlowLayout());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setBackground(new Color(75, 110, 175));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        
        JButton backButton = new JButton("Back (ESC)");
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setBackground(new Color(150, 80, 80));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        
        bottomPanel.add(refreshButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        refreshButton.addActionListener(e -> updateDisplay());
        backButton.addActionListener(e -> returnToMain());
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    returnToMain();
                }
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                returnToMain();
            }
        });
        
        updateDisplay();
        setFocusable(true);
    }
    
    private void updateDisplay() {
        registerPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        
        int row = 0;
        
        JLabel singleRegLabel = createSectionLabel("Single Registers");
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 4;
        registerPanel.add(singleRegLabel, gbc);
        
        gbc.gridwidth = 1;
        String[] registers = {"A", "B", "C", "D", "E", "H", "L"};
        int col = 0;
        for (String reg : registers) {
            if (col == 4) {
                col = 0;
                row++;
            }
            gbc.gridx = col++;
            gbc.gridy = row;
            registerPanel.add(createRegisterBox(reg, cpu.registers.get(reg)), gbc);
        }
        
        row += 2;
        JLabel pairRegLabel = createSectionLabel("Register Pairs");
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 4;
        registerPanel.add(pairRegLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        registerPanel.add(createRegisterPairBox("BC", cpu.registers.getBC()), gbc);
        gbc.gridx = 1;
        registerPanel.add(createRegisterPairBox("DE", cpu.registers.getDE()), gbc);
        gbc.gridx = 2;
        registerPanel.add(createRegisterPairBox("HL", cpu.registers.getHL()), gbc);
        
        row += 2;
        JLabel specialRegLabel = createSectionLabel("Special Registers");
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 4;
        registerPanel.add(specialRegLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        registerPanel.add(createRegisterPairBox("PC", cpu.programCounter.get()), gbc);
        gbc.gridx = 1;
        registerPanel.add(createRegisterPairBox("SP", cpu.stackPointer.get()), gbc);
        
        row += 2;
        JLabel flagLabel = createSectionLabel("Flags");
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 4;
        registerPanel.add(flagLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        registerPanel.add(createFlagBox("Zero", cpu.flags.z), gbc);
        gbc.gridx = 1;
        registerPanel.add(createFlagBox("Sign", cpu.flags.s), gbc);
        gbc.gridx = 2;
        registerPanel.add(createFlagBox("Parity", cpu.flags.p), gbc);
        
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        registerPanel.add(createFlagBox("Carry", cpu.flags.cy), gbc);
        gbc.gridx = 1;
        registerPanel.add(createFlagBox("AuxCarry", cpu.flags.acy), gbc);
        
        row += 2;
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(cpu.halted ? new Color(200, 100, 100) : new Color(100, 200, 100));
        statusPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        statusPanel.setPreferredSize(new Dimension(200, 50));
        JLabel statusLabel = new JLabel("CPU: " + (cpu.halted ? "HALTED" : "RUNNING"));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        statusPanel.add(statusLabel);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 4;
        registerPanel.add(statusPanel, gbc);
        
        registerPanel.revalidate();
        registerPanel.repaint();
    }
    
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(255, 200, 100));
        return label;
    }
    
    private JPanel createRegisterBox(String name, int value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(70, 73, 75));
        panel.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 200), 2));
        panel.setPreferredSize(new Dimension(150, 80));
        
        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(new Color(100, 200, 255));
        
        JLabel valueLabel = new JLabel(String.format("%02X (%d)", value, value), SwingConstants.CENTER);
        valueLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        valueLabel.setForeground(Color.WHITE);
        
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createRegisterPairBox(String name, int value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(70, 73, 75));
        panel.setBorder(BorderFactory.createLineBorder(new Color(150, 100, 200), 2));
        panel.setPreferredSize(new Dimension(180, 80));
        
        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(new Color(200, 150, 255));
        
        JLabel valueLabel = new JLabel(String.format("%04X (%d)", value, value), SwingConstants.CENTER);
        valueLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        valueLabel.setForeground(Color.WHITE);
        
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFlagBox(String name, boolean value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(value ? new Color(100, 180, 100) : new Color(180, 100, 100));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panel.setPreferredSize(new Dimension(150, 70));
        
        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        
        JLabel valueLabel = new JLabel(value ? "1" : "0", SwingConstants.CENTER);
        valueLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        valueLabel.setForeground(Color.WHITE);
        
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void returnToMain() {
        mainWindow.setVisible(true);
        dispose();
    }
}
