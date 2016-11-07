import java.util.Hashtable;


public class Node {

	int nodeID;
	double nodeOutput;
	InputAndWeight[] incoming;
	//Hashtable<Integer, InputAndWeight[]> completeNodeInfo = new Hashtable<Integer, InputAndWeight[]>();
	
	public Node(int ID, InputAndWeight[] inputsAndWeights){
		nodeID = ID;
		incoming = inputsAndWeights;
//		nodeOutput = calcOutput(incoming);
	}

	public double[] getNodeInput() {
		double[] inputs = new double[incoming.length];
		
		for(int i = 0; i < incoming.length; i++){
			inputs[i] = incoming[i].getInput();
		}
		return inputs;
	}

	public void setNodeInput(double[] nodeInput) {
		for(int i = 0; i < incoming.length; i++){
			incoming[i].setInput(nodeInput[i]);
		}
	}

	public double getNodeOutput() {
		return nodeOutput;
	}

	public void setNodeOutput(double nodeOutput) {
		this.nodeOutput = nodeOutput;
	}

	public double[] getNodeWeight() {
		double[] weights = new double[incoming.length];
		
		for(int i = 0; i < incoming.length; i ++){
			weights[i] = incoming[i].getWeight();
		}
		
		return weights;
	}

	public void setNodeWeight(double[] nodeWeight) {
		for(int i = 0; i < incoming.length; i++){
			incoming[i].setWeight(nodeWeight[i]);
		}
	}
	
	//Need to move this method or somehow rework so input nodes can direct output
//	public double calcOutput(InputAndWeight[] inputAndWeights){
//		double output = 0.0;
//		double net = 0.0;
//		
//		for(int i = 0; i < inputAndWeights.length; i++){
//			net += inputAndWeights[i].getInput() * inputAndWeights[i].getWeight();
//		}
//		
//		output = 1/(1 + Math.pow(Math.E, -net));
//		
//		return output;
//	}
}
