package Classifiers;
import java.util.*;

class DecisionTree{
    private DecisionTreeNode root;
    private Set<Integer> featureIndices;
    
    public DecisionTree(List<String> trainingData, Set<Integer> featureIndices) {
        this.featureIndices = featureIndices;
        this.root = buildTree(trainingData);
    }
    
    private DecisionTreeNode buildTree(List<String> data) { //The splitdata will store all the dataset apart.
        List<List<String>> splitData = data.stream()
                .map(line -> Arrays.asList(line.split(",")))
                .collect(Collectors.toList());
        
        
        
        // Split data
        List<String> leftData = new ArrayList<>();
        List<String> rightData = new ArrayList<>();
        for (String instance : data) {
            if (bestSplit.goesLeft(instance)) {
                leftData.add(instance);
            } else {
                rightData.add(instance);
            }
        }
        
        // Recursively build subtrees
        DecisionTreeNode leftChild = buildTree(leftData);
        DecisionTreeNode rightChild = buildTree(rightData);
        
        return new DecisionTreeNode(bestSplit.toString(), leftChild, rightChild);
    }
    
    public int predict(String[] features) {
        return predict(root, features);
    }
    
    private int predict(DecisionTreeNode node, String[] features) {
        if (node.isLeaf()) {
            return node.getPrediction();
        }
        
        String[] condition = node.getSplitCondition().split(" ");
        int featureIndex = Integer.parseInt(condition[0]);
        double threshold = Double.parseDouble(condition[2]);
        double value = Double.parseDouble(features[featureIndex]);
        
        if (value <= threshold) {
            return predict(node.getLeftChild(), features);
        } else {
            return predict(node.getRightChild(), features);
        }
    }
    
}