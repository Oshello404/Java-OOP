package musicapp;

import java.util.*;
import java.io.*;
/**
 *
 * @author hansj
 */
public class MusicApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
        // Receives input String from user
        Scanner filenameinput = new Scanner(System.in);
        // To store the name of the file
        String filename;
        // errorCounter is used to measure amount of erros up to 3. When 3, it terminates. Status is used to store 
        // this true/false value
        int errorCounter = 0;
        boolean status = false;
        
        //Initialize variable to store object reference
        FileInputStream openFile = null;
        
        // String comment is used to read lines in the file
        // charCheck is used to identify '#' and '*' at the beginning of each new line
        String comment;
        char charCheck;
        
        // counter will later be used to keep track of time signature for printing '|'
        int counter = 0;
        // intCheck will be explained later. It is just used to check integers
        int intCheck = 0;
        
        do {
            // Exit system if errorCounter is 3 or more
            if (errorCounter == 3){
                System.exit(1);
            }
            System.out.print("Song filename : ");
            filename = filenameinput.nextLine();
            
            try {
                openFile = new FileInputStream(filename);
                // If file is opened, status is true
                status = true;

                // Get the time signature, default is 4.
                int timesignature = 4;

                // Finally, we have the PrintStream and Scanner objects
                // PrintStream object to output .txt file
                PrintStream newFile = null;
                Scanner songFile = null;

                try {
                    // Opens file with input String from user (filename)
                    songFile = new Scanner(openFile);
                    // Create a new file
                    newFile = new PrintStream("melody_" + filename);
                    int newLinecounter = 0;
                    while(songFile.hasNextLine()){
                        comment = songFile.nextLine();   
                        // We check the first char of the scanned line
                        charCheck = comment.charAt(0);
                        if(charCheck == '#'){
                            // '#' means a comment in the file. Simply print it.
                            System.out.println(comment);
                            newFile.println(comment);
                            // newLinecounter set to 1 to avoid additional line after printing anything other than notes(Do, Re..)
                            newLinecounter = 1;
                        }
                        else if(charCheck == '*'){
                            // '*' means a time signature notation. We store it then to variable timesignature
                            timesignature = Integer.parseInt(String.valueOf(comment.charAt(1)));
                            newFile.println();
                            System.out.println();
                            System.out.println("#Time signature = " + timesignature);
                            newFile.println("#Time signature = " + timesignature);
                            newLinecounter = 1;
                        }    
                        else{
                            // Only if newLinecounter is zero will it print a new line.
                            // In other words, this if statement will be skipped if it has printed a comment or time signature
                            if(newLinecounter == 0){
                                System.out.println();
                                newFile.println();
                                newLinecounter = 1;
                            }
                            
                            for(int i = 0; i < comment.length(); i++){
                                charCheck = comment.charAt(i);
                                // Switch case to print Do to Ti and Off
                                switch(charCheck) {
                                    case 'd' :
                                        System.out.print("Do");
                                        newFile.print("Do");
                                        break;
                                    case 'r' :
                                        System.out.print("Re");
                                        newFile.print("Re");
                                        break;
                                    case 'm' :
                                        System.out.print("Me");
                                        newFile.print("Me");
                                        break;
                                    case 'f' :
                                        System.out.print("Fa");
                                        newFile.print("Fa");
                                        break;
                                    case 's' :
                                        System.out.print("So");
                                        newFile.print("So");
                                        break;
                                    case 'l' :
                                        System.out.print("La");
                                        newFile.print("La");
                                        break;
                                    case 't' :
                                        System.out.print("Ti");
                                        newFile.print("Ti");
                                        break;
                                    case 'o' :
                                        System.out.print("Off");
                                        newFile.print("Off");
                                        break;
                                }
                                // If i is odd, a.k.a it is a number (d1m2r3, all the odd indexes are numbers),
                                // Print "-" by the amount specified - 1
                                if(i%2 != 0){
                                    intCheck = Integer.parseInt(String.valueOf(charCheck));
                                    for(int j = 1; j < intCheck; j++){
                                        System.out.print("-");
                                        newFile.print("-");
                                        // Anytime we print a single "-", add one to counter
                                        counter++;
                                    }
                                    // There is another counter++ here since counter is added by amount specified - 1
                                    counter++;
                                    // A space after every note
                                    System.out.print(" ");
                                    newFile.print(" ");
                                }
                                // If counter is equal to timesignature, reset counter and print "| "
                                if(counter == timesignature){
                                    counter = 0;
                                    System.out.print("| ");
                                    newFile.print("| ");
                                }
                            }
                            // Reset newLinecounter after every iteration of a line of notes
                            newLinecounter = 0;
                        }
                    }
                    // Before entering the while loop again to find the next line, print a new line
                    System.out.println();
                    newFile.println();
                    
                } 
                // Takes care of output file exception
                catch (Exception e){
                    System.out.println("Cannot output melody file!");
                }
                //close the printstream and scanner objects
                finally{
                    if (newFile != null)
                        newFile.close();
                    if (songFile != null)
                        songFile.close();
                }
            } //Catches erros in file input and output. errorCounter is to terminate program if more than 2
            catch (IOException e){
                System.out.println("Something wrong!");
                errorCounter +=1;
            }
            //close the FileInputStream object
            finally{
                if (openFile != null)
                    openFile.close();
            }
        }
        while(!status && errorCounter <= 3);
    }
}
