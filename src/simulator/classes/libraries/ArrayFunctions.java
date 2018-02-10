/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.libraries;

/**
 *
 * @author HP
 */
public class ArrayFunctions {
    public static double[] add (double[] array1, double[] array2) {
        double[] arraySum = new double[array1.length];
        
        if (array1.length == array2.length) {            
            for (int i = 0; i < array1.length; i++) {
                arraySum[i] = array1[i] + array2[i];
            }
        } else {
            throw new IllegalArgumentException("Arrays must have equal lengths.");
        }
        
        return arraySum;
    }    
}
