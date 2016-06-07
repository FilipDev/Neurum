package me.pauzen.neuralnetwork;/*
 *  Created by Filip P. on 12/27/15 12:16 AM.
 */

import me.pauzen.neuralnetwork.activationfunction.ActivationFunction;
import me.pauzen.neuralnetwork.layer.Layer;
import me.pauzen.neuralnetwork.neuron.InputNeuron;
import me.pauzen.neuralnetwork.neuron.Neuron;
import me.pauzen.neuralnetwork.neuron.OutputNeuron;
import me.pauzen.neuralnetwork.neuron.SaveStateNeuron;

import java.util.*;
import java.util.function.Function;

public class NeuralNetwork {
    private final List<Layer>                     layers               = new ArrayList<>();
    private final Map<Neuron, Map<Neuron, Float>> synapticInputWeights = new HashMap<>();
    private final Map<Neuron, Float>              values               = new HashMap<>();
    private final Function<Float, Float> activationFunction;

    public NeuralNetwork(InputNeuron[] inputNeurons, int[] neuronsPerHiddenLayer, OutputNeuron[] outputNeurons, float threshold, ActivationFunction activationFunction) {
        this.activationFunction = activationFunction.getFunction();
        Layer inputLevel = new Layer(null);
        Collections.addAll(inputLevel.getNeurons(), inputNeurons);
        layers.add(inputLevel);

        for (int neuronsPerLayer : neuronsPerHiddenLayer) {
            Layer layer = new Layer(getLastLayer());
            for (int i = 0; i < neuronsPerLayer; i++) {
                layer.addNeuron(new Neuron(threshold));
            }
            layers.add(layer);
        }

        Layer outputLevel = new Layer(getLastLayer());
        Collections.addAll(outputLevel.getNeurons(), outputNeurons);
        layers.add(outputLevel);
        insertWeights(0.5F);
    }

    public NeuralNetwork(List<Layer> layers, ActivationFunction activationFunction) {
        this.activationFunction = activationFunction.getFunction();
        this.layers.addAll(layers);
    }

    public NeuralNetwork(NeuralNetwork old, ActivationFunction activationFunction) {
        this.activationFunction = activationFunction.getFunction();
        for (Layer layer : old.layers) {
            layers.add(layer.clone());
        }
    }

    public void addNeuron(int layerIndex, Neuron neuron) {
        Layer layer = layers.get(layerIndex);
        Layer next = layers.get(layerIndex + 1);
        for (Neuron nextNeuron : next.getNeurons()) {
            this.synapticInputWeights.get(nextNeuron).put(neuron, 0.5F);
        }
        layer.addNeuron(neuron);
    }

    public float[] forward() {
        resetValues();
        Layer lastLayer = getLastLayer();
        lastLayer.run(this);
        return lastLayer.toValueArray(this);
    }

    public float[] backward() {
        forward();
        return reverse().forward();
    }

    public void insertWeights(Map<Neuron, Map<Neuron, Float>> synapticInputWeights) {
        this.synapticInputWeights.putAll(synapticInputWeights);
    }

    public void insertWeights(float defaultWeight) {
        for (int i = layers.size() - 1; i > 0; i--) {
            Layer layer = layers.get(i);
            for (Neuron neuron : layer.getNeurons()) {
                synapticInputWeights.put(neuron, new HashMap<>());
                Layer previousLayer = layer.getPreviousLayer();
                for (Neuron neuron1 : previousLayer.getNeurons()) {
                    synapticInputWeights.get(neuron).put(neuron1, defaultWeight);
                }
            }
        }
    }

    private Layer getLastLayer() {
        return layers.get(layers.size() - 1);
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public Map<Neuron, Map<Neuron, Float>> getSynapticInputWeights() {
        return synapticInputWeights;
    }

    public Map<Neuron, Float> getInputWeights(Neuron neuron) {
        System.out.println("getInputWeights = " + getSynapticInputWeights());
        return getSynapticInputWeights().getOrDefault(neuron, Collections.EMPTY_MAP);
    }

    public float getValue(Layer layer, Neuron neuron) {
        if (neuron instanceof SaveStateNeuron) {
            float value = ((SaveStateNeuron) neuron).getValue();
            values.put(neuron, value);
            return value;
        }
        values.putIfAbsent(neuron, neuron.calculate(layer.getInputArray(this, neuron), getActivationFunction()));
        return values.get(neuron);
    }

    public void resetValues() {
        values.clear();
    }

    public NeuralNetwork reverse() {
        List<Layer> newLayers = new ArrayList<>();
        {
            Layer layer = getLastLayer().clone(null);
            newLayers.add(layer);
            layer.getNeurons().clear();
            for (Neuron neuron : getLastLayer().getNeurons()) {
                layer.addNeuron(((OutputNeuron) neuron).toInput());
            }
        }
        for (int i = layers.size() - 2; i > 0; i--) {
            Layer previousLayer = i == layers.size() - 1 ? null : layers.get(i + 1);
            newLayers.add(layers.get(i).clone(previousLayer));
        }
        {
            Layer layer1 = getLayers().get(0);
            Layer layer = layer1.clone(newLayers.get(newLayers.size() - 1));
            newLayers.add(layer);
            layer.getNeurons().clear();
            for (Neuron neuron : layer1.getNeurons()) {
                layer.addNeuron(((InputNeuron) neuron).toOutput());
            }
        }
        NeuralNetwork neuralNetwork = new NeuralNetwork(newLayers, ActivationFunction.ARCTAN_INVERSE);
        for (Map.Entry<Neuron, Map<Neuron, Float>> neuronMapEntry : synapticInputWeights.entrySet()) {
            Map<Neuron, Float> value = neuronMapEntry.getValue();
            for (Map.Entry<Neuron, Float> neuronFloatEntry : value.entrySet()) {
                neuralNetwork.synapticInputWeights.putIfAbsent(neuronFloatEntry.getKey(), new HashMap<>());
                neuralNetwork.synapticInputWeights.get(neuronFloatEntry.getKey()).put(neuronMapEntry.getKey(), neuronFloatEntry.getValue());
            }
        }
        System.out.println("outputWeight = " + neuralNetwork.getLayers().get(1).getNeurons().get(0).getOutputWeight(neuralNetwork, neuralNetwork.getLastLayer().getNeurons().get(0)));
        return neuralNetwork;
    }
    
    private Map<Neuron, Float> invert(Neuron neuron, Map.Entry<Neuron, Float> inputWeight) {
        Neuron newInput = inputWeight.getKey();
        
    }

    @Override
    public String toString() {
        StringBuilder finalBuilder = new StringBuilder();
        for (int i = getLayers().size() - 1; i >= 0; i--) {
            Layer layer = getLayers().get(i);
            List<Neuron> neurons = layer.getNeurons();
            if (i == getLayers().size() - 1) {
                finalBuilder.append("--- Output Layer ---\n");
            } else if (i == 0) {
                finalBuilder.append("--- Input Layer ---\n");
            } else {
                finalBuilder.append("--- Hidden Layer ").append(i).append(" ---\n");
            }
            for (Neuron neuron : neurons) {
                finalBuilder.append(neuron.toComplexString(layer, this)).append("\n");
            }
        }
        return finalBuilder.toString();
    }

    public Function<Float, Float> getActivationFunction() {
        return activationFunction;
    }
}