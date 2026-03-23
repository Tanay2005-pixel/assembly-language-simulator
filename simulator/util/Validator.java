package simulator.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Validator {

    private static final Set<String> VALID_REGISTERS = new HashSet<>(
            Arrays.asList("A", "B", "C", "D", "E", "H", "L", "M"));

    private static final Set<String> VALID_PAIRS = new HashSet<>(
            Arrays.asList("B", "D", "H", "SP", "PSW"));

    private static final Set<String> VALID_MNEMONICS = new HashSet<>(Arrays.asList(
            "MOV", "MVI", "LDA", "STA", "LHLD", "SHLD", "LXI", "XCHG",
            "ADD", "ADI", "SUB", "SUI", "INR", "DCR", "INX", "DCX", "DAD", "MUL", "DIV",
            "ANA", "ANI", "ORA", "ORI", "XRA", "XRI", "CMA", "CMP", "CPI", "RLC", "RRC",
            "JMP", "JZ", "JNZ", "JC", "JNC", "JM", "JP", "JPE", "JPO", "CALL", "RET",
            "PUSH", "POP", "XTHL", "SPHL",
            "HLT", "NOP", "STC", "CMC"
    ));
    public static boolean isValidRegister(String reg) {
        return VALID_REGISTERS.contains(reg.toUpperCase());
    }
    public static boolean isValidPair(String pair) {
        return VALID_PAIRS.contains(pair.toUpperCase());
    }
    public static boolean isValidMnemonic(String mnemonic) {
        return VALID_MNEMONICS.contains(mnemonic.toUpperCase());
    }
    public static boolean isValidAddress(int address) {
        return address >= 0 && address <= 0xFFFF;
    }
    public static boolean isValidByte(int value) {
        return value >= 0 && value <= 0xFF;
    }
    public static String validateLine(String[] tokens) {
        if (tokens.length == 0) return null;
        String mnemonic = tokens[0].toUpperCase();
        if (!isValidMnemonic(mnemonic)) {
            return "Unknown instruction: '" + mnemonic + "'";
        }
        return null;
    }
}
