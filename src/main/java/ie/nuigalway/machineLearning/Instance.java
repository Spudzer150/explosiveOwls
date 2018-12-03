package ie.nuigalway.machineLearning;

import java.util.Arrays;

/**
 * A data class to store the information for one data point (In this case an owl)
 * 
 * @author Murphy Berry - 15489068
 *
 */
public class Instance {

	double[] data; 
	String classification;

	public Instance(String[] a){
		this.data = new double[a.length-1];
		for( int i=0; i<a.length-1; i++){
			String S = a[i];
			data[i]= Double.parseDouble(S);
		}
		this.classification=a[a.length-1];
	}

	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
	@Override
	public String toString() {
		return "Instance [data= " + Arrays.toString(data) + ", classification= " + classification + "]";
	}

	
}
