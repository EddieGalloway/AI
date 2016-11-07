
public class InputAndWeight {

	double[] pair = new double[2];
	
	public InputAndWeight(){
		pair[0] = 0.0;
		pair[1] = 0.0;
	}
	
	public InputAndWeight(double input, double weight){
		pair[0] = input;
		pair[1] = weight;
	}
	
	public double getInput(){
		return pair[0];
	}
	public void setInput(double input){
		pair[0] = input;
	}
	public double getWeight(){
		return pair[1];
	}
	public void setWeight(double weight){
		pair[1] = weight;
	}
}
