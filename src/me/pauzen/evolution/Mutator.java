package me.pauzen.evolution;/*
 *  Created by Filip P. on 12/27/15 2:23 AM.
 */

import me.pauzen.neuralnetwork.NeuralNetwork;
import me.pauzen.neuralnetwork.activationfunction.ActivationFunction;
import me.pauzen.neuralnetwork.layer.Layer;
import me.pauzen.neuralnetwork.neuron.Neuron;

import java.util.Map;
import java.util.Random;

public class Mutator {

    private static final Random RANDOM = new Random();

    public NeuralNetwork mutate(NeuralNetwork neuralNetwork, float maxDeviation, boolean insertNeuron, boolean insertBias) {
        NeuralNetwork newNetwork = new NeuralNetwork(neuralNetwork.getLayers(), ActivationFunction.ARCTAN);
        for (Map.Entry<Neuron, Map<Neuron, Float>> inputWeights : neuralNetwork.getSynapticInputWeights().entrySet()) {
            float deviation = (float) (Math.random() * maxDeviation - maxDeviation / 2);

            for (Map.Entry<Neuron, Float> weightEntry : inputWeights.getValue().entrySet()) {
                newNetwork.getInputWeights(inputWeights.getKey()).put(weightEntry.getKey(), weightEntry.getValue() + deviation);
            }
        }

        for (Layer layer : neuralNetwork.getLayers()) {
            newNetwork.getLayers().clear();
        }

        if (insertBias && (Math.random() <= 0.3 / maxDeviation / 20)) {
            newNetwork.getLayers().get(RANDOM.nextInt(newNetwork.getLayers().size() - 1));
        }

        if (insertNeuron && (Math.random() <= 0.3 / maxDeviation / 20)) {
            newNetwork.getLayers().get(RANDOM.nextInt(newNetwork.getLayers().size() - 1)).addNeuron(new Neuron(0.0F));
        }

        return newNetwork;
    }
}
