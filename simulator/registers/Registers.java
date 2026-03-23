package simulator.registers;

public class Registers {
    private int A, B, C, D, E, H, L;

    public int get(String name) {
        switch (name.toUpperCase()) {
            case "A": return A;
            case "B": return B;
            case "C": return C;
            case "D": return D;
            case "E": return E;
            case "H": return H;
            case "L": return L;
            default: throw new IllegalArgumentException("Unknown register: " + name);
        }
    }

    public void set(String name, int value) {
        value = value & 0xFF;
        switch (name.toUpperCase()) {
            case "A": A = value; break;
            case "B": B = value; break;
            case "C": C = value; break;
            case "D": D = value; break;
            case "E": E = value; break;
            case "H": H = value; break;
            case "L": L = value; break;
            default: throw new IllegalArgumentException("Unknown register: " + name);
        }
    }

    public int getHL() {
        return (H << 8) | L;
    }
    public void setHL(int value) {
        H = (value >> 8) & 0xFF; L = value & 0xFF; 
    }

    public int getBC() { 
        return (B << 8) | C; 
    }
    public void setBC(int value) { 
        B = (value >> 8) & 0xFF; C = value & 0xFF; 
    }

    public int getDE() { 
        return (D << 8) | E; 
    }
    public void setDE(int value) { 
        D = (value >> 8) & 0xFF; E = value & 0xFF; 
    }

    public void reset() { 
        A = B = C = D = E = H = L = 0; 
    }

    @Override
    public String toString() {
        return String.format("A=%02X B=%02X C=%02X D=%02X E=%02X H=%02X L=%02X", A, B, C, D, E, H, L);
    }
}
