package me.pauzen.neuralnetwork.neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InputNeuron<T> extends SaveStateNeuron {

    private final Function<T, Float> valueGetter;
    private final T                  object;

    public InputNeuron(Function<T, Float> valueGetter, T object) {
        this.valueGetter = valueGetter;
        this.object = object;
    }
    
    public OutputNeuron toOutput() {
        return new OutputNeuron();
    }
    
    public static <T> List<InputNeuron> create(List<T> objects, Function<T, Float> valueGetter) {
        List<InputNeuron> inputNeurons = new ArrayList<>();
        for (T object : objects) {
            inputNeurons.add(new InputNeuron<>(valueGetter, object));
        }
        return inputNeurons;
    }

    public float applyFunction() {
        return valueGetter.apply(object);
    }
}
