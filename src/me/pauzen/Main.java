package me.pauzen;/*
 *  Created by Filip P. on 12/26/15 9:06 PM.
 */

import me.pauzen.neuralnetwork.NeuralNetwork;
import me.pauzen.neuralnetwork.activationfunction.ActivationFunction;
import me.pauzen.neuralnetwork.neuron.InputNeuron;
import me.pauzen.neuralnetwork.neuron.OutputNeuron;

public class Main {

    public static void main(String[] args) {
        InputNeuron<Float> floatInput = new InputNeuron<>((value) -> value, 0.6F);
        OutputNeuron output = new OutputNeuron();
        NeuralNetwork neuralNetwork = new NeuralNetwork(new InputNeuron[]{floatInput}, new int[]{1}, new OutputNeuron[]{output}, 0.0F, ActivationFunction.ARCTAN);
        neuralNetwork.forward();
        System.out.println(neuralNetwork.toString());
        NeuralNetwork reverse = neuralNetwork.reverse();
        reverse.forward();
        System.out.println(reverse.toString());

    }

}
