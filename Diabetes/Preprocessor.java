import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Preprocessor{
    private CustomerData customerData;

    private String sex;
    private Float Max_BMI;
    private Float Max_HbA1C;
    private Float Max_Blood_Glucose_Level;
    private Float Max_Age;

    public Preprocessor() {
        this.sex = "undefined";
        this.Max_Age = 112f;
        this.Max_BMI = 300f;
        this.Max_HbA1C = 9f;
        this.Max_Blood_Glucose_Level = 300f;
    }
    public Preprocessor(Float Max_BMI, Float Max_HbA1C, Float Max_Blood_Glucose_Level, Float Max_Age ) {
        this.sex = "undefined";
        this.Max_BMI = Max_BMI;
        this.Max_HbA1C = Max_HbA1C;
        this.Max_Blood_Glucose_Level = Max_Blood_Glucose_Level;
        this.Max_Age = Max_Age;

    }
    public Preprocessor(String sex, Float Max_BMI, Float Max_HbA1C, Float Max_Blood_Glucose_Level, Float Max_Age ) {
        this.sex = sex;
        this.Max_BMI = Max_BMI;
        this.Max_HbA1C = Max_HbA1C;
        this.Max_Blood_Glucose_Level = Max_Blood_Glucose_Level;
        this.Max_Age = Max_Age;

    }

    public List<List<Float>> preprocessDataset(List<String> dataset){
        List<List<Float>> splitData = dataset.stream()
        .map(line -> {
            String[] parts = line.split(",");
            List<Float> processed = new ArrayList<>();
            int offset = 1;

            for (int i = 0; i < parts.length; i++) {
                String val = parts[i].trim().toLowerCase();

                // Boolean conversion

              if(val.equals("female")){ 
                   processed.add(0.0f);
                   offset = 0;
              }
              else if(val.equals("male")){
                   processed.add(1.0f);
                   offset = 0;
              }
              else if(val.equals("undefined")){
                  processed.add(-1.0f);
              }
              else if (val.equals("true") || val.equals("yes")) {
                   processed.add(1.0f);
              } 
              else if (val.equals("false") || val.equals("no")) {
                   processed.add(0.0f);
              } 
              else {
                   float num = Float.parseFloat(val);
                   switch (i - offset) {
                       case 0:
                           processed.add(normalize(Max_Age, num)); break;
                       case 1:
                           processed.add(normalize(Max_BMI, num)); break;
                       case 2:
                           processed.add(normalize(Max_Blood_Glucose_Level, num)); break;
                       case 3:
                           processed.add(normalize(Max_HbA1C, num)); break;
                       default:
                           processed.add(num); // If index doesn't match known feature
                   }
               }
           }

            return processed;
        } )
        .collect(Collectors.toList());
        return splitData;
        
    }
    public Float normalize(float maxValue, Float value){
        if(value <= 0 || value == null){
            return 0f;
        }
        return value / maxValue;
    }

    public static void main(String args[]){

    }
}
