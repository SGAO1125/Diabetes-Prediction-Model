package Classifiers;
public class DecisionTreeNode {
    private String splitCondition;         // E.g., "4 <= 6.5"
    private DecisionTreeNode left;
    private DecisionTreeNode right;
    private Integer prediction;            // null if not a leaf

    // Constructor for internal nodes
    public DecisionTreeNode(String splitCondition, DecisionTreeNode left, DecisionTreeNode right) {
        this.splitCondition = splitCondition;
        this.left = left;
        this.right = right;
        this.prediction = null;
    }

    // Constructor for leaf nodes
    public DecisionTreeNode(int prediction) {
        this.prediction = prediction;
        this.splitCondition = null;
        this.left = null;
        this.right = null;
    }

    public boolean isLeaf() {
        return prediction != null;
    }

    public int getPrediction() {
        return prediction;
    }

    public String getSplitCondition() {
        return splitCondition;
    }

    public DecisionTreeNode getLeftChild() {
        return left;
    }

    public DecisionTreeNode getRightChild() {
        return right;
    }
}