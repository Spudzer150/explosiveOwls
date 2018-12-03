package ie.nuigalway.machineLearning;

/**
 * A class to represent nodes of the decision tree
 * @author Niall Greaney
 * @ID 15479942
 */
public class Node {
	private Node[] branches;
	private SplittingInstance splittingInstance;
	private String classification;
	
	/**
	 * Constructor
	 */
	public Node() {
		this(null);
	}
	
	/**
	 * Constructor
	 * @param splittingInstance - the instance the node splits on
	 */
	public Node(SplittingInstance splittingInstance) {
		this.splittingInstance = splittingInstance;
		this.branches = new Node[2];
		this.classification = "";
	}
	
	/**
	 * Set the left branch's node
	 * @param leftBranch - the left node
	 */
	public void setLeftBranch(Node leftBranch) {
		this.branches[0] = leftBranch;
	}
	
	/**
	 * Set the right branch's node
	 * @param rightBranch - the right node
	 */
	public void setRightBranch(Node rightBranch) {
		this.branches[1] = rightBranch;
	}
	
	/**
	 * Gets the right node
	 * @return Node - the right node
	 */
	public Node getLeftBranch() {
		return this.branches[0];
	}
	
	/**
	 * Gets the left node
	 * @return Node - the left node
	 */
	public Node getRightBranch() {
		return this.branches[1];
	}
	/**
	 * @return the classification
	 */
	public String getClassification() {
		return classification;
	}
	/**
	 * @param classification the classification to set
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}
	/**
	 * @return the splittingInstance
	 */
	public SplittingInstance getSplittingInstance() {
		return splittingInstance;
	}
	/**
	 * @param splittingInstance the splittingInstance to set
	 */
	public void setSplittingInstance(SplittingInstance splittingInstance) {
		this.splittingInstance = splittingInstance;
	}
	
	
	
}
