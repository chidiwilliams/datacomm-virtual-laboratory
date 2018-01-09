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
    
    public static double[] setMaxAmplitude(double[] array, double maxAmplitude) {
        double[] array_ampd = new double[array.length];
        double maxValue = 0;
        double factor;
        
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        
        factor = maxAmplitude / maxValue;
        
        for (int i = 0; i < array.length; i++) {
            array_ampd[i] = array[i] * factor;
        }
        
        return array_ampd;
    }
//    TBD
//    public static double[] multiply (double[] array1, double[] array2) {
//        double[] arrayMult = new double[array1.length];
//        
//        if (array1.length == array2.length) {            
//            for (int i = 0; i < array1.length; i++) {
//                arrayMult[i] = array1[i] * array2[i];
//            }
//        } else {
//            throw new IllegalArgumentException("Arrays must have equal lengths.");
//        }
//        
//        return arrayMult;
//    }
}
