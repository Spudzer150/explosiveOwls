package ie.nuigalway.machineLearning;

/**
 * Data Class for Holding the Instance and the Information Gain
 * @author Niall Greaney
 * @ID 15479942
 */
public class SplittingInstance {
	private Instance instance;
	private double infoGain;
	private int dataIndexOfSplttingValue;
	
	/**
	 * Constructor
	 * @param instance - the Instance
	 * @param infoGain - the Information Gain
	 */
	public SplittingInstance(Instance instance, double infoGain) {
		this.instance = instance;
		this.infoGain = infoGain;
	}
	
	/**
	 * Constructor
	 * @param infoGain - the Information Gain
	 */
	public SplittingInstance(double infoGain) {
		this.infoGain = infoGain;
	}

	/**
	 * @return the instance
	 */
	public Instance getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	/**
	 * @return the infoGain
	 */
	public double getInfoGain() {
		return infoGain;
	}

	/**
	 * @param infoGain the infoGain to set
	 */
	public void setInfoGain(double infoGain) {
		this.infoGain = infoGain;
	}

	/**
	 * @return the dataIndexOfSplttingValue
	 */
	public int getDataIndexOfSplttingValue() {
		return dataIndexOfSplttingValue;
	}

	/**
	 * @param dataIndexOfSplttingValue the dataIndexOfSplttingValue to set
	 */
	public void setDataIndexOfSplttingValue(int dataIndexOfSplttingValue) {
		this.dataIndexOfSplttingValue = dataIndexOfSplttingValue;
	}

	
}
