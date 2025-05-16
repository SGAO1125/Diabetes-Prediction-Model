import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class CustomerData {

    // Store customers with a unique IDs
    private final HashMap<String, Customer> customerMap = new HashMap<>();
    private final String FILE_NAME = "User.txt"; // File used for saving customer data

    // Health data of current customer
    private float BMI;
    private float Hb1AC;
    private float Blood_Glucose_Level;
    private boolean Heart_Disease;
    private int Age;
    private boolean Sex; // true = Male, false = Female
    private boolean Hypertension;
    private boolean isdiabetic;

    public CustomerData() {
        loadCustomersFromFile();
    }

    // Getter methods
    public float getBMI() { return BMI; }
    public float getHb1AC() { return Hb1AC; }
    public float getBlood_Glucose_Level() { return Blood_Glucose_Level; }
    public boolean getHeart_Disease() { return Heart_Disease; }
    public int getAge() { return Age; }
    public boolean getSex() { return Sex; }
    public boolean getHyperTension() { return Hypertension; }

    // Launches GUI for user input
    public void launchGUI() {
        JFrame frame = new JFrame("Customer Data Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel displayPanel = new JPanel(new BorderLayout());

        // Input fields
        JTextField nameField = new JTextField();

        String[] sexOptions = {"0 - Female", "1 - Male"};
        JComboBox<String> sexBox = new JComboBox<>(sexOptions);

        JTextField ageField = new JTextField();
        JTextField bmiField = new JTextField();
        JTextField glucoseField = new JTextField();
        JTextField hb1acField = new JTextField();

        String[] heartDiseaseOptions = {"0 - No", "1 - Yes"};
        JComboBox<String> heartDiseaseBox = new JComboBox<>(heartDiseaseOptions);

        String[] hypertensionOptions = {"0 - No", "1 - Yes"};
        JComboBox<String> hypertensionBox = new JComboBox<>(hypertensionOptions);

        // Display area for data output
        JTextArea displayArea = new JTextArea(12, 40);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Buttons for submit and show all customers
        JButton submitButton = new JButton("Submit & Save");
        JButton showAllButton = new JButton("Show All Customers");

        // Add labels and input fields to input panel
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Sex (0 = Female, 1 = Male):"));
        inputPanel.add(sexBox);
        inputPanel.add(new JLabel("Age (1 - 112):"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("BMI (1 - 50):"));
        inputPanel.add(bmiField);
        inputPanel.add(new JLabel("Blood Glucose Level (1 - 300):"));
        inputPanel.add(glucoseField);
        inputPanel.add(new JLabel("HbA1C Level (3.5 - 9):"));
        inputPanel.add(hb1acField);
        inputPanel.add(new JLabel("Heart Disease (0 = No, 1 = Yes):"));
        inputPanel.add(heartDiseaseBox);
        inputPanel.add(new JLabel("Hypertension (0 = No, 1 = Yes):"));
        inputPanel.add(hypertensionBox);
        
        // Buttons to button panel
        buttonPanel.add(submitButton);
        buttonPanel.add(showAllButton);
        // Scrollable display area 
        displayPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        // Add all panels to main frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(displayPanel, BorderLayout.SOUTH);

        // Handler for "Submit & Save" button
        submitButton.addActionListener(e -> {
            try {
                // Validate user name
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    displayArea.setText("Error: Name is required.");
                    return;
                }

                // Validate user input values
                int sex = sexBox.getSelectedIndex();
                int age = Integer.parseInt(ageField.getText());
                float bmi = Float.parseFloat(bmiField.getText());
                float glucose = Float.parseFloat(glucoseField.getText());
                float hb1ac = Float.parseFloat(hb1acField.getText());
                int heartDisease = heartDiseaseBox.getSelectedIndex();
                int hypertension = hypertensionBox.getSelectedIndex();
                int isdiabetic = -1;    // Default as unknown

                // No out of bounds input
                if (age < 1 || age > 112) throw new NumberFormatException("Age must be between 1 and 112.");
                if (bmi < 1 || bmi > 50) throw new NumberFormatException("BMI must be between 1 and 50.");
                if (glucose < 1 || glucose > 300) throw new NumberFormatException("Glucose must be between 1 and 300.");
                if (hb1ac < 3.5 || hb1ac > 9) throw new NumberFormatException("HbA1C must be between 3.5 and 9.");

                // Check for existing name
                boolean nameExists = customerMap.values().stream()
                        .anyMatch(c -> c.getName().equalsIgnoreCase(name));

                if (nameExists) {
                    // If the name exists ask user if they want to overwrite
                    int option = JOptionPane.showConfirmDialog(frame,
                            "Customer with this name already exists.\nDo you want to overwrite the existing data?",
                            "Duplicate Name Found",
                            JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.NO_OPTION) {
                        displayArea.setText("Please enter a different name.");
                        return;
                    }

                    // Remove previous entry with same name
                    customerMap.entrySet().removeIf(entry -> entry.getValue().getName().equalsIgnoreCase(name));
                }

                // Store values to field
                Sex = (sex == 1);
                Age = age;
                BMI = bmi;
                Blood_Glucose_Level = glucose;
                Hb1AC = hb1ac;
                Heart_Disease = (heartDisease == 1);
                Hypertension = (hypertension == 1);

                // Customer string data array
                String[] data = {
                        String.valueOf(sex),
                        String.valueOf(age),
                        String.valueOf(bmi),
                        String.valueOf(glucose),
                        String.valueOf(hb1ac),
                        String.valueOf(heartDisease),
                        String.valueOf(hypertension),
                        String.valueOf(isdiabetic)
                };

                // Create and store new customer in map
                Customer customer = new Customer(name, data);
                customerMap.put(customer.getUserID(), customer);
                saveCustomersToFile();
                // Show confirmation that customer was saved successfully
                displayArea.setText("Customer saved!\nID: " + customer.getUserID());
                // Print to terminal
                Display();

            } catch (NumberFormatException ex) {
                displayArea.setText("Error: " + ex.getMessage());
            }
        });

        // Handler for "Show All Customers"
        showAllButton.addActionListener(e -> {
            if (customerMap.isEmpty()) {
                displayArea.setText("No customers stored.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Customer c : customerMap.values()) {
                    sb.append("ID: ").append(c.getUserID()).append("\n")
                      .append("Name: ").append(c.getName()).append("\n")
                      .append("Data: ").append(String.join(", ", c.getdata())).append("\n\n");
                }
                displayArea.setText(sb.toString());
            }
        });

        frame.setVisible(true);
    }

    // Load data from User.txt into customerMap
    private void loadCustomersFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String id = null, name = null;
            String[] data = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    id = line.substring(4).trim();
                } else if (line.startsWith("Name: ")) {
                    name = line.substring(6).trim();
                } else if (line.startsWith("Data: ")) {
                    data = line.substring(6).split(", ");
                    if (id != null && name != null && data != null) {
                        Customer customer = new Customer(name, data);
                        customerMap.put(id, customer);
                    }
                    id = name = null;
                    data = null;
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load customers: " + e.getMessage());
        }
    }

    // Save current customers to User.txt file
    private void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Customer customer : customerMap.values()) {
                writer.write("ID: " + customer.getUserID());
                writer.newLine();
                writer.write("Name: " + customer.getName());
                writer.newLine();
                writer.write("Data: " + String.join(", ", customer.getdata()));
                writer.newLine();
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save customers: " + e.getMessage());
        }
    }

    // Print the current customers data to terminal
    public void Display() {
    System.out.println("\n--- Customer Data ---");
    System.out.println("Sex: " + (Sex ? "Male" : "Female"));
    System.out.println("Age: " + Age);
    System.out.println("BMI: " + BMI);
    System.out.println("Blood Glucose Level: " + Blood_Glucose_Level);
    System.out.println("Hb1AC: " + Hb1AC);
    System.out.println("Heart Disease: " + (Heart_Disease ? "Yes" : "No"));
    System.out.println("Hypertension: " + (Hypertension ? "Yes" : "No"));
    }
}