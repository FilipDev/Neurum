package me.pauzen.neuralnetwork.layer;

/*
 *  Created by Filip P. on 12/26/15 11:40 PM.
 */

import me.pauzen.neuralnetwork.NeuralNetwork;
import me.pauzen.neuralnetwork.neuron.InputNeuron;
import me.pauzen.neuralnetwork.neuron.Neuron;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private final List<Neuron> neurons = new ArrayList<>();
    private final Layer previous;

    public Layer(Layer previous) {
        this.previous = previous;
    }

    public void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }

    public void run(NeuralNetwork network) {
        if (previous != null) {
            for (Neuron neuron : getNeurons()) {
                neuron.calculate(getInputArray(network, neuron), network.getActivationFunction());
            }
        }
    }

    public float[] getInputArray(NeuralNetwork network, Neuron neuron) {
        if (neuron instanceof InputNeuron) {
            return new float[]{((InputNeuron) neuron).applyFunction()};
        }
        float[] values = new float[previous.getNeurons().size()];
        for (int i = 0; i < previous.getNeurons().size(); i++) {
            Neuron previousNeuron = previous.getNeurons().get(i);
            values[i] = previousNeuron.getOutputWeight(network, neuron) * network.getValue(getPreviousLayer(), previousNeuron);
        }
        return values;
    }

    public Layer getPreviousLayer() {
        return previous;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public Layer clone(Layer previous) {
        Layer layer = new Layer(previous);
        layer.neurons.addAll(neurons.subList(0, neurons.size()));
        return layer;
    }
    
    public Layer clone() {
        return clone(previous);
    }

    public float[] toValueArray(NeuralNetwork network) {
        List<Neuron> neuronList = getNeurons();
        float[] values = new float[neuronList.size()];
        for (int i = 0; i < neuronList.size(); i++) {
            values[i] = network.getValue(this, neuronList.get(i));
        }
        return values;
    }
}
