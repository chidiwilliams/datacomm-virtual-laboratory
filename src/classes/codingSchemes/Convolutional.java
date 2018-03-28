/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.codingSchemes;

import java.util.Arrays;

/**
 * One-input convolutional coding
 * 
 * @author chidi
 */
public class Convolutional {
    int[] state;
    int[][] outputMatrix;
    int output;
    int width;
    
    String symbol;
    final String[] alphabet = new String[]{
        "a", "b", "c", "d", "e", "f", "g", "h", "i", 
        "j", "k", "l", "m", "n", "o", "p", "q", "r", 
        "s", "t"," u", "v", "w", "x", "y", "z", "!",
        "@", "#", "$", "%", "^", "&", "*", "(", ")"
    };
    
    /**
     *
     * @param outputMatrix
     */
    public Convolutional(int[][] outputMatrix) {
        this.state = new int[outputMatrix[0].length];
        this.outputMatrix = outputMatrix;
        this.width = outputMatrix[0].length;
        this.symbol = this.makeSymbol();
    }
    
    public Convolutional(String symbol) {
        this.symbol = symbol;
        int[][] opMat = this.makeMatrix();
        this.outputMatrix = opMat;
        this.state = new int[opMat[0].length];
    }
    
    private int xor(int x, int y) {
        return (x + y) % 2;                                           

    }
    
    private int[][] makeMatrix() {
        String[] symbols = this.symbol.split("");
        this.width = Integer.parseInt(symbols[0]);
        
        String[] newsymbols = new String[symbols.length - 1];
        
        for (int i = 0; i < symbols.length; i++) {
            if (i > 0) {
                newsymbols[i - 1] = symbols[i];
            }
        }
        
        String[] bits = new String[newsymbols.length];
        
        for (int i = 0; i < newsymbols.length; i++) {
            int letterPosition = Arrays.asList(alphabet).indexOf(newsymbols[i].toLowerCase());
            
            int bit = Integer.parseInt(Integer.toBinaryString(letterPosition));
            
            bits[i] = String.format("%0" + width + "d", bit);
        }
        
        int[][] op = new int[newsymbols.length][width];
        
        for (int i = 0; i < newsymbols.length; i++) {
            String[] bin = bits[i].split("");
            
            for (int j = 0; j < width; j++) {
                op[i][j] = Integer.parseInt(bin[j]);
            }
        }
        
        return op;
    }
    
    private String makeSymbol() {
        StringBuilder build = new StringBuilder();
        
        build.append(width);
        
        for (int[] outputMatrix1 : outputMatrix) {
            int value = 0;
            for (int j = 0; j < outputMatrix1.length; j++) {
                value += outputMatrix1[j] * Math.pow(2, outputMatrix1.length - 1 - j);
            }
            build.append(alphabet[value].toUpperCase());
        }
        
        return build.toString();
    }
   
    private int[] sendInput(int input) {
        for (int i = state.length; i >= 2; i--) {
            state[i-1] = state[i-2];
        }
        
        state[0] = input;
        
        int[] outputs = new int[this.num_outputs()];
        
        for (int i = 0; i < this.num_outputs(); i++) {
            for (int j = 0; j < outputMatrix[0].length; j++) {
                outputs[i] = xor(outputs[i], outputMatrix[i][j] * state[j]);
            }
        }
        
        return outputs;
    }
    
    public int[][] sendInputStream(int[] inputStream) {
        int[][] outputStream = new int[inputStream.length][num_outputs()];
        
        for (int i = 0; i < inputStream.length; i++) {
            outputStream[i] = sendInput(inputStream[i]);
        }
        
        return outputStream;
    }
    
    public int[] computeWeights(int[] output, int[] check1, int[] check2) {
        if (output.length != check1.length) {
            throw new IllegalArgumentException();
        }
        
        if (check1.length != check2.length) {
            throw new IllegalArgumentException();
        }
        
        int[] weights = new int[2];
        
        for (int i = 0; i < output.length; i++) {
            if (output[i] != check1[i]) {
                weights[0]++;
            }
            if (output[i] != check2[i]) {
                weights[1]++;
            }
        }
        
        return weights;
    }
    
    public int checkWeight(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            throw new IllegalArgumentException();
        }
        
        int weight = 0;
        
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                weight++;
            }
        }
        
        return weight;
    }
    
    public int[] decode(int[][] outputStream, boolean changeState) {
        int[][] combinations = new int[(int) Math.pow(2, outputStream.length)][outputStream.length];
        int[] weights = new int[(int) Math.pow(2, outputStream.length)];
        int[] saved_state = new int[this.state.length];
        
        System.arraycopy(this.state, 0, saved_state, 0, this.state.length);
        
        for (int i = 0; i < combinations.length; i++) {
            int bit = Integer.parseInt(Integer.toBinaryString(i));
            
            String[] expanded_bit = String.format("%0" + outputStream.length + "d", bit).split("");
            
            for (int j = 0; j < expanded_bit.length; j++) {
                combinations[i][j] = Integer.parseInt(expanded_bit[j]);
            }
        }
        
        for (int i = 0; i < combinations.length; i++) {
            int[][] opMatrix = sendInputStream(combinations[i]);
            
            for (int j = 0; j < opMatrix.length; j++) {
                weights[i] += checkWeight(outputStream[j], opMatrix[j]);
            }
            
            System.arraycopy(saved_state, 0, this.state, 0, this.state.length);
        }
        
        int min_weight = weights[0];
        int min_position = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] < min_weight) {
                min_weight = weights[i];
                min_position = i;
            }
        }
        
        int bit = Integer.parseInt(Integer.toBinaryString(min_position));
        String[] expanded_bit = String.format("%0" + outputStream.length + "d", bit).split("");
        
        int[] received = new int[expanded_bit.length];
        for (int i = 0; i < received.length; i++) {
            received[i] = Integer.parseInt(expanded_bit[i]);
        }
        
        if (changeState) {
            sendInputStream(received);
        }
        
        return received;
    }
    
    public int[][] correct(int[][] outputStream) {
        int[] decoded = decode(outputStream, false);
        int[] saved_state = new int[this.state.length];
        
        System.arraycopy(this.state, 0, saved_state, 0, this.state.length);
        
        int[][] corrected = sendInputStream(decoded);
        
        System.arraycopy(saved_state, 0, this.state, 0, this.state.length);

        return corrected;
    }
    
    public String getSymbol() {
        return this.symbol;
    }
    
    public int[][] getOutputMatrix() {
        return this.outputMatrix;
    }
    
    public int[] getState() {
        return this.state;
    }
    
    public void setState(int[] stateMatrix) {
        this.state = stateMatrix;
    }
    
    public int num_outputs() {
        return outputMatrix.length;
    }
}
