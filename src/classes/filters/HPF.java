/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.filters;

import classes.helpers.Filter;
import classes.helpers.filterType;

/**
 *
 * @author HP
 */
public class HPF {
    private Filter filter;
    private double[] filtered;
    
    /**
     * 
     * @param sampFreq
     * @param cutoffFreq
     * @param input
     */
    public HPF (double sampFreq, double cutoffFreq, double[] input) {
//      Calculate the number of taps required for the filter.
        int num_taps;
        switch((int) sampFreq) {
            case 128: {
                num_taps = 13;
                break;
            }
            case 256:
            case 512: {
                num_taps = 15;
                break;
            }
            case 1024: {
                if (cutoffFreq < 16) {
                    num_taps = 39;
                } else if (cutoffFreq < 20) {
                    num_taps = 33;
                } else if (cutoffFreq < 30) {
                    num_taps = 29;
                } else if (cutoffFreq < 40) {
                    num_taps = 21;
                } else if (cutoffFreq < 50) {
                    num_taps = 39;
                } else if (cutoffFreq < 60) {
                    num_taps = 31;
                } else {
                    num_taps = 41;
                }
                break;
            }
            case 2048: {
                num_taps = (int) 
                        (-46.63636 + 
                        11.84942 * cutoffFreq - 
                        0.4668378 * Math.pow(cutoffFreq, 2) + 
                        0.006907643 * Math.pow(cutoffFreq, 3) - 
                        0.00003727555 * Math.pow(cutoffFreq, 4) + 
                        0.0000000391251 * Math.pow(cutoffFreq, 5));
                if (num_taps % 2 == 0) {
                    num_taps += 1;
                }
                System.out.println(num_taps);
                break;
            }
            default: {
                num_taps = 39;
                break;
            }
        }
        
        doFiltering(sampFreq, cutoffFreq, num_taps, input);
    }
    
    /**
     * 
     * @param sampFreq
     * @param cutoffFreq
     * @param num_taps
     * @param input
     */
    public HPF (double sampFreq, double cutoffFreq, int num_taps, double[] input) {              
        doFiltering(sampFreq, cutoffFreq, num_taps, input);
    }
    
    private void doFiltering(double sampFreq, double cutoffFreq, int num_taps, double[] input) {
        setFilter(new Filter(filterType.HPF, num_taps, sampFreq, cutoffFreq));
        
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
