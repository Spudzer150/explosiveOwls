package ie.nuigalway.machineLearning;

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
