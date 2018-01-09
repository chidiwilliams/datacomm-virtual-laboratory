/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.signals;

import java.util.stream.IntStream;
import simulator.classes.libraries.Complex;
import simulator.classes.libraries.FFT;

/**
 *
 * @author HP
 */
public class Signal {
    public Signal(int samples) {
        signal = new double[samples];
    }

    private double[] signal;
    private String name = "Signal";

    public double[] getSignal() {
        return signal;
    }

    public double getSignalValue(int index) {
        return this.signal[index];
    }

    public void setSignal(double[] signal) {
        this.signal = signal;
    }

    public void setSignalValue(int index, double value) {
        this.signal[index] = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return this.signal.length;
    }

    public void print(String header, int printMode) {
        System.out.println(header);
        for (int i = 0; i < signal.length; i++) {
            double value = signal[i];
            if (printMode == 0) {
                System.out.print(value + " ");
            } else {
                System.out.println(String.format("%-7s", (i + ".")) + value);
            }
        }
        
        System.out.println("");
    }
}
