import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulatorGUI extends JFrame {
    
    public SimulatorGUI() {
        setTitle("8085 Simulator");
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        getContentPane().setBackground(Color.WHITE);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Welcome to 8085 Simulator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 50, 50));
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(createOptionPanel("A", "Write Assembly Code", new Color(75, 110, 175)), gbc);
        
        gbc.gridy = 1;
        mainPanel.add(createOptionPanel("M", "Check Memory", new Color(100, 150, 100)), gbc);
        
        gbc.gridy = 2;
        mainPanel.add(createOptionPanel("R", "Check Registers Status", new Color(180, 100, 140)), gbc);
        
        gbc.gridy = 3;
        mainPanel.add(createOptionPanel("G", "Execute Code", new Color(200, 120, 50)), gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        JLabel infoLabel = new JLabel("Press the corresponding key to navigate");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        infoLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(infoLabel);
        
        add(footerPanel, BorderLayout.SOUTH);
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char key = Character.toLowerCase(e.getKeyChar());
                
                if (key == 'a') {
                    openCodeWindow();
                } else if (key == 'm') {
                    openMemoryWindow();
                } else if (key == 'r') {
                    openRegisterWindow();
                } else if (key == 'g') {
                    openExecuteWindow();
                }
            }
        });
        
        setFocusable(true);
    }
    
    private JPanel createOptionPanel(String key, String description, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(600, 90));
        panel.setMaximumSize(new Dimension(600, 90));
        
        JPanel keyBox = new JPanel();
        keyBox.setBackground(color);
        keyBox.setPreferredSize(new Dimension(90, 70));
        keyBox.setBorder(BorderFactory.createLineBorder(color.darker(), 3));
        keyBox.setLayout(new GridBagLayout());
        
        JLabel keyLabel = new JLabel(key);
        keyLabel.setFont(new Font("Arial", Font.BOLD, 48));
        keyLabel.setForeground(Color.WHITE);
        keyBox.add(keyLabel);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        descLabel.setForeground(new Color(60, 60, 60));
        descLabel.setPreferredSize(new Dimension(400, 30));
        
        panel.add(keyBox);
        panel.add(descLabel);
        
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(245, 245, 245));
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE);
                panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
            public void mouseClicked(MouseEvent e) {
                if (key.equals("A")) {
                    openCodeWindow();
                } else if (key.equals("M")) {
                    openMemoryWindow();
                } else if (key.equals("R")) {
                    openRegisterWindow();
                } else if (key.equals("G")) {
                    openExecuteWindow();
                }
            }
        });
        
        return panel;
    }
    
    private void openCodeWindow() {
        String startAddr = JOptionPane.showInputDialog(this, 
            "Enter Starting Memory Address (hex):", "Starting Address", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (startAddr != null && !startAddr.trim().isEmpty()) {
            try {
                int addr = Integer.parseInt(startAddr.trim(), 16);
                CodeWindow codeWin = new CodeWindow(this, addr);
                codeWin.setVisible(true);
                setVisible(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid hex address!");
            }
        }
    }
    
    private void openMemoryWindow() {
        MemoryWindow memWin = new MemoryWindow(this);
        memWin.setVisible(true);
        setVisible(false);
    }
    
    private void openRegisterWindow() {
        RegisterWindow regWin = new RegisterWindow(this);
        regWin.setVisible(true);
        setVisible(false);
    }
    
    private void openExecuteWindow() {
        ExecuteWindow execWin = new ExecuteWindow(this);
        execWin.setVisible(true);
        setVisible(false);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulatorGUI gui = new SimulatorGUI();
            gui.setVisible(true);
        });
    }
}
