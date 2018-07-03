package classes.signals;

public class WaveSignal extends Signal {
    public WaveSignal(WaveSignalType wavetype, int samples, double frequency, double phase) {
        super(samples);
        
        this.frequency = frequency;
        this.wavetype = wavetype;
        this.phase = phase;
        
        makeCarrier();
    }

    private final double frequency;
    private final WaveSignalType wavetype;
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
            case TRIANGULAR:                
                for (int i = 0; i < frequency; i++) {
                    int start = (int) (i / frequency * 2048);
                    
                    int limit = (int) (2048 / frequency);
                    int limit2 = (int) (2048 / (frequency * 2));
                    int limit4 = (int) (2048 / (frequency * 4));
                    
                    for (int j = start; j < start + limit4; j++) {
                        carrier[j] = (j - start) * Math.pow(limit4, -1);
                    }
                    
                    for (int j = start + limit4; j < start + limit2; j++) {
                        carrier[j] = (-1 * (j - (start + limit4)) * Math.pow(limit4, -1)) + 1;
                    }
                    
                    for (int j = start + limit2; j < start + limit2 + limit4; j++) {
                        carrier[j] = -1 * (j - (start + limit2)) * Math.pow(limit4, -1);
                    }
                    
                    for (int j = start + limit2 + limit4; j < start + limit; j++) {
                        carrier[j] = ((j - (start + limit2 + limit4)) * Math.pow(limit4, -1)) - 1;
                    }
                }
                
                for (int i = 0; i < carrier.length; i++) {
                    System.out.println(carrier[i]);
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        
        this.setSignal(carrier);
    }
}
