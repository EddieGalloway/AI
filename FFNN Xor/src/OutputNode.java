
public class OutputNode extends Node{

	Node outputNode;
	
	public OutputNode(int ID, InputAndWeight[] inputsAndWeights){
		super(ID, inputsAndWeights);
	}
	
	public double calcOutput(InputAndWeight[] inputAndWeights){
		double output = 0.0;
		double net = 0.0;
		
		for(int i = 0; i < inputAndWeights.length; i++){
			net += inputAndWeights[i].getInput() * inputAndWeights[i].getWeight();
		}
		
		output = 1/(1 + Math.pow(Math.E, -net));
		
		return output;
	}
}
