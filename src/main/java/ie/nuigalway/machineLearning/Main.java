package ie.nuigalway.machineLearning;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		String path = "./resources/owls.csv";
		
		ArrayList<Instance> owls = DataReader.readData(path);
		System.out.println(owls.get(45).getClassification());
	}

}
