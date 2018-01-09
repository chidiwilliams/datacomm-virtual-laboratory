/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.libraries;

import java.util.stream.IntStream;
import simulator.classes.signals.*;

/**
 *
 * @author HP
 */
public class SignalFFT {
    private final double[] frequencies;
    private final double[] twoSidedSpectrum;
    private final double[] singleSidedSpectrum;
    
    public SignalFFT(double samplingFrequency, double[] signal) {
//      Complexify
        Complex[] complexSignal = new Complex[signal.length];
        IntStream.range(0, signal.length)
                .forEach(i -> 
                        complexSignal[i] = new Complex(signal[i], 0)
                );
        
//      Compute FFT
        Complex[] signalFFT = FFT.fft(complexSignal);
        
//      Compute two-sided spectrum
        twoSidedSpectrum = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            twoSidedSpectrum[i] = signalFFT[i].abs() / signal.length;
        }
        
//      Compute the single-sided spectrum
        singleSidedSpectrum = new double[(signal.length / 2) + 1];
        System.arraycopy(twoSidedSpectrum, 0, singleSidedSpectrum, 0, (signal.length / 2) + 1);
        
        for (int i = 1; i < singleSidedSpectrum.length - 1; i++) {
            singleSidedSpectrum[i] *= 2;
        }
        
//      Get frequency domain
        frequencies = new double[(signal.length / 2) + 1];
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = samplingFrequency * i / signal.length;
        }
    }
    
    public double[] getFrequencies() {
        return frequencies;
    }
    
    public double[] getTwoSidedSpectrum() {
        return twoSidedSpectrum;
    }
    
    public double[] getSingleSidedSpectrum() {
        return singleSidedSpectrum;
    }
    
    public int getArraytoMax(double maxFrequency) {
        int max = frequencies.length - 1;
        
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] < maxFrequency) {
                max = i;
            }
        }
        
        return max;
    }
    
    public double[] getFrequenciesToMax(double maxFrequency) {
        int max = getArraytoMax(maxFrequency);
        
        double[] freq = new double[max];
        System.arraycopy(frequencies, 0, freq, 0, freq.length);
        
        return freq;
    }
    
    public double[] getSingleSidedSpectrumToMax(double maxFrequency) {
        int max = getArraytoMax(maxFrequency);
        
        double[] spec = new double[max];
        System.arraycopy(singleSidedSpectrum, 0, spec, 0, spec.length);
        
        return spec;
    }
}
