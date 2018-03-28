package simulator.classes.signals;

import java.util.Arrays;

public class CarrierSignal extends Signal {
    public CarrierSignal(int samples, int frequency) {
        super(samples);
        makeCarrier(frequency);
    }

    private int frequency;

    public int getFrequency() {
        return frequency;
    }

    private void makeCarrier(int frequency) {
        this.frequency = frequency;

        double[] carrier = new double[this.getLength()];

        Arrays.setAll(carrier, i -> Math.sin(2 * Math.PI * frequency * i / this.getLength()));

        this.setSignal(carrier);
    }
}
