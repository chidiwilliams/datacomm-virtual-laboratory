/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.filters;

import simulator.classes.libraries.Filter;
import simulator.classes.libraries.filterType;

/**
 *
 * @author HP
 */
public class BPF {
    Filter filter;
    double[] filtered;
    
    /**
     *
     * @param sampFreq
     * @param cutoffFreqLow
     * @param cutoffFreqHigh
     * @param input
     */
    public BPF (double sampFreq, double cutoffFreqLow, double cutoffFreqHigh, double[] input) {
//      Calculate the number of taps required for the filter.
//      About 150 taps are required per 2048 samples.
        int num_taps =  (int) Math.round(input.length * 150 / 2048);
        
        doFiltering(sampFreq, cutoffFreqLow, cutoffFreqHigh, num_taps, input);
    }
    
    /**
     *
     * @param sampFreq
     * @param cutoffFreqLow
     * @param cutoffFreqHigh
     * @param num_taps
     * @param input
     */
    public BPF (double sampFreq, double cutoffFreqLow, double cutoffFreqHigh, int num_taps, double[] input) {      
        doFiltering(sampFreq, cutoffFreqLow, cutoffFreqHigh, num_taps, input);
    }
    
    private void doFiltering(double sampFreq, double cutoffFreqLow, double cutoffFreqHigh, int num_taps, double[] input) {
        filter = new Filter(filterType.BPF, num_taps, sampFreq, cutoffFreqLow, cutoffFreqHigh);
        
        filtered = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            filtered[i] = filter.do_sample(input[i]);
        }
    }
    
    public double[] getFiltered() {
        return filtered;
    }
}
