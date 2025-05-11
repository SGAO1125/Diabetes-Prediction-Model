public class Customer{
    private String UserID;
    private String Name;
    private String[] data;
    
    public Customer(String id, String name, String input[]){
        UserID = id;
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
        System.out.println("Hello World!!");
        String inputforSteven [] = {"han", "due", "Trhee"};
        Customer Steven = new Customer("21232324","Steven", inputforSteven);
        System.out.println("The id of the user " + Steven.Name + " is:" +  Steven.UserID);
    }
}