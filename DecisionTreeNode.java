import java.util.Map;

public class DecisionTreeNode {
    // For splitting node:
    private Integer featureIndex; // Feature index to split on (null for leaf nodes)
    private Map<String, DecisionTreeNode> children; // Children for each possible value of the feature
    
    // For leaf node:
    private String label; // The label for classification (only for leaf nodes)

    // Constructor for a leaf node
    public DecisionTreeNode(String label) {
        this.label = label;
        this.featureIndex = null; // Leaf node has no feature to split
        this.children = null;
    }

    // Constructor for a splitting node
    public DecisionTreeNode(Integer featureIndex, Map<String, DecisionTreeNode> children) {
        this.featureIndex = featureIndex;
        this.children = children;
        this.label = null; // Splitting node does not have a label
    }

    // Check if the node is a leaf node
    public boolean isLeaf() {
        return label != null;
    }

    // Getters and Setters
    public Integer getFeatureIndex() {
        return featureIndex;
    }

    public void setFeatureIndex(Integer featureIndex) {
        this.featureIndex = featureIndex;
    }

    public Map<String, DecisionTreeNode> getChildren() {
        return children;
    }

    public void setChildren(Map<String, DecisionTreeNode> children) {
        this.children = children;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
