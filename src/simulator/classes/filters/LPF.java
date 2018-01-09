/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.filters;

import simulator.classes.libraries.*;

/**
 *
 * @author HP
 */
public class LPF {
    Filter filter;
    double[] filtered;
    
    /**
     * 
     * @param sampFreq
     * @param cutoffFreq
     * @param input
     */
    public LPF (double sampFreq, double cutoffFreq, double[] input) {
//      Calculate the number of taps required for the filter.
//      About 150 taps are required per 2048 samples.
        int num_taps =  (int) Math.round(input.length * 150 / 2048);
        
        System.out.println(num_taps);
        
        filter = new Filter(filterType.LPF, num_taps, sampFreq, cutoffFreq);
        
        filtered = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            filtered[i] = filter.do_sample(input[i]);
        }
    }
    
    /**
     * 
     * @param sampFreq
     * @param cutoffFreq
     * @param num_taps
     * @param input
     */
    public LPF (double sampFreq, double cutoffFreq, int num_taps, double[] input) {      
        System.out.println(num_taps);
        
        filter = new Filter(filterType.LPF, num_taps, sampFreq, cutoffFreq);
        
        filtered = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            filtered[i] = filter.do_sample(input[i]);
        }
    }
    
    public double[] getFiltered() {
        return filtered;
    }
}
