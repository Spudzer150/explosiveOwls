package ie.nuigalway.machineLearning;

/**
 * A data class to store counts of each classification
 * 
 * @author murphy Berry - 15489068 
 *
 */
public class ClassificationCount {
	
	private String type;
	private int count;
	
	public ClassificationCount(String type){
		this.type=type;
		this.count=0;
	}

	public void incrementCount(){
		count++;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
