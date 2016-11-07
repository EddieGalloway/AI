
public class Connection {
    double weight = 0;
    double deltaWeight = 0;
 
    final Neuron fromNeuron;
    final Neuron toNeuron;
    int counter = 0;
    final int connectionID; 
 
    public Connection(Neuron fromN, Neuron toN, int id) {
        fromNeuron = fromN;
        toNeuron = toN;
        connectionID = id;
        counter++;
    }
 
    public double getWeight() {
        return weight;
    }
 
    public void setWeight(double w) {
        weight = w;
    }
 
    public void setDeltaWeight(double w) {
        deltaWeight = w;
    }
 
    public Neuron getFromNeuron() {
        return fromNeuron;
    }
 
    public Neuron getToNeuron() {
        return toNeuron;
    }
}
