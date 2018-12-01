package ie.nuigalway.machineLearning;

import java.util.ArrayList;

public class C45 {
	private DataPreperation dataPreper;
	public C45() {
		this.dataPreper = new DataPreperation();
	}
	
	public double getEntropy(ArrayList<ClassificationCount> classCounts) {
		double entropy = 0.0;
		int numClasses = classCounts.size();
		
		for (int i = 0; i < classCounts.size(); i++) {
			double count = classCounts.get(i).getCount();
			
            if (classCounts.get(i).getCount() > 0) {
                double p = (double) count / numClasses;
                entropy -= p * Math.log(p)/Math.log(2);
            }
		}
		return entropy;
	}
	
	public double getInfoGain(ArrayList<Instance> dataset, int attributeIndex) {
		double infoGain = 0.0;
		
		dataset = dataPreper.sortingHat(dataset, attributeIndex);
		ArrayList<Double> thresholds = this.getThresholds(dataset, attributeIndex);
		
		
		
		return infoGain;
	}
	
	public ArrayList<Double> getThresholds(ArrayList<Instance> dataset, int attributeIndex) {
		ArrayList<Double> thresholds = new ArrayList<Double>();
		for(Instance ins : dataset) {
			double attributeValue = ins.getData()[attributeIndex];
			if (!thresholds.contains(attributeValue)){
				thresholds.add(attributeValue);
			}
		}
		
		// We have to remove the last threshold as we are splitting by <=
		// If we split by last threshold we would have a list with all elements 
		// and a list with no elements
		thresholds.remove(thresholds.size()-1);
		
		return thresholds;
	}
	
}
