import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
public class DecisionTreeTest {
    @Test
    public void testPureDatasetReturnsLeaf() {
        List<List<Float>> data = new ArrayList<>(List.of(
                new ArrayList<>(List.of(1.0f, 2.0f, 3.0f, 4.0f, 5.5f, 0.0f)), // index 4 is HbA1c (5.5), index 5 is label
                new ArrayList<>(List.of(1.1f, 2.1f, 3.1f, 4.1f, 5.7f, 0.0f)),
                new ArrayList<>(List.of(0.9f, 1.9f, 2.9f, 3.9f, 5.3f, 0.0f))
        ));
        Set<Integer> featureIndices = new HashSet<>(Set.of(0, 1, 2, 3, 4));
        DecisionTree tree = new DecisionTree(data, featureIndices);
        int prediction = tree.predict(List.of(1.0f, 2.0f, 3.0f, 4.0f, 5.6f));
        assertEquals(0, prediction);
    }
    @Test
    public void testSplitOnHbA1c() {
        List<List<Float>> data = new ArrayList<>(List.of(
                new ArrayList<>(List.of(1.0f, 2.0f, 3.0f, 4.0f, 6.5f, 1.0f)),
                new ArrayList<>(List.of(1.1f, 2.1f, 3.1f, 4.1f, 5.0f, 0.0f)),
                new ArrayList<>(List.of(0.9f, 1.9f, 2.9f, 3.9f, 5.2f, 0.0f))
        ));
        Set<Integer> featureIndices = new HashSet<>(Set.of(0, 1, 2, 3, 4));
        DecisionTree tree = new DecisionTree(data, featureIndices);

        assertEquals(0, tree.predict(List.of(1.0f, 2.0f, 3.0f, 4.0f, 5.1f))); // Expected: 0
        assertEquals(1, tree.predict(List.of(1.0f, 2.0f, 3.0f, 4.0f, 6.6f))); // Expected: 1
    }
}
