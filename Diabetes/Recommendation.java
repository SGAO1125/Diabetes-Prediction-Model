import java.util.*;

public class Recommendation { //give user personalized meal plan

    private int date;
    private Map<String, List<String>> foodTree;

    public Recommendation() {
        this.date = getCurrentDate();
        this.foodTree = initializeFoodTree();
    }

    private int getCurrentDate() { // Get the current date
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private Map<String, List<String>> initializeFoodTree() {// Initialize the food tree with recommended foods based on health conditions
        Map<String, List<String>> tree = new HashMap<>();

        tree.put("High_BMI", Arrays.asList("Grilled chicken", "Steamed vegetables", "Quinoa", "Avocados"));
        tree.put("High_Glucose", Arrays.asList("Oats", "Lentils", "Whole grains", "Leafy greens"));
        tree.put("High_HbA1C", Arrays.asList("Brown rice", "Sweet potatoes", "Nuts", "Beans"));
        tree.put("Heart_Disease", Arrays.asList("Salmon", "Olive oil", "Berries", "Spinach"));
        tree.put("Hypertension", Arrays.asList("Bananas", "Beets", "Low-fat yogurt", "Garlic"));

        return tree;
    }

    public String getPersonalizedMealPlan(CustomerData customer) {//by looking at the customer data, we can give them a personalized meal plan
        List<String> recommended = new ArrayList<>();

        if (customer.getBMI() > 25.0f) { // If customer BMI is greater than 25
            recommended.addAll(foodTree.get("High_BMI"));
        }
        if (customer.getBlood_Glucose_Level() > 140.0f) { //If Customer Blood_Glucose_Level is greater than 140
            recommended.addAll(foodTree.get("High_Glucose"));
        }
        if (customer.getHb1AC() > 6.0f) { //If Customer Hb1Ac is greater than 6.0
            recommended.addAll(foodTree.get("High_HbA1C"));
        }
        if (customer.getHeart_Disease()) { //If Customer has Heart_Disease
            recommended.addAll(foodTree.get("Heart_Disease"));
        }
        if (customer.getHyperTension()) {//If Customer has HyperTension
            recommended.addAll(foodTree.get("Hypertension"));
        }

        if (recommended.isEmpty()) {
            return "Your values are in a healthy range! Stick to a balanced diet: lean protein, fruits, vegetables, and whole grains.";
        }

        Set<String> uniqueFoods = new LinkedHashSet<>(recommended); // Remove duplicates, keep order
        return "Personalized Food Recommendations:\n- " + String.join("\n- ", uniqueFoods);
    }

    public String logMealHistory(int customerID, String[] meal) { // Log the meal history for a customer
        return "Meal logged for customer ID: " + customerID + " → " + Arrays.toString(meal);
    }

    public String compareNutritionGoals(String[] dataset) {
        return "Nutrition comparison complete. Results: [Mock comparison based on dataset]";
    }

    public void generateMonthlyFood() {
        // Placeholder for monthly planning logic
    }

    public void generateDailyMeal() {
        // Placeholder for daily meal generation logic
    }

    public void generateHealthyDatasetAverage(List<CustomerData> healthyCustomers) { // Calculate and print average values from the healthy dataset
        if (healthyCustomers.isEmpty()) {
            System.out.println("No healthy datasets provided.");
            return;
        }

        double totalBMI = 0;
        double totalGlucose = 0;
        double totalHbA1C = 0;
        int totalHeartDisease = 0;
        int totalHypertension = 0;

        int count = healthyCustomers.size();

        for (CustomerData customer : healthyCustomers) {
            totalBMI += customer.getBMI();
            totalGlucose += customer.getBlood_Glucose_Level();
            totalHbA1C += customer.getHb1AC();
            totalHeartDisease += customer.getHeart_Disease() ? 1 : 0;
            totalHypertension += customer.getHyperTension() ? 1 : 0;
        }

        System.out.println("\n--- Average Values from Healthy Dataset ---");
        System.out.printf("Average BMI: %.2f\n", totalBMI / count);
        System.out.printf("Average Blood Glucose Level: %.2f\n", totalGlucose / count);
        System.out.printf("Average HbA1C: %.2f\n", totalHbA1C / count);
        System.out.printf("Heart Disease Presence (0-1): %.2f\n", (double) totalHeartDisease / count);
        System.out.printf("Hypertension Presence (0-1): %.2f\n", (double) totalHypertension / count);
    }
}
