/*
 *  Created by Filip P. on 5/20/16 10:44 PM.
 */

package me.pauzen.neuralnetwork.activationfunction;

import java.util.function.Function;

public enum ActivationFunction {

    ARCTAN(value -> (float) (Math.atan((double) value) / Math.PI + 0.5F)),
    ARCTAN_INVERSE(value -> (float) (Math.tan((double) value - 0.5D) * Math.PI));

    private final Function<Float, Float> function;

    private ActivationFunction(Function<Float, Float> function) {
        this.function = function;
    }

    public Function<Float, Float> getFunction() {
        return function;
    }
}
