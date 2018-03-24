/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.modulations;

import classes.signals.WaveSignal;
import classes.signals.WaveSignalType;

/**
 *
 * @author chidi
 */
public class QAM_8 {
    double[] bits;
    double[] modulated;
    
    public QAM_8 (double[] bits, int samples) {
        int num_carriers = (int) (bits.length + 2) / 3;
        double[][] bits_arr = new double[num_carriers][3];
        modulated = new double[samples];
        this.bits = bits;
        
        for (int i = 0; i < num_carriers; i++) {
            for (int j = 0; j < 3; j++) {
                if (((3 * i) + j) < bits.length) {
                    bits_arr[i][j] = bits[(i * 3) + j];
                }
            }
        }
        
        int width = (int) samples / num_carriers;
        
        for (int i = 0; i < num_carriers; i++) {
            for (int j = i * width; j < (i + 1) * width; j++) {
                int angle = (bits_arr[i][1] == 0) ? 135 : 45;
                angle = (bits_arr[i][0] == 0) ? (angle * -1) : angle;
                
                double amp = (bits_arr[i][2] == 0) ? 0.765 : 1.848;
                
                modulated[j] = amp * Math.sin((2 * Math.PI * bits.length * (j - (i * width)) / (width * 3)) + angle);
            }
        }
    }
    
    public double[] getDemodulated(double[] received) {
        double[] demodulated = new double[modulated.length];
        
        WaveSignal carrier = new WaveSignal(WaveSignalType.SIN, modulated.length, bits.length, -45);
        
        for (int i = 0; i < demodulated.length; i++) {
            demodulated[i] = received[i] * carrier.getSignalValue(i);
        }
        
        return demodulated;
    }
    
    public double[] getModulated() {
        return this.modulated;
    }
}
