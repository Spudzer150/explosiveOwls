package ie.nuigalway.machineLearning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author murphy Berry - 15489068 
 *
 */
public class DataPreperation
{
	private ArrayList<Instance> training;
	private ArrayList<Instance> testing;
	
	public DataPreperation() {
		this.training = new ArrayList<Instance>();
		this.testing = new ArrayList<Instance>();
	}
	
	public void DataSplit(ArrayList<Instance> Data) {
		Collections.shuffle(Data);
		
		int twoThirds = 2 * (Data.size()/3);
		training = new ArrayList<Instance>(Data.subList(0, twoThirds));
		testing = new ArrayList<Instance>(Data.subList(twoThirds, Data.size()));
	}
	
	/**
	 * @author Niall Greaney 15479942
	 * 
	 * Get the indexs for splitting the dataset for k-fold cross validation
	 * @param numFolds
	 * @param datasetSize
	 * @return int[] - an array of splitting indexes
	 */
	public int[] getDataSplitIndexes(int numFolds, int datasetSize) {
		
		int[] splittingIndexes = new int[numFolds+1];
		
		// First index is 0 
		splittingIndexes[0] = 0;
		
		/*
		 * Split the array into numFolds sections.
		 * E.g. for an array of 10 elements we would split at 2, 5 and 7
		 * I.e. [0, 1] [2, 3, 4] [5, 6] [7, 8, 9]
		 */
		for (int i=1; i<numFolds; i++) {
			splittingIndexes[i] = (int) ((i) * (float) datasetSize/numFolds); 
		}
		
		// Last index is the last equal to the size of the array as
		// the toIndex in List.split() is exclusive
		splittingIndexes[numFolds] = datasetSize;
		
		return splittingIndexes;
	}
	
	public ArrayList<ClassificationCount> getClassificationCount(ArrayList<Instance> Data) {
		ArrayList<ClassificationCount> count = new ArrayList<ClassificationCount>();
		
		ArrayList<String> uniqueClassification = new ArrayList<String>();
		
		for(Instance inst: Data){
			String classification = inst.getClassification();
			if (!uniqueClassification.contains(classification)){
				uniqueClassification.add(classification);
			}
		}
		
		for(String uniqueClassifier: uniqueClassification) {
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
	public ArrayList<Instance> sortingHat(ArrayList<Instance> Data , int attributeIndex) {
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

	/**
	 * @return the training
	 */
	public ArrayList<Instance> getTraining() {
		return training;
	}

	/**
	 * @return the testing
	 */
	public ArrayList<Instance> getTesting() {
		return testing;
	}
	
}
