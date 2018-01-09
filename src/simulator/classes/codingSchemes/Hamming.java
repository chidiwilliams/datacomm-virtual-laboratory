/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.codingSchemes;

/**
 *
 * @author HP
 */
public class Hamming {
    public Hamming (double[] input) {
//      Check if the input contains only binary inputs (0, 1)
//      If not, throw IllegalArgumentException

        checkBinary(input);
        
        if (input.length == 4) {
            message = input;
            
//            Perform Hamming(7, 4) on the four-bit message. Save to variable transmitted. Do parity bit also.
            hamming74();
            addParity8();
        } else {
            throw new IllegalArgumentException("Cannot compute the Hamming code of this array.");
        }
        
    }
    
    double[] message;
    private final double[][] generatorFour = {
        { 1, 1, 0, 1 }, { 1, 0, 1, 1 }, { 1, 0, 0, 0 }, { 0, 1, 1, 1 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 }
    };
    private final double[][] checkFour = {
        { 1, 0, 1, 0, 1, 0, 1 }, { 0, 1, 1, 0, 0, 1, 1 }, { 0, 0, 0, 1, 1, 1, 1 }
    };
    double[] transmitted;
    double[] transWithParity;
    
    private void hamming74 () {
        double[] trans = {0, 0, 0, 0, 0, 0, 0};
        
        for (int i = 0; i < trans.length; i++) {
            for (int j = 0; j < 4; j++) trans[i] += this.generatorFour[i][j] * message[j];
            trans[i] = trans[i] % 2;
        }
        
        this.transmitted = trans;
    }
    
    private void addParity8 () {
        double sum = 0;
        double[] withParity = new double[8];
        
        for (int i = 0; i < this.transmitted.length; i++) {
            sum += this.transmitted[i];
            
            withParity[i] = this.transmitted[i];
        }
        
        if ((sum % 2) == 0) {
            withParity[7] = 0;
        } else {
            withParity[7] = 1;
        }
        
        this.transWithParity = withParity;
    }
    
    public double[] checkError (double[] received) {
        this.checkLength(received, this.transmitted.length);
        
        double[] syndrome = {0, 0, 0};
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                syndrome[i] += this.checkFour[i][j] * received[j];
            }
        }
        
        return syndrome;
    }
    
    public double[] getCoded () {
        return this.transmitted;
    }
    
    public double[] getCodedWithParity () {
        return this.transWithParity;
    }
    
    private void checkLength (double[] array, int length) {
        if (array.length != length) throw new IllegalArgumentException("Invalid array length");
    }
    
    private void checkBinary (double[] array) {
        for (int i = 0; i < array.length; i++) {
            if ((array[i] != 0) && (array[i] != 1)) {
                throw new IllegalArgumentException("Input array must contain only zeros and ones.");
            }
        }
    }
}
