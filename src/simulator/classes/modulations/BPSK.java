/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.modulations;

import java.util.stream.IntStream;

/**
 *
 * @author HP
 */
public class BPSK {
    private double[] baseband;
    private double[] carrier;
    private double[] modulated;
    
    private void doBPSK () {
        for (int i = 0; i < baseband.length; i++) {
//          PHASE SHIFT
            baseband[i] = (baseband[i] == 1) ? 1 : -1;
            
//          MODULATION
            modulated[i] = baseband[i] * carrier[i];
        }
    }
    
    public double[] getModulated() {
        return this.modulated;
    }
    
    public double[] getDemodulated(double[] received) {
        double[] demodulated = new double[baseband.length];
        
        if (received.length != demodulated.length) {
            throw new IllegalArgumentException("Invalid array length");
        }
        
//      DEMODULATION
        for (int i = 0; i < demodulated.length; i++) {
            demodulated[i] = received[i] * carrier[i];
        }
        
        return demodulated;
    }
    
    public BPSK(double[] baseband, double[] carrier) {
        if (baseband.length != carrier.length) {
            throw new IllegalArgumentException("Baseband and carrier must have the same array size");
        }
        
        this.baseband = baseband;
        this.carrier = carrier;
        this.modulated = new double[baseband.length];

        doBPSK();
    }
}