import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Single Scanner instance for the entire class
    private static final Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {
        // Main loop for menu options
        while (true) {
            // Display the mnenu
            System.out.println();
            System.out.println(
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MAIN MENU ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(
                    "1) Add Home | 2) Remove Home | 3) Update Home | 4) Search Home | 5) Browse Homes | 6) Exit");
            System.out.println("Select an action: ");
            String user_input = scnr.nextLine();
            // get user selection, call corresponding method
            switch (user_input) {
                case "1":
                    addHome();
                    break;
                case "2":
                    System.out.println(removeHome());
                    break;
                case "3":
                    System.out.println(updateHome());
                    break;
                case "4":
                    System.out.println(searchHome());
                    break;
                case "5":
                    System.out.println(browseHomes());


                    // Ask if the user wants to print the info to a file
                    System.out.println("Would you like to print the information to a file? (Y/N)");
                    String printToFileResponse = scnr.nextLine().trim().toUpperCase();

                    if (printToFileResponse.equals("Y")){
                        String filePath = ""; // file path
                        System.out.println(printToFile(filePath));
                    } else if (printToFileResponse.equals("N")){
                        System.out.println("File will not be printed.");
                    } else {
                        System.out.println("Invalid input. File will not be printed.");
                    }

                    break;
                case "6":
                    System.out.println("Program ended.");
                    scnr.close();
                    System.exit(6);
                default:
                    System.out.println("Invalid. Select an action from menu by entering a number 1 - 6.");
                    System.out.println("");
                    main(args);
                    break;
            }
        }
    }

    public static String addHome() {
        try {
            // get user input for new home
            System.out.print("Enter the square footage: ");
            int square_feet = scnr.nextInt();
            scnr.nextLine(); // Consume the leftover newline character

            System.out.print("Enter the address: ");
            String address = scnr.nextLine();
            System.out.print("Enter the city: ");
            String city = scnr.nextLine();
            System.out.print("Enter the state: ");
            String state = scnr.nextLine();
            System.out.print("Enter the zip code: ");
            int zip_code = scnr.nextInt();
            scnr.nextLine(); // Consume the leftover newline character

            System.out.print("Enter the model name: ");
            String model_name = scnr.nextLine();
            System.out.print("Enter the sale status (sold, available, under contract): ");
            String sale_status = scnr.nextLine();

            Home newHome = new Home(square_feet, address, city, state, zip_code, model_name, sale_status);
            Home.home_directory.add(newHome);

            return "Home added successfully.";
        } catch (Exception e) {
            return "Failed to add home: " + e.getMessage();
        }
    }

    public static String updateHome() {
        try {
            System.out.println("Enter the street address of the home you wish to update: ");
            String addressToUpdate = scnr.nextLine().trim(); // get address from user,

            // check if directory is empty
            if (Home.home_directory.isEmpty()) {
                return "The directory is empty. Nothing to update.";
            }

            // find home to update
            Home homeToUpdate = null;
            for (Home h : Home.home_directory) { // loop through the home directory with a for each loop
                if (h.getAddress().equalsIgnoreCase(addressToUpdate)) { // get address of each home, if statement
                                                                        // compares retrieved address with user's input
                    homeToUpdate = h;
                    System.out.println();
                    System.out.println("Home found: " + homeToUpdate); // debug statement
                    System.out.println();
                    break;
                }
            }
            if (homeToUpdate != null) {
                // Get new details for the home
                System.out.println("Enter the new square footage: ");
                int newSquareFeet = scnr.nextInt();
                scnr.nextLine(); // consume leftover newline
                System.out.println("Enter the new street address: ");
                String newAddress = scnr.nextLine().trim();
                System.out.println("Enter the new city: ");
                String newCity = scnr.nextLine().trim();
                System.out.println("Enter the new state: ");
                String newState = scnr.nextLine().trim();
                System.out.println("Enter the new zip code: ");
                int newZipCode = scnr.nextInt();
                scnr.nextLine(); // consume leftover newline
                System.out.println("Enter the new model name: ");
                String newModelName = scnr.nextLine().trim();
                System.out.println("Enter the new sale status (sold, available, under contract)");
                String newSaleStatus = scnr.nextLine().trim();
                // update the existing home object
                homeToUpdate.setSquareFeet(newSquareFeet);
                homeToUpdate.setAddress(newAddress);
                homeToUpdate.setCity(newCity);
                homeToUpdate.setState(newState);
                homeToUpdate.setZipCode(newZipCode);
                homeToUpdate.setModelName(newModelName);
                homeToUpdate.setSaleStatus(newSaleStatus);

                return "Home updated successfully.";
            } else {
                return "Home not found in the directory.";
            }
        } catch (Exception e) {
            return "Failed to update home: " + e.getMessage();
        }
    }

    public static String removeHome() {
        try {
            System.out.println("Enter street address of home you wish to remove: ");
            String addressToRemove = scnr.nextLine().trim();

            // check if directory is empty.
            if (Home.home_directory.isEmpty()) {
                System.out.println("The directory is empty. Nothing to remove.");
            }

            // find home to remove by street address
            Home homeToRemove = null;
            for (Home h : Home.home_directory) {
                if (h.getAddress().equalsIgnoreCase(addressToRemove)) {
                    homeToRemove = h;
                    break; // exist loop once home is found.
                }
            }
            if (homeToRemove != null) {
                Home.home_directory.remove(homeToRemove);
                return "Home successfully removed." + homeToRemove.getAddress();
            } else {
                return "Home not found in the directory.";
            }
        } catch (Exception e) {
            return "Failed to remove home: " + e.getMessage();
        }
    }

    public static String searchHome() {
        try {
            System.out.println("Enter street address of home you wish to find: ");
            String addressToSearch = scnr.nextLine().trim();
            System.out.println("Searching for: " + addressToSearch);  // Debug statement

            // check if home exists or not
            if (Home.home_directory.isEmpty()) {
                return "Directory empty. Nothing to search.";
            }

            // find home by street address
            Home foundHome = null;
            for (Home h : Home.home_directory) {
                // System.out.println("Checking home with address: " + h.getAddress());  // Debug statement
                if (h.getAddress().equalsIgnoreCase(addressToSearch)) {
                    foundHome = h;
                    break;
                }
            }
            if (foundHome != null) {
                // System.out.println("Home found: " + foundHome.toString());
                return "Home found: " + foundHome.toString();
            } else {
                System.out.println("Home not found in directory.");
                return "Home not found in directory.";
            }
        } catch (Exception e) {
            System.out.println("Failed to search home: " + e.getMessage());
            return "Failed to search home: " + e.getMessage();
        }
    }


    public static String browseHomes() {
        try {
            // check if home exists or not
            if (Home.home_directory.isEmpty()) {
                return "The directory is empty. Nothing to display.";
            } else {
                System.out.println("Home Directory:\n");
                // System.out.println(Home.home_directory);
                for (Home h : Home.home_directory){
                    System.out.println(h);
                }
            }
            return "Homes listed successfully.";
        } catch (Exception e) {
            return "Failed to list homes: " + e.getMessage();
        }
    }

    public static String printToFile(String filepPath){
        try (FileWriter writer = new FileWriter(filepPath)){
            for (Home h : Home.home_directory){
                writer.write(h.toString() + System.lineSeparator());
            }
            return "File successfully written to: " + filepPath;
        } catch (IOException e){
            return "Failed to write to file: " + e.getMessage();
        }   
    }

}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
class Home { // Home class is not public since Main is public
    private int square_feet;
    private String address;
    private String city;
    private String state;
    private int zip_code;
    private String model_name;
    private String sale_status;

    static ArrayList<Home> home_directory = new ArrayList<>();

    // constructor
    public Home(int square_feet, String address, String city, String state, int zip_code, String model_name,
            String sale_status) {
        this.square_feet = square_feet;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.model_name = model_name;
        this.sale_status = sale_status;
    }

    // getters and setters
    public int getSquareFeet() {
        return square_feet;
    }

    public void setSquareFeet(int square_feet) {
        this.square_feet = square_feet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public int getZipCode() {
        return zip_code;
    }

    public void setZipCode(int zip_code) {
        this.zip_code = zip_code;
    }

    public String getModelName() {
        return model_name;
    }

    public void setModelName(String model_name) {
        this.model_name = model_name;
    }

    public String getSaleStatus() {
        return sale_status;
    }

    public void setSaleStatus(String sale_status) {
        this.sale_status = sale_status;
    }

    @Override
    public String toString() {
        return "Square Feet: " + square_feet + ", Address: " + address + ", City: " + city + ", State: " + state
                + ", ZipCode: " + zip_code
                + ", Model: " + model_name + ", Status: " + sale_status + " | ";
    }
}