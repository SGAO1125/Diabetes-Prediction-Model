import java.util.ArrayList;
import java.util.List;

public class Preprocessor{
    private CustomerData customerData;
    Float Max_BMI;
    Float Max_HbA1C;
    Float Max_Blood_Glucose_Level;
    Float Max_Age;
    
    public List<Float> preprocess(CustomerData data){
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
    public float normalize(float maxValue, Float value){
        if(value <= 0 || value == null){
            return 0f;
        }
        return value / maxValue;
    }

    public static void main(String args[]){

    }
}