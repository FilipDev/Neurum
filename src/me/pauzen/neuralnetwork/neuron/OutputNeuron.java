package me.pauzen.neuralnetwork.neuron;

public class OutputNeuron extends SaveStateNeuron {

    public OutputNeuron() {
        super();
    }
    
    public InputNeuron<OutputNeuron> toInput() {
        return new InputNeuron<>((val) -> this.getValue(), this);
    }
}
