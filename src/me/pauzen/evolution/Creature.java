/*
 *  Created by Filip P. on 5/21/16 6:28 PM.
 */

package me.pauzen.evolution;

import me.pauzen.neuralnetwork.NeuralNetwork;
import me.pauzen.neuralnetwork.activationfunction.ActivationFunction;

public class Creature {

    private NeuralNetwork neuralNetwork;

    public Creature(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public Creature clone() {
        return new Creature(new NeuralNetwork(neuralNetwork, ActivationFunction.ARCTAN));
    }

}
