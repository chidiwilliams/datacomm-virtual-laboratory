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
public enum filterType {
    LPF, BPF, HPF;

    public static final int SIZE = Integer.SIZE;

    public int getValue() {
        return this.ordinal();
    }

    public static filterType forValue(int value) {
        return values()[value];
    }
}
