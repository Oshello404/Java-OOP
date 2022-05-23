package snacktime;

import java.lang.Math.*;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author hansj
 */

class SnackTimeHelper {
  /*
   * Get the user input String from Snack Menu
   * @return an integer of user input: 1, 2, 3, 4, 5, -1 means Cancel/Close
   */
  public static int getChoiceFromSnackMenu() {
    String choice;
    choice = SnackTime.showSnackMenu();
    if (choice == null) {
      return -1; // "Cancel" is clicked
    }
    return parseChoices(choice);
  }

  /*
   * Parse the user input String, handle exception and get the user input as an integer
   * @param choice the user input String
   * @return an integer of user input
   */
  private static int parseChoices(String choice) {
    int task;
    try { // exception handling for Invalid Number Format
      task = Integer.parseInt(choice);
    } catch (NumberFormatException e) {
      task = -1;
    }
    return task;
  }
}

class Snack {
    String name;
    double price;
    
    // Snack object constructor
    public Snack(String tempname) {
        name = tempname;
        price = genRandomPrice();
    }
    public String getName(){
        return(name);
    }
    public double getPrice(){
        return(price);
    }
    public void printMessage(){
        System.out.printf(name + " is sold at $%.2f.", price);
        System.out.println();
    }
    public double genRandomPrice(){
        Random rngObj = new Random();
        double result = (rngObj.nextInt(21)+10);
        result = result/2;
        return (result);
        // the variable result is a random number with increment 0.5 between 5.0 to 15.0
    }
}
    
public class SnackTime {
    
    private static int[] coinsInCents;
    private static String[] snackNames;
    
    private static String StringOutput; // for printing amount paid and change delivered, string concatenation is required
    private static double WindowPane; // a JOptionPane class Object for JOptionPane.showConfirmDialog when buying snacks
    
    private static double AmountPaid, Change; // AmountPaid is amount paid, change is required to store and calculate the value 
                                              // of change in dollars and coins
    
    public static Snack KitKat, Oreo, Marshmallow, Cupcake; // Snack Objects that are declared as instance fields in order to be
                                                            // accessed by showSnackMenu and main. It is initialized in main.
   
    public static String showSnackMenu(){
        // 4 strings are used here for simplicity. Concatenated to form JOptionPane.showInputDialog for buying snacks
        String temp1 = String.format("1. [$%.2f] %s", KitKat.getPrice(), KitKat.getName());
        String temp2 = String.format("2. [$%.2f] %s", Oreo.getPrice(), Oreo.getName());
        String temp3 = String.format("3. [$%.2f] %s", Marshmallow.getPrice(), Marshmallow.getName());
        String temp4 = String.format("4. [$%.2f] %s", Cupcake.getPrice(), Cupcake.getName());
        
        return JOptionPane.showInputDialog("Buy Snack : Input your choice\n"
        + temp1 + "\n" + temp2 + "\n" + temp3 + "\n" + temp4 + "\n5. Cancel");
    }
    
