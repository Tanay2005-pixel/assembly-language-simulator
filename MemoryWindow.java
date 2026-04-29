import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import simulator.cpu.CPU;

public class MemoryWindow extends JFrame {
    
    private JTextField addressField;
    private JTextField valueField;
    private JPanel memoryPanel;
    private SimulatorGUI mainWindow;
    private CPU cpu;
    private int currentAddress;
    
    public MemoryWindow(SimulatorGUI main) {
        this.mainWindow = main;
        this.cpu = SimulatorState.getCPU();
        this.currentAddress = 0;
        
        setTitle("Memory Viewer & Editor");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(60, 63, 65));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JLabel titleLabel = new JLabel("Memory Operations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        
        add(topPanel, BorderLayout.NORTH);
        
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(70, 73, 75));
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel addrLabel = new JLabel("Address (hex):");
        addrLabel.setForeground(Color.WHITE);
        addrLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(addrLabel, gbc);
        
        addressField = new JTextField(10);
        addressField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gbc.gridx = 1;
        controlPanel.add(addressField, gbc);
        
        JButton showButton = new JButton("Show");
        showButton.setBackground(new Color(75, 110, 175));
        showButton.setForeground(Color.WHITE);
        showButton.setFocusPainted(false);
        gbc.gridx = 2;
        controlPanel.add(showButton, gbc);
        
        JLabel valueLabel = new JLabel("New Value (hex):");
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(valueLabel, gbc);
        
        valueField = new JTextField(10);
        valueField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gbc.gridx = 1;
        controlPanel.add(valueField, gbc);
        
        JButton writeButton = new JButton("Write");
        writeButton.setBackground(new Color(100, 150, 100));
        writeButton.setForeground(Color.WHITE);
        writeButton.setFocusPainted(false);
        gbc.gridx = 2;
        controlPanel.add(writeButton, gbc);
        
        add(controlPanel, BorderLayout.WEST);
        
        memoryPanel = new JPanel();
        memoryPanel.setLayout(new BoxLayout(memoryPanel, BoxLayout.Y_AXIS));
        memoryPanel.setBackground(new Color(43, 43, 43));
        
        JScrollPane scrollPane = new JScrollPane(memoryPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(60, 63, 65));
        bottomPanel.setLayout(new FlowLayout());
        
        JButton clearButton = new JButton("Clear Display");
        clearButton.setBackground(new Color(120, 120, 120));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        
        JButton backButton = new JButton("Back (ESC)");
        backButton.setBackground(new Color(150, 80, 80));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        
        bottomPanel.add(clearButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        showButton.addActionListener(e -> showMemory());
        writeButton.addActionListener(e -> writeMemory());
        clearButton.addActionListener(e -> {
            memoryPanel.removeAll();
            memoryPanel.revalidate();
            memoryPanel.repaint();
        });
        backButton.addActionListener(e -> returnToMain());
        
        KeyAdapter keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    showMemory();
                    currentAddress++;
                    addressField.setText(String.format("%04X", currentAddress));
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    returnToMain();
                }
            }
        };
        
        addressField.addKeyListener(keyListener);
        valueField.addKeyListener(keyListener);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                returnToMain();
            }
        });
    }
    
    private void showMemory() {
        String addressText = addressField.getText().trim();
        
        try {
            currentAddress = Integer.parseInt(addressText, 16);
            int value = cpu.memory.read(currentAddress);
            
            JPanel entryPanel = new JPanel();
            entryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            entryPanel.setBackground(new Color(50, 50, 50));
            entryPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
            entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            
            JLabel addrLabel = new JLabel(String.format("Address: %04X", currentAddress));
            addrLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
            addrLabel.setForeground(new Color(100, 200, 255));
            addrLabel.setPreferredSize(new Dimension(150, 25));
            
            JLabel valueLabel = new JLabel(String.format("Value: %02X (%d)", value, value));
            valueLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
            valueLabel.setForeground(new Color(150, 255, 150));
            
            entryPanel.add(addrLabel);
            entryPanel.add(valueLabel);
            
            memoryPanel.add(entryPanel);
            memoryPanel.revalidate();
            memoryPanel.repaint();
            
            JScrollBar vertical = ((JScrollPane)memoryPanel.getParent().getParent()).getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid hex address!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error reading memory: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void writeMemory() {
        String addressText = addressField.getText().trim();
        String valueText = valueField.getText().trim();
        
        try {
            int address = Integer.parseInt(addressText, 16);
            int value = Integer.parseInt(valueText, 16);
            
            cpu.memory.write(address, value);
            
            JOptionPane.showMessageDialog(this, 
                String.format("Written %02X to address %04X", value, address),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            valueField.setText("");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid hex value!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error writing memory: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void returnToMain() {
        mainWindow.setVisible(true);
        dispose();
    }
}
