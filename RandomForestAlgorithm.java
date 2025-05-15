import java.util.*;

public class RandomForestAlgorithm{
    private DecisionTree[] trees;
    private int numTrees;
    private Random random;  
    
    // Feature index number in the string array dataset
    private static final int SEX = 0; //0 or 1
    private static final int AGE = 1; //0 to 99
    private static final int BMI = 2; // 0 to 50 float
    private static final int GLUCOSE = 3; // 0 to 300 int
    private static final int HBA1C = 4; // 3.5 to 9 float
    private static final int HEART_DISEASE = 5; // 0 or 1
    private static final int HYPERTENSION = 6; // 0 or 1
    private static final int DIABETES = 7; // Target variable, 0 or 1
    
    public RandomForestAlgorithm(int numTrees) {
        this.numTrees = numTrees;
        this.trees = new DecisionTree[numTrees];
        this.random = new Random();
    }
    
    public void train(List<List<Float>> dataset) {
        int numInstances = dataset.size();
        int numFeatures = dataset.get(0).size()-1; // Exclude target variable
        
        for (int i = 0; i < numTrees; i++) {
            // 1. Create bootstrap sample
            List<List<Float>> bootstrapSample = createBootstrapSample(dataset, numInstances);
            
            // 2. Select random feature subset (always include HbA1c)
            Set<Integer> featureSubset = selectFeatureSubset(numFeatures);
            
            // 3. Build decision tree
            trees[i] = new DecisionTree(bootstrapSample, featureSubset);
        }
    }

    private List<List<Float>> createBootstrapSample(List<List<Float>> dataset, int size) { // create a bootstrap sample for building a tree
        List<List<Float>> sample = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sample.add(dataset.get(random.nextInt(size)));
        }
        return sample;
    }
    
    private Set<Integer> selectFeatureSubset(int totalFeatures) { //choose a random subset of features for building a tree
        Set<Integer> subset = new HashSet<>();
        subset.add(HBA1C); // Always include HbA1c
        
        int subsetSize = (int) Math.sqrt(totalFeatures);
        while (subset.size() < subsetSize) {
            int feature = random.nextInt(totalFeatures);
            if (feature != DIABETES) { // Don't include target
                subset.add(feature);
            }
        }
        return subset;
    }
}