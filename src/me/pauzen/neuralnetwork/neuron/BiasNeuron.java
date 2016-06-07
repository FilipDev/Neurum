/*
 *  Created by Filip P. on 5/23/16 10:17 PM.
 */

package me.pauzen.neuralnetwork.neuron;

import java.util.function.Function;

public class BiasNeuron extends Neuron {

    public BiasNeuron() {
        super(0.0F);
    }

    @Override
    public float calculate(float[] inputs, Function<Float, Float> outputStrengthFunction) {
        return 1.0F;
    }
}
