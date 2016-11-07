import java.util.ArrayList;
import java.util.HashMap;

public class Neuron { 
    final int NEURON_ID;
    double bias = 1;
    double output;
    
    Connection biasConnection; 
    ArrayList<Connection> incoming = new ArrayList<Connection>();
    HashMap<Integer,Connection> connectionMap = new HashMap<Integer,Connection>();
     
    public Neuron(int id){        
        NEURON_ID = id;
    }
    
    public void calculateOutput(){
    	double net = 0.0;
        for(Connection con : incoming){
            Neuron fromNeuron = con.getFromNeuron();
            double weight = con.getWeight();
            double prevOutput = fromNeuron.getOutput(); //output from previous layer
             
            net += (weight * prevOutput);
        }
        net += (biasConnection.getWeight()*bias);
         
        output = 1.0 / (1.0 + Math.pow(Math.E, -net));
    }
     
    public void addIncoming(ArrayList<Neuron> inNeurons){
        for(Neuron n: inNeurons){
            Connection conn = new Connection(n,this,n.NEURON_ID);
            incoming.add(conn);
            connectionMap.put(n.NEURON_ID, conn);
        }
    }
     
    public Connection getConnection(int neuronIndex){
        return connectionMap.get(neuronIndex);
    }
 
    public void addInConnection(Connection con){
        incoming.add(con);
    }
    public void addBiasConnection(Neuron n){
        Connection con = new Connection(n,this,n.NEURON_ID);
        biasConnection = con;
        incoming.add(con);
    }
    public ArrayList<Connection> getAllincoming(){
        return incoming;
    }
     
    public double getBias() {
        return bias;
    }
    public double getOutput() {
        return output;
    }
    public void setOutput(double output){
        this.output = output;
    }
}
