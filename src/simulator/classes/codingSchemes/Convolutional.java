/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.codingSchemes;

import java.util.Arrays;

/**
 * One-input convolutional coding
 * 
 * @author chidi
 */
public class Convolutional {
    int[] inputNodes;
    int[][] outputMatrix;
    int output;
    
    /**
     *
     * @param num_registers
     * @param outputMatrix
     */
    public Convolutional(int num_registers, int[][] outputMatrix) {
        // outputMatrix[0].length === num_registers + 1
        // outputMatrix.length is the number of outputs
        
        this.inputNodes = new int[num_registers + 1];
        this.outputMatrix = outputMatrix;
    }
    
    public static int xor(int x, int y) {
        return (x + y) % 2;
    }
    
    public int[] sendInput(int input) {
//      Shift input registers
        for (int i = inputNodes.length; i >= 2; i--) {
            inputNodes[i-1] = inputNodes[i-2];
        }
        
//      Set first register to current input
        inputNodes[0] = input;
        
        return computeOutput();
    }
    
    public int[][] sendInputStream(int[] inputStream) {
        int[][] outputStream = new int[inputStream.length + num_registers()][num_outputs()];
        
        for (int i = 0; i < inputStream.length; i++) {
            outputStream[i] = sendInput(inputStream[i]);
        }
        
        for (int i = inputStream.length; i < inputStream.length + num_registers(); i++) {
            outputStream[i] = sendInput(0);
        }
        
        return outputStream;
    }
    
    public int num_outputs() {
        return outputMatrix.length;
    }
    
    public int num_registers() {
        return inputNodes.length - 1;
    }
    
    private int[] computeOutput() {
        int[] outputs = new int[this.num_outputs()];
        
        for (int i = 0; i < this.num_outputs(); i++) {
            for (int j = 0; j < outputMatrix[0].length; j++) {
                outputs[i] = xor(outputs[i], outputMatrix[i][j] * inputNodes[j]);
            }
        }
        
        return outputs;
    }
    
    public static void main(String[] args) {
        Convolutional conv = new Convolutional(3, 
                new int[][]{
                    {1, 0, 1, 1}, {1, 1, 0, 1}
                });
        
        int[][] op = conv.sendInputStream(new int[]{1, 0, 1, 1, 0});
        
        for (int[] op1 : op) {
            System.out.println(Arrays.toString(op1));
        }
    }
}
