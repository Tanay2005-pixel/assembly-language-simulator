package simulator.registers;

public class Flags {
    public boolean z;
    public boolean s;
    public boolean p;
    public boolean cy;
    public boolean acy;
    public void updateFlags(int result) {
        z= (result & 0xFF) == 0;
        s= (result & 0x80) != 0;
        p= computeParity(result & 0xFF);
        cy= (result > 0xFF) || (result < 0);
        acy = false;
    }
    private boolean computeParity(int value) {
        int count = 0;
        while (value != 0) { count += (value & 1); value >>= 1; }
        return (count % 2) == 0;
    }
    public void reset() {
        z = s = p = cy = acy = false;
    }
    @Override
    public String toString() {
        return String.format("Z=%b S=%b P=%b CY=%b AC=%b", z, s, p, cy, acy);
    }
}