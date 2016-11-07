import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Network {
	
	ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
	ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
	ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
	double networkOutput[];
	double learningRate = 0.5;
//	final double TARGET_RMSE = 0.1;
	final int MAX_EPOCH_NUM = 100000;
	Random rand = new Random();
	File outFile;
	PrintWriter write;
	
	double[][] xorInput = {
			{.1, .1},
			{.1, .9},
			{.9, .1},
			{.9, .9}
	};
	double[] xorOutput = {.1, .9, .9, .1};
	
	double[][] validSymbols = {
			{	
				.1, .1, .9, .1, .1,
				.1, .1, .9, .1, .1,
				.9, .9, .9, .9, .9,		//	+
				.1, .1, .9, .1, .1,
				.1, .1, .9, .1, .1
			},
			{	
				.1, .1, .1, .1, .1,
				.1, .1, .1, .1, .1,
				.9, .9, .9, .9, .9,		//	-
				.1, .1, .1, .1, .1,
				.1, .1, .1, .1, .1
			},
			{
				.9, .1, .1, .1, .1,
				.1, .9, .1, .1, .1,
				.1, .1, .9, .1, .1,		//	\
				.1, .1, .1, .9, .1,
				.1, .1, .1, .1, .9
			},
			{
				.1, .1, .1, .1, .9,
				.1, .1, .1, .9, .1,
				.1, .1, .9, .1, .1,		//	/
				.1, .9, .1, .1, .1,
				.9, .1, .1, .1, .1
			},
			{
				.9, .1, .1, .1, .9,
				.1, .9, .1, .9, .1,
				.1, .1, .9, .1, .1,		//	X
				.1, .9, .1, .9, .1,
				.9, .1, .1, .1, .9
			},
			{
				.1, .1, .9, .1, .1,
				.1, .1, .9, .1, .1,
				.1, .1, .9, .1, .1,		//	|
				.1, .1, .9, .1, .1,
				.1, .1, .9, .1, .1
			}
	};
	
	double[][] expectedOutputs = {
			{.9, .1, .1, .1, .1, .1},	//	+
			{.1, .9, .1, .1, .1, .1},	//	-
			{.1, .1, .9, .1, .1, .1},	//	\
			{.1, .1, .1, .9, .1, .1},	//	/
			{.1, .1, .1, .1, .9, .1},	//	X
			{.1, .1, .1, .1, .1, .9},	//	|
	};
	
	String[] symbolNames = {"plus sign +", "minus sign -", "back slash \\", "forward slash /", "X", "vertical bar |"}; 
	
	public Network(int numInputNodes, int numHiddenLayerNodes, int numOutputNodes) throws FileNotFoundException{
		
		outFile = new File("symbolsOutputFile.txt");
		
		for(int i = 0; i < numInputNodes; i++){
			Neuron inputNeuron = new Neuron(i);
			inputLayer.add(inputNeuron);
		}
		
		for(int i = 0; i < numHiddenLayerNodes; i++){
			Neuron hiddenNeuron = new Neuron(i);
			hiddenNeuron.addIncoming(inputLayer);
			hiddenNeuron.addBiasConnection(new Neuron(25));
			hiddenLayer.add(hiddenNeuron);
		}
		
		for(int i = 0; i < numOutputNodes; i++){
			Neuron outputNeuron = new Neuron(i);
			outputNeuron.addIncoming(hiddenLayer);
			outputNeuron.addBiasConnection(new Neuron(25));
			outputLayer.add(outputNeuron);
		}
		
		for(Neuron n : hiddenLayer){
			for(Connection conn : n.getAllincoming()){
				double weight = (rand.nextDouble()*2.0)-1.0;
				while(weight > 1.000000000000000000000000 || weight < -1.000000000000000000000000){
					weight = (rand.nextDouble()*2.0)-1.0;
				}
				conn.setWeight(weight);
			}
		}
		
		for(Neuron n : outputLayer){
			for(Connection conn : n.getAllincoming()){
				double weight = (rand.nextDouble()*2.0)-1.0;
				while(weight > 1.000000000000000000000000 || weight < -1.000000000000000000000000){
					weight = (rand.nextDouble()*2.0)-1.0;
				}
				conn.setWeight(weight);
			}
		}
		
		networkOutput = new double[outputLayer.size()];
	}
	
	public void setInputs(double[] inputs){
		for(int i = 0; i < inputLayer.size(); i++){
			inputLayer.get(i).setOutput(inputs[i]);
		}
	}
	
	public void feedForward(){
		for(Neuron hiddenNeuron : hiddenLayer){
			hiddenNeuron.calculateOutput();
		};
		for(Neuron outputNeuron : outputLayer){
			outputNeuron.calculateOutput();
		}
	}
	
	public double[] getNetworkOutput(){
		double[] output = new double[outputLayer.size()];
		for(int i = 0; i < outputLayer.size(); i++){
			output[i] = outputLayer.get(i).getOutput();
		}
		return output;
	}
	
	public void backProp(double[] expectedOutput){
		double[] outputLayerErrorSignal = new double[outputLayer.size()];
		double[] hiddenLayerErrorSignal = new double[hiddenLayer.size()];
		for(int i = 0; i < outputLayer.size(); i++){
			double weightAdjustment = 0.0;
			Neuron outputNeuron = outputLayer.get(i);
			for(int j = 0; j < outputNeuron.getAllincoming().size(); j++){
				Connection conn = outputNeuron.getAllincoming().get(j);
				double actualOutput = outputNeuron.getOutput();
				double prevOutput = conn.getFromNeuron().getOutput();
				outputLayerErrorSignal[i] = (expectedOutput[i] - actualOutput) * actualOutput * (1.0 - actualOutput);
				weightAdjustment = learningRate * outputLayerErrorSignal[i] * prevOutput;
				conn.setWeight(conn.getWeight() + weightAdjustment);
			}
			outputNeuron.biasConnection.setWeight(outputNeuron.biasConnection.getWeight() + (learningRate * outputLayerErrorSignal[i] * outputNeuron.getBias()));
		}
		
		for(int k = 0; k < hiddenLayer.size(); k++){
			double weightAdjustment = 0.0;
			Neuron hiddenNeuron = hiddenLayer.get(k);
			for(int l = 0; l < hiddenNeuron.getAllincoming().size(); l++){
				Connection conn = hiddenNeuron.getAllincoming().get(l);
				double actualOutput = hiddenNeuron.getOutput();
				double prevOutput = conn.getFromNeuron().getOutput();
				double outputLayerErrorSum = 0.0;
				for(int m = 0; m < outputLayer.size(); m++){
					Neuron outputNeuron = outputLayer.get(m);
					double connectionWeightToOutput = outputNeuron.getAllincoming().get(hiddenNeuron.NEURON_ID).getWeight();
					outputLayerErrorSum += outputLayerErrorSignal[m] * connectionWeightToOutput;
				}
				hiddenLayerErrorSignal[k] = actualOutput * (1.0 - actualOutput) * outputLayerErrorSum;
				weightAdjustment = hiddenLayerErrorSignal[k] * learningRate * prevOutput;
				conn.setWeight(conn.getWeight() + weightAdjustment);
			}
			hiddenNeuron.biasConnection.setWeight(hiddenNeuron.biasConnection.getWeight() + (learningRate * hiddenLayerErrorSignal[k] * hiddenNeuron.getBias()));
		}
	}
	
	public void train(double targetError){
		double currentError = 1.0;
		int currentEpoch = 0;
		double totalError = 0.0;
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		
		for(int i = 0; i < validSymbols.length; i++){
//		for(int i = 0; i < xorInput.length; i++){
			indexes.add(i);
		}
		
		while(currentError > targetError && currentEpoch < MAX_EPOCH_NUM){
			currentError = 0.0;
			totalError = 0.0;
			
			for(int i = 0; i < 100; i++){
				Collections.shuffle(indexes);
				
				for(int j = 0; j < validSymbols.length; j++){
//				for(int j = 0; j < xorInput.length; j++){
					setInputs(validSymbols[indexes.get(j)]);
//					setInputs(xorInput[indexes.get(j)]);
					feedForward();
					backProp(expectedOutputs[indexes.get(j)]);
//					backProp(xorOutput[indexes.get(j)]);
				}
				currentEpoch ++;
				if(currentEpoch ==1)
					displayNetwork();
			}
			
			for(int a = 0; a < validSymbols.length; a++){
//			for(int a = 0; a < xorInput.length; a++){
				setInputs(validSymbols[indexes.get(a)]);
//				setInputs(xorInput[indexes.get(a)]);
				feedForward();
				networkOutput = getNetworkOutput();
				for(int x = 0; x < networkOutput.length; x++){
					totalError += Math.pow(expectedOutputs[indexes.get(a)][x] - networkOutput[x],2);
//					System.out.println("Expected output for neuron " + indexes.get(a) + " : " + expectedOutputs[indexes.get(a)][x]);
//					System.out.println("Actual output for neuron " + indexes.get(a) + " : " + networkOutput[x]);
//					totalError += Math.pow(xorOutput[indexes.get(a)] - networkOutput[x],2);
				}
			}
			
			currentError = totalError / 2.0;
		}
		
		if(currentError <= targetError){
			System.out.println("TRAINED!!!!! current error = " + currentError + "  current epoch = " + currentEpoch);
		}
		else if(currentEpoch >= MAX_EPOCH_NUM){
			System.out.println("FAILED, target error was " + targetError + " and current error is " + currentError);
		}
		
	}
	
	public void writeOutput(String filename) throws FileNotFoundException{
		File out = new File(filename);
//		PrintWriter current = new PrintWriter(out);
		write = new PrintWriter(out);
//		for(Neuron n : inputLayer){
//			write.print(n.getOutput() + ", ");
//		}
//		write.println();
		double max = 0.0;
		int symbolId = 0;
		for(Neuron o : outputLayer){
			write.print(o.getOutput() + ",");
			if(o.getOutput() > max){
				max = o.getOutput();
				symbolId = o.NEURON_ID;
			}
		}
//		System.out.println(symbolId);
		write.print("\n Symbol recognized as " + symbolNames[symbolId]);
		write.print("\n");
//		current.close();
	}
	
	public void testXorNetwork(Network network){
		for(double i = 0; i <= 1; i += .1){
			for(double j = 0; j <= 1; j += .1){
				double[] inputs = {i,j};
				network.setInputs(inputs);
				feedForward();
//				writeOutput();
			}
		}
	}
	
	public void testSymbolNetwork(Network network, double noiseValue) throws FileNotFoundException{
		double[][] noisyData = new double[6][25];
		for(int i = 0; i < network.validSymbols.length; i++){
			for(int j = 0; j < network.validSymbols[i].length; j++){
				double temp = rand.nextDouble() * noiseValue;
//				System.out.println(temp);
				noisyData[i][j] = validSymbols[i][j] + (temp);
			}
		}
		for(int k = 0; k < noisyData.length; k++){
			network.setInputs(noisyData[k]);
			feedForward();
//			writeOutput("output_noise_level" + noiseValue + ".txt");
		}
	}
	
	public void displayNetwork(){
		for(Neuron n : inputLayer){
			System.out.println("Input Node " + n.NEURON_ID + ":");
			System.out.println("\tInput signal - " + n.getOutput());
		}
		for(Neuron n : hiddenLayer){
//			for(Connection c : n.getAllincoming()){
				System.out.println("Hidden Node "+ n.NEURON_ID + ":");
//				System.out.println("\tConnection "  + c.connectionID + "- weight: " + c.getWeight());
//			}
			System.out.println("\tOutput: " + n.getOutput());
		}
		
		for(Neuron n : outputLayer){
//			for(Connection c : n.getAllincoming()){
				System.out.println("Output Node "+ n.NEURON_ID + ":");
//				System.out.println("\tConnection "  + c.connectionID + "- weight: " + c.getWeight());
//			}
			System.out.println("\tOutput: " + n.getOutput());
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Network network = new Network(25,25,6);
//		Network network = new Network(2,2,1);
		network.train(.001);
//		network.train(Math.pow(network.TARGET_RMSE, 2) * network.xorInput.length / 2.0);
//		network.testNetwork(network);
		int count = 1;
		for(double d = 0.1; d < 1.0; d += .1){
			for(int i = 0; i < 1000; i++){
				network.testSymbolNetwork(network, d);
				network.writeOutput("output_noise_level" + count + ".txt");
			}
			count ++;
		}
		network.write.close();
	}

}
