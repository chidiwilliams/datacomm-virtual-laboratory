/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.libraries;

import classes.codingSchemes.Hamming;
import classes.filters.LPF;
import classes.helpers.Sampler;
import classes.modulations.BPSK;
import classes.modulations.QPSK;
import classes.signals.WaveSignal;
import classes.signals.WaveSignalType;
import simulator.classes.impairments.AWGN;
import simulator.classes.signals.CarrierSignal;
import simulator.classes.signals.Signal;

/**
 *
 * @author HP
 */
public class BER {
    private double BER;
    
    public BER(int num_bits, int num_samples, int signal_power, int noise_power, int cutoff, int num_taps) {
        
        Signal input = new Signal(num_bits);
        for (int i = 0; i < input.getLength(); i++) {
            input.setSignalValue(i, Math.rint(Math.random()));
        }
        
        Signal bb = new Signal(num_samples);
        bb.setSignal(new Sampler().sample(input.getSignal(), num_samples));
        
        CarrierSignal carr = new CarrierSignal(num_samples, num_bits);
        
//        BPSK modulation = new BPSK(bb.getSignal(), carr.getSignal());
        QPSK modulation = new QPSK(
                bb.getSignal(), 
                new WaveSignal(WaveSignalType.SIN, num_samples, num_bits, 0).getSignal(), 
                new WaveSignal(WaveSignalType.COS, num_samples, num_bits, 0).getSignal()
        );
        
        Signal modulated = new Signal(num_samples);
        
        for (int i = 0; i < modulated.getLength(); i++) {
            modulated.setSignalValue(i, modulation.getModulated()[i] * signal_power);
        }
        
        Signal noise = new Signal(num_samples);
        noise.setSignal(new AWGN().generateNoise(noise_power, num_samples));
        
        Signal recv = new Signal(num_samples);
        recv.setSignal(
                ArrayFunctions.add(modulated.getSignal(), noise.getSignal())
        );
        
        Signal demodulated = new Signal(num_samples);
        demodulated.setSignal(modulation.getDemodulated(recv.getSignal()));
        
        LPF lpFilter = new LPF(num_samples, cutoff, num_taps, demodulated.getSignal());
        
        Signal filtered = new Signal(num_samples);
        filtered.setSignal(lpFilter.getFiltered());
        
        Signal thresh = new Signal(num_bits);
        thresh.setSignal(filtered.getThresholds(num_bits));
        
        int errors = 0;
        for (int i = 0; i < input.getLength(); i++) {
            if (input.getSignalValue(i) != thresh.getSignalValue(i)) {
                errors++;
            }
        }
        
        BER = errors * Math.pow(input.getLength(), -1);
    }
    
    public double getBER() {
        return this.BER;
    }

    /**
     * @param BER the BER to set
     */
    public void setBER(double BER) {
        this.BER = BER;
    }
    
    public static void main(String[] args) {
        for (int i = 50; i > 0; i--) {
            double sum_BER = 0;
            
            for (int j = 0; j < 100; j++) {
                sum_BER += new BER(1024, 262144, 1, i, 1024, 150).getBER();
            }
            
            double avg_BER = sum_BER * Math.pow(100, -1);
            System.out.println(avg_BER);
//            System.out.println("Eb/No: 1/" + i);
//            System.out.println("BER: " + avg_BER);
//            System.out.println("----");
        }
    }
}