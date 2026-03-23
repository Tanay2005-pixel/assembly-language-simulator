package simulator.parser;

import java.util.*;
import simulator.instructions.Instruction;
import simulator.instructions.arithmetic.*;
import simulator.instructions.branching.*;
import simulator.instructions.control.*;
import simulator.instructions.datatransfer.*;
import simulator.instructions.logical.*;
import simulator.instructions.stack.*;
import simulator.util.Converter;
import simulator.util.Validator;

public class InstructionParser {

    private final Map<String, Integer> labelMap = new HashMap<>();
    private final List<Instruction> instructions = new ArrayList<>();
    private final List<String[]> rawTokenLines = new ArrayList<>();

    public List<Instruction> parse(String program) {
        labelMap.clear();
        instructions.clear();
        rawTokenLines.clear();
        String[] lines = program.split("\\r?\\n");
        int instrIndex = 0;
        for (String rawLine : lines) {
            String line = stripComment(rawLine).trim();
            if (line.isEmpty()) continue;
            if (line.contains(":")) {
                int colonIdx = line.indexOf(':');
                String label = line.substring(0, colonIdx).trim().toUpperCase();
                labelMap.put(label, instrIndex);
                line = line.substring(colonIdx + 1).trim();
                if (line.isEmpty()) continue;   
            }
            String[] tokens = tokenize(line);
            if (tokens.length == 0) continue;

            String error = Validator.validateLine(tokens);
            if (error != null) {
                System.out.println("[PARSE ERROR] " + error + "  →  line: \"" + rawLine.trim() + "\"");
                continue;
            }

            rawTokenLines.add(tokens);
            instrIndex++;
        }

        for (String[] tokens : rawTokenLines) {
            Instruction instr = buildInstruction(tokens);
            if (instr != null) instructions.add(instr);
        }
        return instructions;
    }
    private Instruction buildInstruction(String[] t) {
        String mnemonic = t[0].toUpperCase();
        try {
            switch (mnemonic) {

                case "MOV":  return new MOV(t[1].toUpperCase(), t[2].toUpperCase());
                case "MVI":  return new MVI(t[1].toUpperCase(), Converter.hexToInt(t[2]));
                case "LDA":  return new LDA(resolveAddress(t[1]));
                case "STA":  return new STA(resolveAddress(t[1]));
                case "LHLD": return new LHLD(resolveAddress(t[1]));
                case "SHLD": return new SHLD(resolveAddress(t[1]));
                case "LXI":  return new LXI(t[1].toUpperCase(), Converter.hexToInt(t[2]));
                case "XCHG": return new XCHG();
                case "ADD":  return new ADD(t[1].toUpperCase());
                case "ADI":  return new ADI(Converter.hexToInt(t[1]));
                case "SUB":  return new SUB(t[1].toUpperCase());
                case "SUI":  return new SUI(Converter.hexToInt(t[1]));
                case "INR":  return new INR(t[1].toUpperCase());
                case "DCR":  return new DCR(t[1].toUpperCase());
                case "INX":  return new INX(t[1].toUpperCase());
                case "DCX":  return new DCX(t[1].toUpperCase());
                case "DAD":  return new DAD(t[1].toUpperCase());
                case "MUL":  return new MUL(t[1].toUpperCase());
                case "DIV":  return new DIV(t[1].toUpperCase());
                case "ANA":  return new ANA(t[1].toUpperCase());
                case "ANI":  return new ANI(Converter.hexToInt(t[1]));
                case "ORA":  return new ORA(t[1].toUpperCase());
                case "ORI":  return new ORI(Converter.hexToInt(t[1]));
                case "XRA":  return new XRA(t[1].toUpperCase());
                case "XRI":  return new XRI(Converter.hexToInt(t[1]));
                case "CMA":  return new CMA();
                case "CMP":  return new CMP(t[1].toUpperCase());
                case "CPI":  return new CPI(Converter.hexToInt(t[1]));
                case "RLC":  return new RLC();
                case "RRC":  return new RRC();
                case "JMP":  return new JMP(resolveAddress(t[1]));
                case "JZ":   return new JZ(resolveAddress(t[1]));
                case "JNZ":  return new JNZ(resolveAddress(t[1]));
                case "JC":   return new JC(resolveAddress(t[1]));
                case "JNC":  return new JNC(resolveAddress(t[1]));
                case "JM":   return new JM(resolveAddress(t[1]));
                case "JP":   return new JP(resolveAddress(t[1]));
                case "JPE":  return new JPE(resolveAddress(t[1]));
                case "JPO":  return new JPO(resolveAddress(t[1]));
                case "CALL": return new CALL(resolveAddress(t[1]));
                case "RET":  return new RET();
                case "PUSH": return new PUSH(t[1].toUpperCase());
                case "POP":  return new POP(t[1].toUpperCase());
                case "XTHL": return new XTHL();
                case "SPHL": return new SPHL();
                case "HLT":  return new HLT();
                case "NOP":  return new NOP();
                case "STC":  return new STC();
                case "CMC":  return new CMC();
                default:
                    System.out.println("[PARSE ERROR] Unhandled mnemonic: " + mnemonic);
                    return null;
            }
        } catch (Exception e) {
            System.out.println("[PARSE ERROR] Bad operands for " + mnemonic + e.getMessage());
            return null;
        }
    }


    private int resolveAddress(String token) {
        String upper = token.trim().toUpperCase();
        if (labelMap.containsKey(upper)) {
            return labelMap.get(upper);
        }
        return Converter.hexToInt(token);
    }

    private String stripComment(String line) {
        int semi  = line.indexOf(';');
        int slash = line.indexOf("//");
        int cut   = line.length();
        if (semi  >= 0) cut = Math.min(cut, semi);
        if (slash >= 0) cut = Math.min(cut, slash);
        return line.substring(0, cut);
    }

    private String[] tokenize(String line) {
        String[] parts = line.trim().split("\\s+", 2);
        if (parts.length == 1) return new String[]{parts[0].toUpperCase()};

        String mnemonic = parts[0].toUpperCase();
        String[] operands = parts[1].split(",");
        String[] result = new String[1 + operands.length];
        result[0] = mnemonic;
        for (int i = 0; i < operands.length; i++) {
            result[i + 1] = operands[i].trim();
        }
        return result;
    }

    public Map<String, Integer> getLabelMap() { return Collections.unmodifiableMap(labelMap); }
}
