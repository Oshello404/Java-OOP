/**
 * CSCI1130 Assignment 1 Future Value
 *
 * I declare that the assignment here submitted is original 
 * except for source material explicitly acknowledged,
 * and that the same or closely related material has not been
 * previously submitted for another course.
 * I also acknowledge that I am aware of university policy and
 * regulations on honesty in academic work, and of the disciplinary
 * guidelines and procedures applicable to breaches of such
 * policy and regulations, as contained in the website.
 * 
 * University Guideline on Academic Honesty:
 *  http://www.cuhk.edu.hk/policy/academichonesty
 * Faculty of Engineering Guideliens to Academic Honesty:
 *  http://www.erg.cuhk.edu.hk/erg/AcademicHonesty
 * 
 * Student Name: Hans Nathanael Junoes
 * Student ID  : 1155147304
 * Date        : 8 October 2021
 */

package futurevalue;

import java.util.Scanner;
import static java.lang.Math.*;

public class FutureValue {
    
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Input Principal [$10000.00 - $109700.00]:");
        double P = keyboard.nextDouble();
        
        while (P < 10000.00 || P > 109700.00){
            System.out.println("Invalid principal, please enter again.\nInput Principal [$10000.00 - $109700.00]:");
            P = keyboard.nextDouble();
        }
        // error checking
        
        System.out.println("Input Annual Interest Rate [1.0% - 10.0%]:");
        double r = keyboard.nextDouble()/100;
        // divided by 100 for percentage
        while (r < 0.01 || r > 0.1) {
            System.out.println("Invalid annual interest rate, please enter again.\nInput Annual Interest Rate [1.0% - 10.0%]:");
            r = keyboard.nextDouble()/100;
            System.out.println("r is " + r);
        }
        
        System.out.println("Input Timespan [2 - 10 years]:");
        int T = keyboard.nextInt();
        while (T < 2 || T > 10){
            System.out.println("Invalid timespan, please enter again.\nInput Timespan [2 - 10 years]:");
            T = keyboard.nextInt();
        }
            
        System.out.println("Input Compounding Period [2, 3 or 6 months]:");
        double temp = keyboard.nextDouble();
        // the user inputs compounding period, not compounding frequency
        while (temp != 2 && temp != 3 && temp != 6) {
            System.out.println("Invalid compounding period, please enter again.\nInput Compounding Period [2, 3 or 6 months]:");
            temp = keyboard.nextDouble();
        }
        // a temporary variable temp is needed to avoid rounding in integer types (m)
        // m needs to be an integer for switch statement further below
        
        int m = 12/(int)temp;
        // m is compounding frequency, not compounding period
        // hence temp/12 yields m, the compounding frequency
        // is a double instead of an integer, to avoid round up/down by Java
        System.out.println("m is " + m);
        
        double FV = P*(pow((1+r/m),(1*m)));
        System.out.printf("Future Value after " + 1 + " year is %.2f\n",FV);
        FV = P*(pow((1+r/m),(2*m)));
        System.out.printf("Future Value after " + 2 + " years is %.2f\n",FV);
        
        if(T >= 3){
            System.out.println("...");
            FV = P*(pow((1+r/m),(T*m)));
            System.out.printf("Future Value after " + T + " years is %.2f\n",FV);
        }
        // print future value after T years as well if T>=3
        
        double T2 = log(2)/(m*(log(1+r/m)));
        // System.out.println("(Exact)Time to double asset : " + T2);
        // the above line prints exact time to double asset in decimals
        
        switch (m){
            case 2 :
                if(T2-floor(T2) <= 0.5) {
                    System.out.println("Time do double asset : " + (int) floor(T2) + " years and 6 months");
                }
                else
                    System.out.println("Time do double asset : " + (int) floor(T2));
                break;
            case 4 :
                if(T2-floor(T2) > 0 && T2-floor(T2) <= 0.25) {
                    System.out.println("Time do double asset : " + (int) floor(T2) + " years and 3 months");
                }
                else if(T2-floor(T2) > 0.25 && T2-floor(T2) <= 0.5) {
                    System.out.println("Time do double asset : " + (int) floor(T2) + " years and 6 months");
                }
                else if(T2-floor(T2) > 0.5 && T2-floor(T2) <= 0.75) {
                    System.out.println("Time do double asset : " + (int) floor(T2) + " years and 9 months");
                }
                else
                    System.out.println("Time do double asset : " + (int) floor(T2)+1 + " years");
                break;
            case 6 :
                if(T2-floor(T2) > 0 && T2-floor(T2) <= 1/6) {
                    System.out.println("Time do double asset : " + (int) floor(T2) + " years and 2 months");
                }
                else if(T2-floor(T2) > 1/6 && T2-floor(T2) <= 1/3) {
                    System.out.println("Time do double asset : " + (int) floor(T2) + " years and 4 months");
                }
                else if(T2-floor(T2) > 1/3 && T2-floor(T2) <= 1/2) {
                    System.out.println("Time do double asset : " + (int) floor(T2) + " years and 6 months");
                }
                else if(T2-floor(T2) > 1/2 && T2-floor(T2) <= 2/3) {
                    System.out.println("Time do double asset : " + (int) floor(T2) + " years and 8 months");
                }
                else if(T2-floor(T2) > 2/3 && T2-floor(T2) <= 5/6) {
                    System.out.println("Time do double asset : " + (int) floor(T2) + " years and 10 months");
                }
                else {
                    System.out.println("Time do double asset : " + (int) (floor(T2) + 1) + " years");
                }
                break;
                
        }
        
    }
}
