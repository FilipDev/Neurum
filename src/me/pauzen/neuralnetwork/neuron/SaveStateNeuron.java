/*
 *  Created by Filip P. on 5/24/16 12:01 AM.
 */

package me.pauzen.neuralnetwork.neuron;

import java.util.function.Function;

public class SaveStateNeuron extends Neuron {

    private float value;

    public SaveStateNeuron() {
        super(0.0F);
    }

    @Override
    public float calculate(float[] inputs, Function<Float, Float> outputStrengthFunction) {
        return value = super.calculate(inputs, outputStrengthFunction);
    }

    public float getValue() {
        return value;
    }
}
