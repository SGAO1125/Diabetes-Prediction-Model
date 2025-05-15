import java.util.UUID;
public class Customer{
    private String UserID;
    private String Name;
    private String[] data;
    
    public Customer(String name, String input[]){
        UserID = UUID.randomUUID().toString().substring(0, 12);
        Name = name;
        data = input;
    }
    public String getUserID(){
        return UserID;
    }

    public String getName(){
        return Name;
    }
    public String[] getdata(){
        return data;
    }

    public static void main(String args[]){
    
    }
}
