package ie.nuigalway.machineLearning;

import java.util.ArrayList;

public class CrossValidationMain {

	public static void main(String[] args) {
		// Path to owls files
		String path = "./resources/owls.csv";
		
		// Read in the data
		ArrayList<Instance> owls = DataAccess.readData(path);
		
		// Construct classifier
		C45 classifier1 = new C45();
		
		// Run 10-fold Cross Validation
		classifier1.crossValidation(10, owls);
	}

}
