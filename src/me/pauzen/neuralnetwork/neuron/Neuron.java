package me.pauzen.neuralnetwork.neuron;


import me.pauzen.neuralnetwork.NeuralNetwork;
import me.pauzen.neuralnetwork.activationfunction.ActivationFunction;
import me.pauzen.neuralnetwork.layer.Layer;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Neuron {

    private final float                  threshold;

    public Neuron(float threshold) {
        this.threshold = threshold;
    }

    public float getOutputWeight(NeuralNetwork neuralNetwork, Neuron neuron) {
        System.out.println(getClass().getName());
        System.out.println("a " + neuralNetwork.getInputWeights(neuron));
        System.out.println(neuron.toString(neuralNetwork));
        System.out.println(toString(neuralNetwork));
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return neuralNetwork.getInputWeights(neuron).get(this);
    }

    public float calculate(float[] inputs, Function<Float, Float> outputStrengthFunction) {
        float total = 0.0F;
        for (float input : inputs) {
            total += input;
        }
        float result = outputStrengthFunction.apply(total);
        return result >= threshold ? result : 0.0F;
    }

    public Neuron clone() {
        return new Neuron(threshold);
    }
    
    public String toString(NeuralNetwork neuralNetwork) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (; i < neuralNetwork.getLayers().size(); i++) {
            if (neuralNetwork.getLayers().get(i).getNeurons().contains(this)) {
                break;
            }
        }
        Map<Neuron, Float> inputWeights = neuralNetwork.getInputWeights(this);
        Set<Map.Entry<Neuron, Float>> inputs = inputWeights.entrySet();
        int j = 0;
        for (Map.Entry<Neuron, Float> input : inputs) {
            builder.append(j++).append(": ").append(input.getValue());
        }
        return "(type = " + getClass().getSimpleName() + ", layer = " + i + ", inputWeights = {" + builder.toString() + "})";
    }
    
    public String toComplexString(Layer layer, NeuralNetwork neuralNetwork) {
        float v = neuralNetwork.getValue(layer, this);
        String value = "(index = " + layer.getNeurons().indexOf(this) + ", outputValue = " + v + ", summatedValue = (";
        Set<Map.Entry<Neuron, Float>> entries = neuralNetwork.getInputWeights(this).entrySet();
        int i = 1;
        for (Map.Entry<Neuron, Float> entry : entries) {
            value += layer.getPreviousLayer().getNeurons().indexOf(entry.getKey()) + i == entries.size() ? "" : " + ";
            i++;
        }
        value += " = " + ActivationFunction.ARCTAN_INVERSE.getFunction().apply(v) + ")";
        return value;
    }
}