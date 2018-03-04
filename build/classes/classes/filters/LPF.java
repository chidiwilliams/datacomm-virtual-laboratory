/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.filters;

import classes.helpers.filterType;
import classes.helpers.Filter;

/**
 *
 * @author HP
 */
public class LPF {
    private Filter filter;
    private double[] filtered;
    
    /**
     * 
     * @param sampFreq
     * @param cutoffFreq
     * @param input
     */
    public LPF (double sampFreq, double cutoffFreq, double[] input) {
//      Calculate the number of taps required for the filter.
//      About 150 taps are required per 2048 samples.
        int num_taps =  Math.round(input.length * 150 / 2048);
        
        doFiltering(sampFreq, cutoffFreq, num_taps, input);
    }
    
    /**
     * 
     * @param sampFreq
     * @param cutoffFreq
     * @param num_taps
     * @param input
     */
    public LPF (double sampFreq, double cutoffFreq, int num_taps, double[] input) {      
        doFiltering(sampFreq, cutoffFreq, num_taps, input);
    }
    
    private void doFiltering(double sampFreq, double cutoffFreq, int num_taps, double[] input) {
        setFilter(new Filter(filterType.LPF, num_taps, sampFreq, cutoffFreq));
        
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
