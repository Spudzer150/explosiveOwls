package ie.nuigalway.machineLearning;

import java.util.ArrayList;

/**
 * Main Method
 * @author Niall Greaney
 * @ID 15479942
 */
public class Main {

	public static void main(String[] args) {
		String path = "./resources/owls.csv";
		
		ArrayList<Instance> owls = DataAccess.readData(path);
		
		DataPreperation dataPreper = new DataPreperation();
		dataPreper.DataSplit(owls);
		
		ArrayList<Instance> trainingDataset = dataPreper.getTraining();
		
		ArrayList<Instance> testingDataset = dataPreper.getTesting();
		
		C45 classifier = new C45();
		
		classifier.trainTree(trainingDataset);
		
		ArrayList<String> predictedClasses = classifier.classify(testingDataset);
		ArrayList<String> actualClasses = new ArrayList<String>();
		
		for (Instance ins : testingDataset) {
			actualClasses.add(ins.getClassification());
		}
		
		System.out.println("Predicted Classifications: ");
		for (String classType : predictedClasses) {
			System.out.print(classType + "; ");
		}
		System.out.println();
		
		System.out.println("Actual Classifications: ");
		for (String classType : actualClasses) {
			System.out.print(classType + "; ");
		}
		System.out.println();
		
		System.out.println("Accuracy: " + classifier.getClassificationAccuracy(predictedClasses, actualClasses) * 100 + "%");
		
		DataAccess.writeData(predictedClasses, actualClasses,classifier.getClassificationAccuracy(predictedClasses, actualClasses));
	}

}
