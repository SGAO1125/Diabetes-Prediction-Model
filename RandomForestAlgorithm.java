import java.util.*;

public class RandomForestAlgorithm {
    private final DecisionTree[] forest;
    private final Random random;
    private final int numTrees;

    public RandomForestAlgorithm(int numTrees) {
        this.numTrees = numTrees;
        this.forest = new DecisionTree[numTrees];
        this.random = new Random();
    }

    public void buildRandomForest(List<String> dataset) {
        int numRows = dataset.size();
        int numFeatures = dataset.get(0).split(",").length - 1; // target is at index 0

        for (int i = 0; i < numTrees; i++) {
            // Create bootstrap sample
            List<String> bootstrapSample = new ArrayList<>();
            for (int j = 0; j < numRows; j++) {
                int idx = random.nextInt(numRows);
                bootstrapSample.add(dataset.get(idx));
            }

            // Select random feature subset (excluding target)
            int m = (int) Math.sqrt(numFeatures);
            Set<Integer> featureSubset = new HashSet<>();
            while (featureSubset.size() < m) {
                int feature = random.nextInt(numFeatures) + 1; // +1 to skip target
                featureSubset.add(feature);
            }

            // Build tree using bootstrap sample and feature subset
            forest[i] = new DecisionTree(bootstrapSample, featureSubset);
        }
    }

    public String predict(String dataLine) {
        String[] sample = dataLine.split(",");
        Map<String, Integer> votes = new HashMap<>();

        for (DecisionTree tree : forest) {
            String prediction = tree.predict(sample);
            votes.put(prediction, votes.getOrDefault(prediction, 0) + 1);
        }

        return votes.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);
    }

    public int getNumTrees() {
        return numTrees;
    }
}
