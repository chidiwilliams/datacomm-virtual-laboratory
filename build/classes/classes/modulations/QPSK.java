/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.modulations;

import java.util.Arrays;
import classes.helpers.ArrayFunctions;

/**
 *
 * @author chidi
 */
public class QPSK {
    double[] bits;
    double[] carrier;
    double[] carrier90;
    double[] modulated;
    
    /**
     *
     * @param bits
     * @param carrier
     * @param carrier90
     */
    public QPSK (double[] bits, double[] carrier, double[] carrier90) {
        if (carrier.length != carrier90.length) {
            throw new IllegalArgumentException("Carriers must have the same array size");
        }
        
        this.bits = bits;
        this.carrier = carrier;
        this.carrier90 = carrier90;
        
        int halflen = (int) Math.ceil(bits.length / 2);
        
        int[] oddbits = new int[halflen];
        int[] evenbits = new int[halflen];
        
        for (int i = 0; i < bits.length; i++) {
            int num = (int) Math.floor(i / 2);
            
            if (i % 2 == 0) {
                evenbits[num] = (int) bits[i];
            } else {
                oddbits[num] = (int) bits[i];
            }
        }
        
        double[] oddmodulated = new BPSK(oddbits, carrier90).getModulated();
        double[] evenmodulated = new BPSK(evenbits, carrier).getModulated();
        
        this.modulated = ArrayFunctions.add(oddmodulated, evenmodulated);
    }
    
    public double[] getModulated() {
        return this.modulated;
    }
}
