package ie.nuigalway.machineLearning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Instance> sortingHat(ArrayList<Instance> Data , int attributeIndex){
		final int Index=attributeIndex;
		Data.sort(new Comparator(){
			public int compare(Object o1, Object o2){
				double[] d1 = ((Instance) o1).getData();
				double[] d2 = ((Instance) o2).getData();
						
				Double value1 = d1[Index];
				Double value2 = d2[Index];
				
				return value1.compareTo(value2);
			}
		});
		
			
		
		return Data;
		
	}
}
