import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PreprocessorTest {

    @Test
    public void testNormalization() {
        Preprocessor p = new Preprocessor(300f, 50f, 10f, 112f);
        List<String> input = Arrays.asList("50,25,5,5");
        List<List<Float>> result = p.preprocessDataset(input);

        List<Float> expected = Arrays.asList(50.0f, 0.5f, 0.5f, 0.044642857f);

        assertEquals(expected.size(), result.get(0).size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(0).get(i), 0.0001f);
        }
    }
    @Test
    public void test_preprocessDataset() {
        List<String> data = Arrays.asList(
                "Female,80.0,25.19,140,6.6,1,0,0",
                "Female,54.0,27.32,80,6.6,0,0,0",
                "Male,28.0,27.32,158,5.7,0,0,0",
                "Female,36.0,23.45,155,5.0,0,0,0",
                "Male,76.0,20.14,155,4.8,1,1,0"
        );

        // Expected data: use List<List<Float>> with Float objects
        List<List<Float>> Expectdata = Arrays.asList(
                Arrays.asList(0.0f, 80.0f / 112f, 25.19f / 300f, 140f / 300f, 6.6f / 9f, 1f, 0f, 0f),
                Arrays.asList(0.0f, 54.0f / 112f, 27.32f / 300f, 80f / 300f, 6.6f / 9f, 0f, 0f, 0f),
                Arrays.asList(1.0f, 28.0f / 112f, 27.32f / 300f, 158f / 300f, 5.7f / 9f, 0f, 0f, 0f),
                Arrays.asList(0.0f, 36.0f / 112f, 23.45f / 300f, 155f / 300f, 5.0f / 9f, 0f, 0f, 0f),
                Arrays.asList(1.0f, 76.0f / 112f, 20.14f / 300f, 155f / 300f, 4.8f / 9f, 1f, 1f, 0f)
        );

        Preprocessor testingObj = new Preprocessor();  // create instance to call non-static method
        List<List<Float>> processedData = testingObj.preprocessDataset(data);

        System.out.println("Running test_preprocessDataset...");
        boolean allPassed = true;

        for (int i = 0; i < processedData.size(); i++) {
            List<Float> actual = processedData.get(i);
            List<Float> expected = Expectdata.get(i);

            boolean rowPassed = true;
            for (int j = 0; j < actual.size(); j++) {
                float a = actual.get(j);
                float e = expected.get(j);

                if (Math.abs(a - e) > 0.001f) {
                    System.out.printf("Mismatch at row %d, index %d: expected %.5f, got %.5f%n", i, j, e, a);
                    rowPassed = false;
                    allPassed = false;
                }
            }

            if (rowPassed) {
                System.out.printf("Row %d passed.%n", i);
            } else {
                System.out.printf("Row %d failed.%n", i);
            }
        }

        System.out.println("The output:");
        for (List<Float> row : processedData) {
            System.out.println(row);
        }

        System.out.println("\nExpected output:");
        for (List<Float> row : Expectdata) {
            System.out.println(row);
        }

        if (allPassed) {
            System.out.println("All rows passed.");
        } else {
            System.out.println("Some rows failed.");
        }

    }
}
