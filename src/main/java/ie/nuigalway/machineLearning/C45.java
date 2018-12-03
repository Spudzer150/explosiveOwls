package ie.nuigalway.machineLearning;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;

/**
 * C4.5 Implementataion in java
 * @author Niall Greaney
 * @ID 15479942
 */
public class C45 {
	// Class for Preparing the data
	private DataPreperation dataPreper;
	// The root node of the tree
	private Node rootNode;
	// List to hold the predicted Classifications
	private ArrayList<String> predictedClasses;
	// The pruning ratio. Used to determine when there is enough of a class in a node
	// to make the node a leaf
	private final static double RATIO = 0.7;
	// Sets the minimum dataset size for a leaf 
	private final static int MINDATASETSIZE = 30;
	
	/**
	 * Constructor
	 */
	public C45() {
		this.dataPreper = new DataPreperation();
		this.predictedClasses = new ArrayList<String>();
	}
	
	/**
	 * Returns the measure of uncertainty of a random variable
	 * @param classCounts - A list of the the counts of unique classes
	 * @return - the entropy of the random variable
	 */
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
	
	/**
	 * Get the greatest information gain of a attribute specified by the attribute Index.
	 * The array is sorted by that attribute and split for each unique value of that attribute
	 * to find the best gain. 
	 * @param dataset - the dataset to be checked.
	 * @param attributeIndex - the index of the attribute in an Intance data array that we want to calculate the gain for.
	 * @return SplittingInstance - a SplittingInstance object which has the instance the data should be split on and the gain of that split
	 */
	private SplittingInstance getInfoGainForAttribute(ArrayList<Instance> dataset, int attributeIndex) {
		SplittingInstance splittingInstance = new SplittingInstance(0.0);
		
		// Sort Instances by attribute
		dataset = dataPreper.sortingHat(dataset, attributeIndex);
		
		// Get the threshold values
		ArrayList<Instance> thresholds = this.getThresholds(dataset, attributeIndex);
		
		// For all possible splits, calculate the greatest information gain
		for (Instance threshold : thresholds) {
			ArrayList<Instance> datasetLessThanThres = new ArrayList<Instance>(dataset.subList(0, dataset.indexOf(threshold)));
			ArrayList<Instance> datasetGtrThanOrEquThres = new ArrayList<Instance>(dataset.subList(dataset.indexOf(threshold), dataset.size()));
			
			// Calculate the entropy
			double entropyOfClassType = getEntropy(dataPreper.getClassificationCount(dataset));
			double entropyOfClassTypeGivenLessThanThres = getEntropy(dataPreper.getClassificationCount(datasetLessThanThres));
			double entropyOfClassTypeGivenGtrThanOrEquThres = getEntropy(dataPreper.getClassificationCount(datasetGtrThanOrEquThres));
			
			// Calculate the info gain
			double infoGain = entropyOfClassType 
						- ((dataset.size()/datasetLessThanThres.size()) * entropyOfClassTypeGivenLessThanThres) 
						- ((dataset.size()/datasetGtrThanOrEquThres.size()) * entropyOfClassTypeGivenGtrThanOrEquThres);
			
			// If the current info gain is greater than the old gain, take the new gain
			if (infoGain > splittingInstance.getInfoGain()) {
				splittingInstance.setInfoGain(infoGain);
				splittingInstance.setInstance(threshold);
				splittingInstance.setDataIndexOfSplttingValue(attributeIndex);
			}
		}
		
		return splittingInstance;
	}
	
	/**
	 * Get the best information gain for all attributes
	 * @param dataset - the dataset
	 * @return SplittingInstance - a SplittingInstance object which has the greater info gain for all attributes
	 */
	private SplittingInstance getMaxInfoGain(ArrayList<Instance> dataset) {
		SplittingInstance bestSplittingInstance = new SplittingInstance(0.0);
		if (!dataset.isEmpty()) {
			for (int i = 0; i < dataset.get(0).getData().length; i++) {
				SplittingInstance splitter = getInfoGainForAttribute(dataset, i);
				if (splitter.getInfoGain() > bestSplittingInstance.getInfoGain()) {
					bestSplittingInstance = splitter;
				}
			}
		}
		return bestSplittingInstance;
	}
	
