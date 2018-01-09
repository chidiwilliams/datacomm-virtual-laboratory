package simulator.classes.impairments;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class AWGN {
    private double amplitude = 1;
    double[] noise;
    
    public AWGN() {}

    public double[] generateNoise(double amplitude, int samples) {
        this.amplitude = amplitude;
        double[] output = new double[samples];

        Random rnd = new Random();
        IntStream.range(0, output.length)
                .forEach(i -> output[i] = rnd.nextGaussian() * amplitude);

        noise = output;
        
        return noise;
    }

    public double[] setAmplitude(double amplitude) {
        this.amplitude = amplitude;

        /* Scaling */
        IntStream.range(0, noise.length)
                .forEach(i -> noise[i] *= amplitude);
        
        return noise;
    }

    public double getAmplitude() {
        return amplitude;
    }

    double[] writeNoise(int amplitude, int samples, String filepath) {
        AWGN awgn = new AWGN();
        double[] noiseSignal = awgn.generateNoise(amplitude, samples);
        
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            for (double aNoise : noiseSignal) {
                bufferedWriter.write(Double.toString(aNoise));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {

        }

        return noiseSignal;
    }

    public double[] readNoise(String filepath, int samples) {
        String filename = (filepath.equals("")) ? "C:\\xampp\\htdocs\\FYP\\src\\simulator\\other\\noise.txt" : filepath;
        ArrayList<Double> samplesList = new ArrayList<>();
        double[] op = new double[samples];
        String line;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            while((line = bufferedReader.readLine()) != null) samplesList.add(Double.parseDouble(line));
            Arrays.setAll(op, samplesList::get);
        } catch (IOException e) {
        }

        this.noise = op;
        
        return noise;
    }
}