package classes.signals;

import java.util.Arrays;

public class CarrierSignal extends Signal {
    public CarrierSignal(WaveType wavetype, int samples, double frequency, double phase) {
        super(samples);
        
        this.frequency = frequency;
        this.wavetype = wavetype;
        this.phase = phase;
        
        makeCarrier();
    }

    private final double frequency;
    private final WaveType wavetype;
    private final double phase;

    public double getFrequency() {
        return frequency;
    }
    
    private void makeCarrier() {
        double[] carrier = new double[this.getLength()];
        
        switch (wavetype) {
            case SIN:
                for (int i = 0; i < this.getLength(); i++) {
                    carrier[i] = Math.sin((2 * Math.PI * frequency * i / this.getLength()) + phase);
                }
                break;
            case COS:
                for (int i = 0; i < this.getLength(); i++) {
                    carrier[i] = Math.cos((2 * Math.PI * frequency * i / this.getLength()) + phase);
                }
                break;
            case SQUARE:
                for (int i = 0; i < frequency; i++) {
                        for (
                                int j = (int) (this.getLength() * i / frequency); 
                                j < ((this.getLength() * i / frequency) + this.getLength() / (frequency * 2)); 
                                j++) {
                            carrier[j] = 1;
                        }
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        
        this.setSignal(carrier);
    }
    
    public static void main(String[] args) {
        CarrierSignal carr = new CarrierSignal(WaveType.SQUARE, 2048, 8, 0);
        
        System.out.println(Arrays.toString(carr.getSignal()));
    }
}