	/**
	 * Gets the List of instances which splits the dataset for every unique attribute value
	 * @param dataset - the dataset
	 * @param attributeIndex - the index of the attribute in an Intance data array that we want to calculate the gain for.
	 * @return ArrayList<Instance> - the list of threshold instances
	 */
	private ArrayList<Instance> getThresholds(ArrayList<Instance> dataset, int attributeIndex) {
		ArrayList<Instance> thresholds = new ArrayList<Instance>();
		for(Instance ins : dataset) {
			double attributeValue = ins.getData()[attributeIndex];
			boolean addToThresholds = true;
			/*
			 * If the instance has an attribute value which is already in the list,
			 * don't add it. Otherwise do. This gives us the first attribute with that 
			 * value. We can then split for less than this value and greater than or equal to that value
			 */
			for (Instance threshold : thresholds) {
				if (threshold.getData()[attributeIndex] == attributeValue) {
					addToThresholds = false;
				} 
			}
			if (addToThresholds) {
				thresholds.add(ins);
			}
		}
		
		/*
		 * We have to remove the first threshold as we are splitting by >=
		 * If we split by last threshold we would have a list with all elements 
		 * and a list with no elements
		 */
		thresholds.remove(0);
		
		return thresholds;
	}
	
	/**
	 * Recursive method for build the decision tree
	 * @param dataset - the dataset
	 * @return Node - a node in the tree
	 */
	private Node buildTree(ArrayList<Instance> dataset) {
		Node node = new Node();
		
		// If the data set is empty then there is no node to add
		if (dataset.isEmpty() || dataset == null) {
			return null;
		}
		
		// If all Instances are the same class, create a leaf node
		if(allInstancesAreSameClass(dataset)) {
			node.setClassification(dataset.get(0).getClassification());
			return node;
		}
		
		// Get the instance with the greatest info gain
		SplittingInstance splittingInstance = getMaxInfoGain(dataset);
		
		// Add the spltting Instance to the node
		node.setSplittingInstance(splittingInstance);
		
		// Split the dataset into two partitions base in the splitting instance
		ArrayList<Instance> datasetLessSplittingIns = new ArrayList<Instance>(dataset.subList(0, dataset.indexOf(splittingInstance.getInstance())));
		ArrayList<Instance> datasetGtrOrEquSplittingIns = new ArrayList<Instance>(dataset.subList(dataset.indexOf(splittingInstance.getInstance()), dataset.size()));
		
		// Recursively create the tree
		node.setLeftBranch(buildTree(datasetLessSplittingIns));
		node.setRightBranch(buildTree(datasetGtrOrEquSplittingIns));

		return node;
	}
	
