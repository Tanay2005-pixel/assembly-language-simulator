package simulator.util;

public class Converter {

    public static int hexToInt(String token) {
        token = token.trim().toUpperCase();
        if (token.endsWith("H")) {
            return Integer.parseInt(token.substring(0, token.length() - 1), 16);
        } 
        else if (token.startsWith("0X")) {
            return Integer.parseInt(token.substring(2), 16);
        } 
        else {
            try {
                return Integer.parseInt(token, 16);
            } 
            catch (NumberFormatException e) {
                return Integer.parseInt(token);
            }
        }
    }

    public static String intToHex(int value) {
        return String.format("%04XH", value & 0xFFFF);
    }

    public static String byteToHex(int value) {
        return String.format("%02XH", value & 0xFF);
    }
}
