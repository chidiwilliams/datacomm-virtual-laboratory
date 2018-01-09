package simulator.classes.signals;

public class BasebandSignal extends Signal {
    public BasebandSignal(int samples) {
        super(samples);
    }
    
    @Override
    public void setSignal(double[] signal) {
    /* Save snapshot of current signal values in case invalid values are inputted. */

//      Check length of input signal
        if (signal.length != this.getLength()) {
            throw new IllegalArgumentException("Invalid signal length");
        }

//      Check for binary inputs
        for (int i = 0; i < this.getLength(); i++) {
            if (!(signal[i] == 0 || signal[i] == 1)) {
                throw new IllegalArgumentException("InputSignal values must be 0 or 1");
            }
        }
        
//      Save each value
        for (int i = 0; i < this.getLength(); i++) {
            this.setSignalValue(i, signal[i]);
        }
    }

    public double[] expandTo(int samples) {
        double[] output = new double[samples];

        if ((samples % this.getLength()) == 0) {
            for (int i = 0; i < this.getLength(); i++)
                for (int j = (samples/this.getLength()) * i; j < (samples/this.getLength()) * (i + 1); j++)
                    output[j] = this.getSignalValue(i);
        } else {
            throw new IllegalArgumentException("Cannot expand signal. New bit length must be a multiple of the original bit length.");
        }

        return output;
    }
}