	/**
	 * Determines the Proportion of a class is enough to create a leaf node by the RATIO constant
	 * @param dataset - the dataset
	 * @return boolean - true if the proportion of one class is 
	 */
	private boolean allInstancesAreSameClass(ArrayList<Instance> dataset) {
		if (!dataset.isEmpty()) {
			ArrayList<Instance> instances = new ArrayList<Instance>();
			ArrayList<String> instanceTypes = new ArrayList<String>();
			ArrayList<Integer> instancesCounts = new ArrayList<Integer>();
			
			instances.add(dataset.get(0));
			instanceTypes.add(dataset.get(0).getClassification());
			instancesCounts.add(1);
			for (Instance ins : dataset) {
				String currentInsType = ins.getClassification();
				if (instanceTypes.contains(currentInsType)) {
					int indexOfClass = instanceTypes.indexOf(currentInsType);
					int currentCount = instancesCounts.get(indexOfClass);
					instancesCounts.set(indexOfClass, ++currentCount);
				} else {
					instances.add(ins);
					instanceTypes.add(ins.getClassification());
					instancesCounts.add(1);
				}
				
			}
			
			int indexOfGreatestCount = 0;
			int maxCount = 0;
			for (int i=0; i<instancesCounts.size(); i++) {
				if (instancesCounts.get(i) > maxCount) {
					maxCount = instancesCounts.get(i);
					indexOfGreatestCount = i;
				}
			}
			
			double instanceProportion = (double) instancesCounts.get(indexOfGreatestCount)/dataset.size();
			if (dataset.size() < MINDATASETSIZE || instanceProportion >= RATIO) {
				dataset.set(0, instances.get(indexOfGreatestCount));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Constructs the Tree for a give dataset
	 * @param dataset - the dataset to train the tree
	 */
	public void trainTree(ArrayList<Instance> dataset) {
		this.rootNode = buildTree(dataset);
	}
	
	/**
	 * Classifies a testing dataset
	 * @param dataset - the dataset to classify
	 * @return ArrayList<String> - a List of predicted class strings 
	 */
	public ArrayList<String> classify(ArrayList<Instance> dataset) {
		predictedClasses = new ArrayList<String>();
		for(Instance instance : dataset) {
			predictedClasses.add(classifyInstance(instance, rootNode));
		}
		return predictedClasses;
	}
	
	/**
	 * Classifies a single instance of the dataset.
	 * @param instance - the instance to classify
	 * @param node - the root node of the tree
	 * @return String - the predicted class type
	 */
	private String classifyInstance(Instance instance, Node node) {
		if (StringUtils.isNotBlank(node.getClassification())) {
			return node.getClassification();
		}
		
		int dataIndex = node.getSplittingInstance().getDataIndexOfSplttingValue();
		double splittingAttribute = node.getSplittingInstance().getInstance().getData()[dataIndex];
		double instanceAttribute = instance.getData()[dataIndex];
		if (instanceAttribute < splittingAttribute) {
			return classifyInstance(instance, node.getLeftBranch());
		} else {
			return classifyInstance(instance, node.getRightBranch());
		}
	}
	
	/**
	 * Calculates the classification accuracy by comparing a list of predicted classes to the list of actual classes
	 * @param predicted - the list of predicted classes
	 * @param actual - the list of actual classes
	 * @return double - the classification accuracy between 0 and 1
	 */
	public double getClassificationAccuracy(ArrayList<String> predicted, ArrayList<String> actual) {
		int totalNumOfInstances = predicted.size();
		int numCorrectClassifications = 0;
		for (int i=0; i<totalNumOfInstances; i++) {
			if (predicted.get(i).equals(actual.get(i))) {
				numCorrectClassifications++;
			}
		}
		double toReturn = (double) numCorrectClassifications / totalNumOfInstances;
		return toReturn;
	}
	
	/**
	 * Preforms k-fold cross validation using the c4.5 classifier and prints 
	 * each folds accuracy as well as an overall accuracy
	 * @param numFolds - the number of folds
	 * @param dataset - the dataset
	 */
	public void crossValidation(int numFolds, ArrayList<Instance> dataset) {
		int[] splittingIndexes = dataPreper.getDataSplitIndexes(numFolds, dataset.size());
		
		Collections.shuffle(dataset);
		
		double averageAccuracy = 0.0;
		for(int i = 0; i<splittingIndexes.length-1; i++) {
			ArrayList<Instance> testingSet = new ArrayList<Instance>(dataset.subList(splittingIndexes[i], splittingIndexes[i+1]));
			ArrayList<Instance> trainingSet = new ArrayList<Instance>();
			if (i == 0) {
				trainingSet = new ArrayList<Instance>(dataset.subList(splittingIndexes[i+1], splittingIndexes[splittingIndexes.length-1]));
			} else if (i == splittingIndexes.length-2) {
				trainingSet = new ArrayList<Instance>(dataset.subList(splittingIndexes[0], splittingIndexes[i]));
			} else {
				trainingSet = new ArrayList<Instance>(dataset.subList(splittingIndexes[0], splittingIndexes[i]));
				trainingSet.addAll(dataset.subList(splittingIndexes[i+1], splittingIndexes[splittingIndexes.length-1]));
			}
			
			System.out.println("Testing Set size: " + testingSet.size());
			System.out.println("Training Set size: " + trainingSet.size());
			this.trainTree(trainingSet);
			
			ArrayList<String> predictedClasses = this.classify(testingSet);
			ArrayList<String> actualClasses = new ArrayList<String>();
			
			for (Instance ins : testingSet) {
				actualClasses.add(ins.getClassification());
			}
			double accuracy = this.getClassificationAccuracy(predictedClasses, actualClasses) * 100;
			averageAccuracy += accuracy;
			System.out.printf("Accuracy: %.2f%% \n\n", accuracy);
			
		}
		
		averageAccuracy = (double) averageAccuracy/numFolds;
		System.out.printf("Average Accuracy: %.2f%% \n", averageAccuracy);
		
	}
}
