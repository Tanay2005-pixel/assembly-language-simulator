import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CodeWindow extends JFrame {
    
    private JTextArea codeArea;
    private SimulatorGUI mainWindow;
    private static String savedCode = "";
    private int startingAddress;
    private static int savedStartingAddress = 0;
    
    public CodeWindow(SimulatorGUI main, int startAddr) {
        this.mainWindow = main;
        this.startingAddress = startAddr;
        
        setTitle("Write Assembly Code - Starting at " + String.format("%04X", startAddr));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(60, 63, 65));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel titleLabel = new JLabel("Assembly Code Editor");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel addrLabel = new JLabel("  |  Start Address: " + String.format("%04X", startAddr));
        addrLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        addrLabel.setForeground(new Color(100, 200, 255));
        
        topPanel.add(titleLabel);
        topPanel.add(addrLabel);
        add(topPanel, BorderLayout.NORTH);
        
        codeArea = new JTextArea();
        codeArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        codeArea.setText(savedCode);
        codeArea.setBackground(new Color(43, 43, 43));
        codeArea.setForeground(new Color(169, 183, 198));
        codeArea.setCaretColor(Color.WHITE);
        codeArea.setTabSize(4);
        
        JScrollPane scrollPane = new JScrollPane(codeArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(60, 63, 65));
        bottomPanel.setLayout(new FlowLayout());
        
        JButton saveButton = new JButton("Save (ESC)");
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.setBackground(new Color(75, 110, 175));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 12));
        cancelButton.setBackground(new Color(120, 120, 120));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        
        bottomPanel.add(saveButton);
        bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        saveButton.addActionListener(e -> saveAndReturn());
        cancelButton.addActionListener(e -> {
            mainWindow.setVisible(true);
            dispose();
        });
        
        codeArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    saveAndReturn();
                }
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveAndReturn();
            }
        });
    }
    
    private void saveAndReturn() {
        savedCode = codeArea.getText();
        savedStartingAddress = startingAddress;
        
        try {
            MongoDBHelper.saveCode(savedCode, startingAddress);
            JOptionPane.showMessageDialog(this, 
                "Code saved successfully at address " + String.format("%04X", startingAddress) + "!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Code saved locally");
        }
        
        mainWindow.setVisible(true);
        dispose();
    }
    
    public static String getSavedCode() {
        return savedCode;
    }
    
    public static int getSavedStartingAddress() {
        return savedStartingAddress;
    }
}
