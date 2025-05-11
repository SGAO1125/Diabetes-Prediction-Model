public class CustomerData {

    private float BMI;
    private float Hb1AC;
    private float Blood_Glucose_Level;
    private boolean Heart_Disease;
    private int age;
    private boolean Sex; // true = Male, false = Female
    private boolean Hypertension;
    private boolean isdiabetic;

    public CustomerData(String[] data) {
        if (data.length == 8) {
            parseData(data);
        } else {
            System.out.println("Error: Expected 8 data elements.");
        }
    }

    private void parseData(String[] data) {
        try {
            BMI = Float.parseFloat(data[0]);
            Hb1AC = Float.parseFloat(data[1]);
            Blood_Glucose_Level = Float.parseFloat(data[2]);
            Heart_Disease = Boolean.parseBoolean(data[3]);
            age = Integer.parseInt(data[4]);
            Sex = Boolean.parseBoolean(data[5]);
            Hypertension = Boolean.parseBoolean(data[6]);
            isdiabetic = Boolean.parseBoolean(data[7]);
        } catch (NumberFormatException e) {
            System.out.println("Data parsing error: " + e.getMessage());
        }
    }

    public void UpdateData(String[] data) {
        if (data.length == 8) {
            parseData(data);
            System.out.println("Data successfully updated.");
        } else {
            System.out.println("Error: Expected 8 data elements.");
        }
    }

    public void EraseData() {
        BMI = 0.0f;
        Hb1AC = 0.0f;
        Blood_Glucose_Level = 0.0f;
        Heart_Disease = false;
        age = 0;
        Sex = false;
        Hypertension = false;
        isdiabetic = false;
        System.out.println("Data erased.");
    }

    public void ResetForm() {
        BMI = 22.0f;
        Hb1AC = 5.5f;
        Blood_Glucose_Level = 100.0f;
        Heart_Disease = false;
        age = 30;
        Sex = false;
        Hypertension = false;
        isdiabetic = false;
        System.out.println("Form reset to default values.");
    }

    public void DisplayAndPredict() {
        System.out.println("\n--- Customer Data ---");
        System.out.println("BMI: " + BMI);
        System.out.println("Hb1AC: " + Hb1AC);
        System.out.println("Blood Glucose Level: " + Blood_Glucose_Level);
        System.out.println("Heart Disease: " + Heart_Disease);
        System.out.println("Age: " + age);
        System.out.println("Sex: " + (Sex ? "Male" : "Female"));
        System.out.println("Hypertension: " + Hypertension);
        System.out.println("Is Diabetic: " + isdiabetic);

        if (BMI >= 30 || Hb1AC >= 6.5 || Blood_Glucose_Level >= 180) {
            System.out.println("Prediction: Potentially Diabetic.");
        } else {
            System.out.println("Prediction: Data within normal ranges.");
        }
    }

    public String[] toDataArray() {
        return new String[]{
            String.valueOf(BMI),
            String.valueOf(Hb1AC),
            String.valueOf(Blood_Glucose_Level),
            String.valueOf(Heart_Disease),
            String.valueOf(age),
            String.valueOf(Sex),
            String.valueOf(Hypertension),
            String.valueOf(isdiabetic)
        };
    }

    public void Exit() {
        System.out.println("Exiting...");
    }
}