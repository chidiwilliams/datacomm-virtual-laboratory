package classes.signals;

import classes.helpers.Complex;

import java.util.stream.IntStream;

public class ComplexSignal extends Signal {
    public ComplexSignal(int samples) {
        super(samples);
    }

    private Complex[] jSignal;

    public Complex[] getJSignal() {
        return jSignal;
    }

    public ComplexSignal setJSignal(Complex[] jSignal) {
        this.jSignal = jSignal;
        return this;
    }

    private Complex getJSignalValue(int index) {
        return this.jSignal[index];
    }

    public double[] magnitude() {
        double[] mag = IntStream.range(0, this.jSignal.length).mapToDouble(i -> Math.round(this.getJSignalValue(i).abs())).toArray();

        return mag;
    }

    public double[] real() {
        return IntStream.range(0, this.jSignal.length).mapToDouble(i -> this.getJSignalValue(i).re()).toArray();
    }

    void setSignal(Complex[] signal) {
        this.jSignal = signal;
    }

    void setSignalValue(int index, Complex value) {
        this.jSignal[index] = value;
    }

    public int getLength() {
        return jSignal.length;
    }
}
