package Classifiers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Preprocessor{
    private CustomerData customerData;

    private Float Max_BMI = 300f;
    private Float Max_HbA1C = 9f;
    private Float Max_Blood_Glucose_Level = 300f;
    private Float Max_Age = 112f;

    
    public List<Float> preprocessCustomerData(CustomerData data){
        List<Float> processed = new ArrayList<>();

        processed.add(normalize((float)customerData.getAge(), Max_Age));
        processed.add(normalize(customerData.getBMI(), Max_BMI));
        processed.add(normalize(data.getHb1AC(), Max_HbA1C));
        processed.add(normalize(data.getBlood_Glucose_Level(), Max_Blood_Glucose_Level));

        // Add booleans as 0.0 or 1.0
        processed.add(data.getHeart_Disease() ? 1.0f : 0.0f);
        processed.add(data.getSex() ? 1.0f : 0.0f);
        processed.add(data.getHyperTension() ? 1.0f : 0.0f);

        return processed;
    }

    public List<List<Float>> preprocessDataset(List<String> dataset){
        List<List<Float>> splitData = dataset.stream()
        .map(line -> {
            String[] parts = line.split(",");
            List<Float> processed = new ArrayList<>();

            for (int i = 0; i < parts.length; i++) {
                String val = parts[i].trim().toLowerCase();

                // Boolean conversion

              if(val.equals("female")){ 
                   processed.add(0.0f);
              }
              else if(val.equals("male")){
                   processed.add(1.0f);
              }
              else if (val.equals("true") || val.equals("yes")) {
                   processed.add(1.0f);
              } 
              else if (val.equals("false") || val.equals("no")) {
                   processed.add(0.0f);
              } 
              else {
                   float num = Float.parseFloat(val);
                   switch (i) {
                       case 0:
                           processed.add(normalize(Max_Age, num)); break;
                       case 1:
                           processed.add(normalize(Max_BMI, num)); break;
                       case 2:
                           processed.add(normalize(Max_HbA1C, num)); break;
                       case 3:
                           processed.add(normalize(Max_Blood_Glucose_Level, num)); break;
                       default:
                           processed.add(0.0f); // If index doesn't match known feature
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