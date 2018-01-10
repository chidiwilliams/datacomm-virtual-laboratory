/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.codingSchemes;

import java.util.Arrays;
import simulator.classes.signals.Signal;

/**
 *
 * @author HP
 */
public class Hamming {
    public Hamming (double[] input) {
        setInput(input);
    }
    
    public Hamming() {
        
    }
    
    double[] message = new double[4];
    boolean has_input = false;
    private final double[][] generatorFour = {
        { 1, 1, 0, 1 }, 
        { 1, 0, 1, 1 }, 
        { 1, 0, 0, 0 }, 
        { 0, 1, 1, 1 }, 
        { 0, 1, 0, 0 }, 
        { 0, 0, 1, 0 }, 
        { 0, 0, 0, 1 }
    };
    private final double[][] checkFour = {
        { 1, 0, 1, 0, 1, 0, 1 }, 
        { 0, 1, 1, 0, 0, 1, 1 }, 
        { 0, 0, 0, 1, 1, 1, 1 }
    };
    private final double[][] decodeFour = {
        { 0, 0, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 1, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 0 },
        { 0, 0, 0, 0, 0, 0, 1 }
    };
    double[] transmitted;
    double[] transWithParity;
    
    public final void setInput(double[] input) {
        has_input = true;
        
        checkIfBinary(input);
        
        if (input.length == 4) {
            message = input;
            
//            Perform Hamming(7, 4) on the four-bit message. Save to variable transmitted. Do parity bit also.
            hamming74();
            addParity8();
        } else {
            throw new IllegalArgumentException("Cannot compute the Hamming code of this array.");
        }
    }
    
    private void hamming74() {
        double[] trans = {0, 0, 0, 0, 0, 0, 0};
        
        for (int i = 0; i < trans.length; i++) {
            for (int j = 0; j < 4; j++) trans[i] += this.generatorFour[i][j] * message[j];
            trans[i] = trans[i] % 2;
        }
        
        this.transmitted = trans;
    }
    
    private void addParity8() {
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
    
    public double[] syndrome (double[] received) {
        this.checkLength(received, 7);
        
        double[] syndrome = new double[3];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
//              For some reason, 2 - i works instead of i !!!
                syndrome[2 - i] += this.checkFour[i][j] * received[j];
            }
            
            syndrome[2 - i] = syndrome[2 - i] % 2;
        }
        
        return syndrome;
    }
    
    public int syndromeNum(double[] received) {
        double[] syndrome = syndrome(received);
        
        int error = -1;
                
        for (int i = 0; i < syndrome.length; i++) {
            error += syndrome[i] * Math.pow(2, 2 - i);
        }
        
        return error;
    }
    
    public double[] decode(double[] received) {
        this.checkLength(received, 7);
        
        double[] corrected = correct(received);
        
        double[] decoded = new double[4];
        for (int i = 0; i < decoded.length; i++) {
            for (int j = 0; j < corrected.length; j++) {
                decoded[i] += decodeFour[i][j] * corrected[j];
            }
        }
        
        return decoded;
    }
    
    public double[] correct(double[] received) {
        this.checkLength(received, 7);
        
        double[] corrected = received;
        
        int error = syndromeNum(received);
        if (error > -1) {
            corrected[error] = (received[error] == 0) ? 1 : 0;
        }
        
        return corrected;
    }
    
    public double[] getCoded () {
        if (has_input) {
            return this.transmitted;
        } else {
            throw new NullPointerException("No input message sent.");
        }
    }
    
    public double[] getCodedWithParity () {
        if (has_input) {
            return this.transWithParity;
        } else {
            throw new NullPointerException("No input message sent.");
        }
    }
    
    private void checkLength (double[] array, int length) {
        if (array.length != length) throw new IllegalArgumentException("Invalid array length");
    }
    
    private void checkIfBinary (double[] array) {
        for (int i = 0; i < array.length; i++) {
            if ((array[i] != 0) && (array[i] != 1)) {
                throw new IllegalArgumentException("Input array must contain only zeros and ones.");
            }
        }
    }
}
