import java.util.*;

public class DecisionTree {
    private static final int HBA1C_index = 4; // Fixed root feature index
    private int targetIndex;
    private DecisionTreeNode root;

    public DecisionTree(List<List<Float>> data, Set<Integer> featureIndices) {
        this.targetIndex = data.get(0).size() - 1;
        Set<Integer> remainingFeatures = new HashSet<>(featureIndices);
        remainingFeatures.remove(HBA1C_index); // Makes sure that hBA1C is not reused later when building tree
        this.root = buildTreeWithHbA1cRoot(data, remainingFeatures); //Builds Tree using the target attribute HbA1c
    }

    private DecisionTreeNode buildTreeWithHbA1cRoot(List<List<Float>> data, Set<Integer> features) {
        SplitResult rootSplit = findBestSplitOnFeature(data, HBA1C_index);
        if (rootSplit == null) {
            return new DecisionTreeNode(getMajorityLabel(data));
        }

        DecisionTreeNode left = buildTree(rootSplit.leftData, features);
        DecisionTreeNode right = buildTree(rootSplit.rightData, features);

        return new DecisionTreeNode(HBA1C_index + " <= " + rootSplit.threshold, left, right);
    }

    private DecisionTreeNode buildTree(List<List<Float>> data, Set<Integer> features) {
        if (isPure(data)) {
            return new DecisionTreeNode(Math.round(data.get(0).get(targetIndex)));
        }

        if (features.isEmpty()) {
            return new DecisionTreeNode(getMajorityLabel(data));
        }

        SplitResult bestSplit = findBestSplit(data, features);
        if (bestSplit == null) {
            return new DecisionTreeNode(getMajorityLabel(data));
        }

        DecisionTreeNode left = buildTree(bestSplit.leftData, features);
        DecisionTreeNode right = buildTree(bestSplit.rightData, features);

        return new DecisionTreeNode(bestSplit.featureIndex + " <= " + bestSplit.threshold, left, right);
    }

    public int predict(List<Float> features) {
        return predict(root, features);
    }

    private int predict(DecisionTreeNode node, List<Float> features) {
        if (node.isLeaf()) {
            return node.getPrediction();
        }

        String[] condition = node.getSplitCondition().split(" ");
        int featureIndex = Integer.parseInt(condition[0]);
        double threshold = Double.parseDouble(condition[2]);

        return (features.get(featureIndex) <= threshold)
            ? predict(node.getLeftChild(), features)
            : predict(node.getRightChild(), features);
    }

    // === Helpers ===

    private boolean isPure(List<List<Float>> data) {
        int label = Math.round(data.get(0).get(targetIndex));
        for (List<Float> row : data) {
            if (Math.round(row.get(targetIndex)) != label) return false;
        }
        return true;
    }

    private int getMajorityLabel(List<List<Float>> data) {
        int[] counts = new int[2];
        for (List<Float> row : data) {
            counts[Math.round(row.get(targetIndex))]++;
        }
        return counts[0] == counts[1] ? new Random().nextInt(2) : (counts[0] > counts[1] ? 0 : 1);
    }

    private SplitResult findBestSplit(List<List<Float>> data, Set<Integer> features) {
        double bestGini = Double.MAX_VALUE;
        int bestFeature = -1;
        double bestThreshold = 0;
        List<List<Float>> bestLeft = null, bestRight = null;

        for (int feature : features) {
            SplitResult result = findBestSplitOnFeature(data, feature);
            if (result != null && result.gini < bestGini) {
                bestGini = result.gini;
                bestFeature = feature;
                bestThreshold = result.threshold;
                bestLeft = result.leftData;
                bestRight = result.rightData;
            }
        }

        if (bestFeature == -1 || bestLeft.isEmpty() || bestRight.isEmpty()) return null;

        return new SplitResult(bestFeature, bestThreshold, bestLeft, bestRight, bestGini);
    }

    // The lower the gini value is the better the feature is for splitting
    private SplitResult findBestSplitOnFeature(List<List<Float>> data, int feature) {
        data.sort(Comparator.comparingDouble(row -> row.get(feature)));
        // Goal here is to find the best split using the Gini value
        double bestGini = Double.MAX_VALUE;
        double bestThreshold = 0;
        List<List<Float>> bestLeft = null, bestRight = null;

        for (int i = 1; i < data.size(); i++) {
            double prev = data.get(i - 1).get(feature);
            double curr = data.get(i).get(feature);
            // Calculates threshold 
            if (prev != curr) {
                double threshold = (prev + curr) / 2.0; // Take average of both curr and previous feature
                List<List<Float>> left = new ArrayList<>();
                List<List<Float>> right = new ArrayList<>();

                // Store values that are less than the threshold in the left arraylist and others in the right arraylist
                for (List<Float> row : data) {
                    if (row.get(feature) <= threshold) left.add(row);
                    else right.add(row);
                }

                // Calculating highest gini and storing it in maximum
                double gini = giniImpurity(left, right);
                if (gini < bestGini) {
                    bestGini = gini;
                    bestThreshold = threshold;
                    bestLeft = left;
                    bestRight = right;
                }
            }
        }

        if (bestLeft == null || bestRight == null || bestLeft.isEmpty() || bestRight.isEmpty()) return null;

        return new SplitResult(feature, bestThreshold, bestLeft, bestRight, bestGini);
    }
    
    // Calculates gini and stores it in Gini
    private double giniImpurity(List<List<Float>> left, List<List<Float>> right) {
        int total = left.size() + right.size();
        return (left.size() / (double) total) * gini(left) + (right.size() / (double) total) * gini(right);
    }

    //Gini formula
    private double gini(List<List<Float>> data) {
        int[] counts = new int[2];
        for (List<Float> row : data) {
            counts[Math.round(row.get(targetIndex))]++;
        }
        double p0 = counts[0] / (double) data.size();
        double p1 = counts[1] / (double) data.size();
        return 1 - (p0 * p0 + p1 * p1);
    }
    
    private static class SplitResult {
        int featureIndex;
        double threshold;
        List<List<Float>> leftData;
        List<List<Float>> rightData;
        double gini;

        public SplitResult(int featureIndex, double threshold, List<List<Float>> leftData, List<List<Float>> rightData, double gini) {
            this.featureIndex = featureIndex;
            this.threshold = threshold;
            this.leftData = leftData;
            this.rightData = rightData;
            this.gini = gini;
        }
    }
}
