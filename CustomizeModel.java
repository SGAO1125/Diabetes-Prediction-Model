import java.util.ArrayList;
import java.util.List;

public class CustomizeModel{
    private List<String> customizedDataset;

    public CustomizeModel(List<String> dataset, Float Hb1Ac) {
        this.customizedDataset = new ArrayList<>();
        
        // Validate input
        if (dataset == null || Hb1Ac == null) {
            throw new IllegalArgumentException("Dataset and Hb1Ac cannot be null");
        }
        
        // HbA1c index in the dataset (assuming it's at index 4 as per your requirement)
        final int HBA1C_INDEX = 4;
        
        for (String dataRow : dataset) {
            // Split the string by commas
            String[] columns = dataRow.split(",");
            
            // Make sure the row has enough columns
            if (columns.length > HBA1C_INDEX) {
                try {
                    // Parse the HbA1c value from the string
                    float rowHbA1c = Float.parseFloat(columns[HBA1C_INDEX].trim());
                    
                    // Check if it's within the desired range
                    if (rowHbA1c >= Hb1Ac - 2 && rowHbA1c <= Hb1Ac + 2) {
                        customizedDataset.add(dataRow);
                    }
                } catch (NumberFormatException e) {
                    // Handle case where HbA1c value isn't a valid float
                    System.err.println("Skipping row with invalid HbA1c value: " + dataRow);
                }
            }
        }
    }

    public List<String> getCustomizedDataset() {
        return customizedDataset;
    }
}