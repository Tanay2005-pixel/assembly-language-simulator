import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class savefile {
    
    private static final String SAVE_FILE = "saved_code.txt";
    private static final String HISTORY_FILE = "code_history.txt";
    private static final String ADDRESS_FILE = "start_address.txt";
    
    public static void saveCode(String code, int startAddress) {
        try {
            Files.write(Paths.get(SAVE_FILE), code.getBytes());
            Files.write(Paths.get(ADDRESS_FILE), String.format("%04X", startAddress).getBytes());
            
            String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String historyEntry = "\n=== Saved at " + timestamp + 
                                " | Start Address: " + String.format("%04X", startAddress) + 
                                " ===\n" + code + "\n";
            
            Files.write(Paths.get(HISTORY_FILE), historyEntry.getBytes(), 
                       StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            
        } catch (IOException e) {
            System.err.println("Error saving code: " + e.getMessage());
        }
    }
    
    public static String loadCode() {
        try {
            if (Files.exists(Paths.get(SAVE_FILE))) {
                return new String(Files.readAllBytes(Paths.get(SAVE_FILE)));
            }
        } catch (IOException e) {
            System.err.println("Error loading code: " + e.getMessage());
        }
        return "";
    }
    
    public static int loadStartAddress() {
        try {
            if (Files.exists(Paths.get(ADDRESS_FILE))) {
                String addr = new String(Files.readAllBytes(Paths.get(ADDRESS_FILE)));
                return Integer.parseInt(addr.trim(), 16);
            }
        } catch (Exception e) {
            System.err.println("Error loading start address: " + e.getMessage());
        }
        return 0;
    }
}
