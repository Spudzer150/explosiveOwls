package ie.nuigalway.machineLearning;

import java.util.ArrayList;
import java.util.Collections;

public class DataPreperation
{
	private ArrayList<Instance> training;
	private ArrayList<Instance> testing;
	
	public void DataSplit(ArrayList<Instance> Data ){
		Collections.shuffle(Data);
		
		int twoThirds = 2* (Data.size()/3);
		training = (ArrayList<Instance>) Data.subList(0, twoThirds);
		testing = (ArrayList<Instance>) Data.subList(twoThirds,Data.size());
	}

}
