/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.helpers;

/**
 *
 * @author HP
 */
public class Sampler {
    public Sampler () {
        
    }
    
    public double[] sample(double[] sequence, int num_samples) {
        double[] output = new double[num_samples];
        
        if ((num_samples % sequence.length) != 0) {
            throw new IllegalArgumentException("Cannot expand signal. New bit length must be a multiple of the original bit length.");
        }
        
        for (int i = 0; i < sequence.length; i++) {
            for (int j = num_samples * i / sequence.length; j < num_samples * (i + 1) / sequence.length; j++) {
                output[j] = sequence[i];
            }
        }
        
        return output;
    }
}