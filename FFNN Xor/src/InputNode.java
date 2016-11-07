/**
 * Input node simply passes a signal into the network.  
 * The weights for the inputs will always be 1.
 * 
 * @author chase
 *
 */
public class InputNode extends Node{
	
	Node inputNode;
	
	public InputNode(int ID, InputAndWeight[] incomingData){
		super(ID, incomingData);
	}
	
	//The output of an input node will always be the same as the input signal
	public double calcOutput(){
		return this.getNodeInput()[0];
	}
}
