import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class KMeans {
	
	int K;
	Pair[] centroids;
	Pair[] oldCentroids;
	ArrayList<Pair> dataPoints = new ArrayList<Pair>();
	ArrayList<Pair>[] centroidPoints;
	ArrayList<Pair>[] oldCentroidPoints;
	Random rand = new Random();
	
	public KMeans(int K, String inputFileName) throws FileNotFoundException{
		this.K = K;
		File input = new File(inputFileName);
		Scanner scan = new Scanner(input);
		centroids = new Pair[K];
		oldCentroids = new Pair[K];
		centroidPoints = new ArrayList[K];
		oldCentroidPoints = new ArrayList[K];
		
		for(int i = 0; i < K; i++){
			centroids[i] = new Pair(rand.nextDouble(), rand.nextDouble());
			centroidPoints[i] = new ArrayList<Pair>();
			oldCentroidPoints[i] = new ArrayList<Pair>();
		}
		
		while(scan.hasNext()){
			String[] nums = scan.nextLine().split("\t");
			Pair point = new Pair(Double.parseDouble(nums[0]), Double.parseDouble(nums[1]));
			dataPoints.add(point);
		}
		
		do{
			oldCentroidPoints = centroidPoints;
			for(int i = 0; i < centroidPoints.length; i++){
				centroidPoints[i].clear();
			}
			assignCentroids();
			relocateCentroids();
			displayClusters();
		} while(!compare());
	}
	
	
	public void assignCentroids(){
		
		double minDistance;
		int centroidID;
		Pair point;
		for(int i = 0; i < dataPoints.size(); i++){
			point = dataPoints.get(i);
			centroidID = -1;
			minDistance = 1000000;
			for(int j = 0; j < K; j++){
				Double d = Math.sqrt((Math.pow(point.getX() - centroids[j].getX(), 2) + 
							(Math.pow(point.getY() - centroids[j].getY(), 2))));
//				System.out.println(d);
//				System.out.println(minDistance);
				if(d < minDistance){
					minDistance = d;
					centroidID = j;
				}
//				System.out.println(centroidID);
			}
//			System.out.println(centroidID);
			centroidPoints[centroidID].add(point);
		}
//		oldCentroidPoints = centroidPoints;
	}
	
	public void relocateCentroids(){
		double x_sum;
		double y_sum;
		
		oldCentroids = centroids;
		
		for(int i = 0; i < K; i++){
			x_sum = 0;
			y_sum = 0;
			for(int j = 0; j < centroidPoints[i].size(); j++){
				x_sum += centroidPoints[i].get(j).getX();
				y_sum += centroidPoints[i].get(j).getY();
			}
			
			Pair newCentroid = new Pair(x_sum/centroidPoints[i].size(), y_sum/centroidPoints[i].size());
//			System.out.println(newCentroid.getX() + ", " + newCentroid.getY());
			centroids[i] = newCentroid;
		}
	}
	
	public boolean compare(){
		
		boolean isSame = false;
		int matchCount = 0;
		
		for(int i = 0; i < K; i++){
			if((centroids[i].getX() == oldCentroids[i].getX()) && (centroids[i].getY() == oldCentroids[i].getY())){
				matchCount++;
			}
//			int tempSize = 0;
//			if(centroidPoints[i].size() == oldCentroidPoints[i].size()){
//				for(int j = 0; j < centroidPoints[i].size(); j++){
//					if(centroidPoints[i].get(j).equals(oldCentroidPoints[i].get(j))){
//						tempSize ++;
//					}
//				}
//				if(tempSize == centroidPoints[i].size()){
//					matchCount ++;
//				}
//				else{
//					break;
//				}
//			}
		}
		
		if(matchCount == K)
			isSame = true;
		
		return isSame;
	}
	
	public void displayClusters(){
		for(Pair p : centroids){
			System.out.println("Cluster: (" + p.getX() + ", " + p.getY() + ")");
		}
		for(int i = 0; i < K; i ++){
			for(Pair p : centroidPoints[i]){
				System.out.println("Cluster " + i + ": ");
				System.out.println("\t" + "(" + p.getX() + ", " + p.getY() + ")");
			}
			for(Pair p : oldCentroidPoints[i]){
				System.out.println("Cluster " + i + ": ");
				System.out.println("\t" + "(" + p.getX() + ", " + p.getY() + ")");
			}
		}
	}
	
	public static void main(String args[]) throws FileNotFoundException{
		KMeans k = new KMeans(4,"xyz.txt");
		int count = 0;
//		do{
////			k.oldCentroidPoints = k.centroidPoints;
//			k.assignCentroids();
//			k.relocateCentroids();
//			k.displayClusters();
////			for(int i = 0; i < k.K; i++){
////				System.out.println(k.oldCentroidPoints[i]
////			}
//			
//			if(k.oldCentroidPoints.equals(k.centroidPoints)){
//				System.out.println("Clusters stabilized in " + count + " iterations.");
//			}
//			count ++; 
//			k.oldCentroidPoints = k.centroidPoints;
//			for(ArrayList<Pair> a : k.centroidPoints){
//				a.clear();
//			}
//		}
//		while(!k.compare());
//		if(k.oldCentroidPoints.equals(k.centroidPoints)){
			System.out.println("Clusters stabilized in " + count + " iterations.");
//		}
//		k.displayClusters();
	}
}
