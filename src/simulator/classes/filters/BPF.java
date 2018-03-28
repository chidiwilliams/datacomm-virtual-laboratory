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
    private Filter filter;
    private double[] filtered;
    
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
        int num_taps =  Math.round(input.length * 150 / 2048);
        
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
        setFilter(new Filter(filterType.BPF, num_taps, sampFreq, cutoffFreqLow, cutoffFreqHigh));
        
        setFiltered(new double[input.length]);
        for (int i = 0; i < input.length; i++) {
            getFiltered()[i] = getFilter().do_sample(input[i]);
        }
    }
    
    /**
     *
     * @return
     */
    public double[] getFiltered() {
        return filtered;
    }

    /**
     * @return the filter
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    /**
     * @param filtered the filtered to set
     */
    public void setFiltered(double[] filtered) {
        this.filtered = filtered;
    }
}
