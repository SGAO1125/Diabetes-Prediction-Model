import java.util.UUID; // Import UUID class which is used to generate unique ID
public class Customer{
    private String UserID; // Unique ID for each customer 
    private String Name; // Customers name
    private String[] data; // Array to store customer's data
    
    // Initialize UserID, Name, and data
    public Customer(String name, String input[]){
        UserID = UUID.randomUUID().toString().substring(0, 12);
        Name = name;
        data = input;
    }
    public String getUserID(){ // Getter for UserID
        return UserID;
    }

    public String getName(){ // Getter for Name
        return Name;
    }
    public String[] getdata(){ // Getter for data array
        return data;
    }

    public static void main(String args[]){
    
    }
}