import java.util.*;
import java.util.Random;

// 1. DecisionTreeNode.java
class DecisionTreeNode {
    int IndexofFeature; // e.g., 4 for "HbA1c"
    private double splitCondition; // e.g., "HbA1c <= 6.5"
    private DecisionTreeNode leftChild;
    private DecisionTreeNode rightChild;
    private Integer prediction; // Only for leaf nodes
    
    public DecisionTreeNode() { // Default constructor
        this.splitCondition = 0.0;
        this.leftChild = null;
        this.rightChild = null;
        this.prediction = null;
    }

    public DecisionTreeNode(int index, double splitCondition ,DecisionTreeNode leftChild, DecisionTreeNode rightChild) {
        this.IndexofFeature = index;
        this.splitCondition = splitCondition;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.prediction = null;
    }
    
    // Constructor for leaf nodes
    public DecisionTreeNode(Integer prediction) {
        this.IndexofFeature = -1; // No feature index for leaf nodes
        this.prediction = prediction;
        this.splitCondition = null;
        this.leftChild = null;
        this.rightChild = null;
    }
    
    public boolean isLeaf() {
        return prediction != null;
    }
    
    // Getters
    public String getSplitCondition() { return splitCondition; }
    public DecisionTreeNode getLeftChild() { return leftChild; }
    public DecisionTreeNode getRightChild() { return rightChild; }
    public Integer getPrediction() { return prediction; }
}