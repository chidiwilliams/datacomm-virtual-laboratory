/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.modulations;

import classes.helpers.ArrayFunctions;

/**
 *
 * @author chidi
 */
public class QPSK {
    double[] baseband;
    double[] carrier;
    double[] carrier90;
    double[] oddmodulated;
    double[] evenmodulated;
    double[] modulated;
    
    /**
     *
     * @param baseband
     * @param carrier
     * @param carrier90
     */
    public QPSK (double[] baseband, double[] carrier, double[] carrier90) {
        if (carrier.length != carrier90.length) {
            throw new IllegalArgumentException("Carriers must have the same array size");
        }
        
        this.baseband = baseband;
        this.carrier = carrier;  // SIN wave
        this.carrier90 = carrier90; // COS wave
        
        int halflen = (int) Math.ceil(baseband.length / 2);
        
        int[] oddbits = new int[halflen];
        int[] evenbits = new int[halflen];
        
        for (int i = 0; i < baseband.length; i++) {
            int num = (int) Math.floor(i / 2);
            
            if (i % 2 == 0) {
                oddbits[num] = (int) baseband[i];
            } else {
                evenbits[num] = (int) baseband[i];
            }
        }
        
        oddmodulated = new BPSK(oddbits, carrier).getModulated();
        evenmodulated = new BPSK(evenbits, carrier90).getModulated();
        
        modulated = ArrayFunctions.add(oddmodulated, evenmodulated);
    }
    
    public double[] getModulated() {
        return this.modulated;
    }
    
    public double[] getDemodulated(double[] received) {        
        double[] demodulated = new double[received.length];
        
        for (int i = 0; i < 8; i++) {            
            for (int j = i * received.length / 8; j < (i + 1) * received.length / 8; j++) {
                demodulated[j] = (i % 2 == 0) ? received[j] * carrier[j] : received[j] * carrier90[j];
            }
        }
        
        return demodulated;
    }
}
