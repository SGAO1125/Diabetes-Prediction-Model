import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

public class CustomizedModelTest {
    private static final List<String> TEST_DATA = Arrays.asList( //testing data
            "Female,80.0,25.19,140,6.6,1,0,0",
            "Female,54.0,27.32,80,6.6,0,0,0",
            "Male,28.0,27.32,158,5.7,0,0,0",
            "Female,36.0,23.45,155,5.0,0,0,0",
            "Male,76.0,20.14,155,4.8,1,1,0",
            "Female,35.0,24.89,158,5.1,0,0,0",
            "Female,66.0,26.51,155,6.2,0,0,0",
            "Female,36.0,35.24,126,6.5,0,0,1",
            "Female,39.0,24.23,140,5.0,0,0,0",
            "Male,40.0,23.88,90,4.8,0,0,0"
    );

    @Test
    public void testFilterByHbA1c() {
        // Expected results for target HbA1c = 6.0 (±2 range = 4.0-8.0)
        List<String> expectedResults = Arrays.asList( //expected results
                "Female,80.0,25.19,140,6.6,1,0,0",
                "Female,54.0,27.32,80,6.6,0,0,0",
                "Male,28.0,27.32,158,5.7,0,0,0",
                "Female,66.0,26.51,155,6.2,0,0,0",
                "Female,36.0,35.24,126,6.5,0,0,1",
                "Female,35.0,24.89,158,5.1,0,0,0",
                "Female,39.0,24.23,140,5.0,0,0,0"
        );

        CustomizeModel model = new CustomizeModel(TEST_DATA, 6.0f);
        List<String> actualResults = model.getCustomizedDataset();

        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void testEdgeCaseHbA1cMin() {
        // Expected results for minimum valid HbA1c (3.5) (±2 range = 3.5-5.5)
        List<String> expectedResults = Arrays.asList( //expected results
                "Female,36.0,23.45,155,5.0,0,0,0",
                "Male,76.0,20.14,155,4.8,1,1,0",
                "Female,35.0,24.89,158,5.1,0,0,0",
                "Female,39.0,24.23,140,5.0,0,0,0",
                "Male,40.0,23.88,90,4.8,0,0,0"
        );

        CustomizeModel model = new CustomizeModel(TEST_DATA, 3.5f);
        List<String> actualResults = model.getCustomizedDataset();

        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void testEdgeCaseHbA1cMax() {
        // Expected results for maximum valid HbA1c (9.0) (±2 range = 7.0-9.0)
        List<String> expectedResults = Arrays.asList(//expected results
                // No records should be returned as all are below 7.0
        );

        CustomizeModel model = new CustomizeModel(TEST_DATA, 9.0f);
        List<String> actualResults = model.getCustomizedDataset();

        assertEquals(expectedResults, actualResults);
    }
}
