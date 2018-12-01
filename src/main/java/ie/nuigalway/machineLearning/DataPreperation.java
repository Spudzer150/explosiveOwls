package ie.nuigalway.machineLearning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author murphy Berry - 15489068 
 *
 */
public class DataPreperation
{
	private ArrayList<Instance> training;
	private ArrayList<Instance> testing;
	
	public DataPreperation(){
	this.training= new ArrayList<Instance>();
	this.testing= new ArrayList<Instance>();
	}
	
	public void DataSplit(ArrayList<Instance> Data ){
		Collections.shuffle(Data);
		
		int twoThirds = 2* (Data.size()/3);
		training = (ArrayList<Instance>) Data.subList(0, twoThirds);
		testing = (ArrayList<Instance>) Data.subList(twoThirds,Data.size());
	
	}
	public ArrayList<ClassificationCount> getClassificationCount(ArrayList<Instance> Data){
		ArrayList<ClassificationCount> count = new ArrayList<ClassificationCount>();
		
		ArrayList<String> uniqueClassification = new ArrayList<String>();
		
		for(Instance inst: Data){
			String classification = inst.getClassification();
			if (!uniqueClassification.contains(classification)){
				uniqueClassification.add(classification);
			}
		}
		
		for(String uniqueClassifier: uniqueClassification){
			ClassificationCount ClassCount = new ClassificationCount(uniqueClassifier);
			for(Instance ins: Data){
				if (ins.getClassification().equals(uniqueClassifier)){
					ClassCount.incrementCount();
				}
			}
			count.add(ClassCount);
		}
		
		return count;
	}

}