    public static void main(String[] args) {
        // initialize coins, snack names & prices
        coinsInCents = new int[]{1000, 500, 200, 100, 50};
        snackNames = new String[]{"KitKat", "Oreo", "Marshmallow", "Cupcake"};
        
        // initialize all 4 snack objects declared previously
        KitKat = new Snack(snackNames[0]);
        Oreo = new Snack(snackNames[1]);
        Marshmallow = new Snack(snackNames[2]);
        Cupcake = new Snack(snackNames[3]);
        
        // simply output a message how much each good is sold for by calling instance method printMessage
        KitKat.printMessage();
        Oreo.printMessage();
        Marshmallow.printMessage();
        Cupcake.printMessage();

        // Call getChoicefromSnackMenu from SnackTimeHelper class to get input from user
        int snackMenuChoice = SnackTimeHelper.getChoiceFromSnackMenu();
        // The boolean temp here is used for while loop checking. Initialize it as true to enter the while loop below
        boolean temp = true;
        
        while(temp == true){
            // While invalid input, prompt the Snack Menu window again
            while (snackMenuChoice > 5 || snackMenuChoice < -1){
                JOptionPane.showMessageDialog(null, "Invalid Input. Please try again.");
                snackMenuChoice = SnackTimeHelper.getChoiceFromSnackMenu();
            }
            
            // In the case that the user clicks Cancel or exit the window (X button) :
            if (snackMenuChoice == -1 || snackMenuChoice == 5){
                System.out.println("User closed or cancelled dialog box");
                // temp is false to exit the while loop
                temp = false;
                JOptionPane.showMessageDialog(null, "Hope to serve you again.");
            }
      
            // for each choice :
            else if (snackMenuChoice == 1){
                System.out.println("User picked 1");
                // WindowPane object for the confirm dialog
                WindowPane = JOptionPane.showConfirmDialog(null,"Insert $20 to buy KitKat?","Confirm",JOptionPane.YES_NO_OPTION);
                // Change is 20 minus the price of object
                Change = 20 - KitKat.getPrice();
                // AmountPaid is the price of object
                AmountPaid = KitKat.getPrice();
            }
            else if (snackMenuChoice == 2){
                System.out.println("User picked 2");
                WindowPane = JOptionPane.showConfirmDialog(null,"Insert $20 to buy Oreo?","Confirm",JOptionPane.YES_NO_OPTION);
                Change = 20 - Oreo.getPrice();
                AmountPaid = Oreo.getPrice();
            }
            else if (snackMenuChoice == 3){
                System.out.println("User picked 3");
                WindowPane = JOptionPane.showConfirmDialog(null,"Insert $20 to buy Marshmellow?","Confirm",JOptionPane.YES_NO_OPTION);
                Change = 20 - Marshmallow.getPrice();
                AmountPaid = Marshmallow.getPrice();
            }
            else if (snackMenuChoice == 4){
                System.out.println("User picked 4");
                WindowPane = JOptionPane.showConfirmDialog(null,"Insert $20 to buy Cupcake?","Confirm",JOptionPane.YES_NO_OPTION);
                Change = 20 - Cupcake.getPrice();
                AmountPaid = Cupcake.getPrice();
            }
            
            // As long as WindowPane object has value of 0, meaning user has clicked Ok, calculate the change in coins below :
            // Here the condition 'snackMenuChoice != -1' is also needed in the scenario that the user closed or cancelled 
            // the dialog box, which returns value of -1. Both conditions need to be fulfilled in order to enter the commands below
            if (WindowPane == 0 && snackMenuChoice != -1){
                // This integer array 'counter' is used later to store how much change respective to the array coinsInCents is to be
                // returned. 
                int[] counter = new int[]{0, 0, 0, 0, 0};
                
                for(int i = 0; i < 5; i++){
                    // If Change is greater than the index iterated from left to right (from largest 1000cents to 50 cents),
                    // add 1 to the respective index in counter then reduce Change by the value of the cents and
                    // go to the next largest cents value and continue the loop. 
                    
                    // If not, then immediately go the next largest cents value and continue the loop.
                    // Change is multiplied by 100 for calculation in cents.
                    while(Change*100 >= coinsInCents[i]){
                        counter[i]++;
                        Change = Change - (double)(coinsInCents[i])/100;
                    }
                }
            
                // Firstly initialize the string StringOutput showing amount paid via 'AmountPaid'
                StringOutput = String.format("%.2f paid\nCoins returned :\n", AmountPaid);
                // Next, iterate through the counter array. 
                
                // If there is a value of at least 1, meaning that the coins returned includes the value of the respective index 
                // from coinsInCents, then concatenate the value of the : (counter array) x (coinsInCents array) to StringOutput
                // in that index. 
                // Do this for all 5 elements in the array. If the value is 0, skip and go to the next index.
                for(int i = 0; i < 5; i++){
                    if(counter[i] >= 1){
                        StringOutput = StringOutput + String.format("$%s x %s\n", (double)(coinsInCents)[i]/100, counter[i]);
                    }
                }
                // Finally, we print StringOutput via showMessageDialog to output amount paid and the coins received as change
                JOptionPane.showMessageDialog(null, StringOutput);
                
                // Then, we prompt again the initial Snack menu choice for user to input
                snackMenuChoice = SnackTimeHelper.getChoiceFromSnackMenu();
                // If user exits or press cancel, temp is false and exit the while loop to terminate the program
                // If not, meaning user picks a value between 1-4, then go back to the while loop
                if(snackMenuChoice == -1 || snackMenuChoice == 5){
                    System.out.println("User closed or cancelled dialog box");
                    // temp is false to exit the while loop
                    temp = false;
                    JOptionPane.showMessageDialog(null, "Hope to serve you again.");
                }
                else{
                    temp = true;
                }
            }
        
            
        }

    }
 
}
