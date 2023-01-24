import java.util.*;
import java.security.SecureRandom;
import java.io.*;

public class main extends shortcut {
    // global variables
    public static Scanner scan = new Scanner(System.in);
    public static long startTime = 0;
    public static long endTime = 0;

    public static void main(String[] args) {
        // splash screen
        println("\nWelcome to 24 Games Solver");
        println("==========================");
        println();
        
        // menu options
        println("Options: ");
        println("[0] Exit");
        println("[1] Input Cards from Console");
        println("[2] Auto Generate cards");
        println();

        int option;

        do { // input validation
            print("Enter option [0-2]: ");
            option = scan.nextInt();
            scan.nextLine();
        } while (option < 0 || option > 2);

        println();

        switch(option){
            case 1:
                userInput();
                break;
            case 2:
                autoInput();
                break;
            default:                
                println("See ya! Thank you mate!");
                System.exit(0);
                break;
        }

        println("\nExecution time : " + (endTime-startTime) + " miliseconds");
    }

    public static void userInput(){
        println("User Input");
        println("==========");
        println();

        String[] inputs;
        String input;
        boolean reinput;

        do{ // inputs validation
            reinput = false;

            // user input
            print("Input : ");
            input = scan.nextLine();
    
            // trim and split the input
            inputs = Arrays.copyOfRange(input.trim().split("[ ]+"), 0, 4);
    
            // validation of each input
            int i = 0;
            while (!reinput && i < inputs.length){
                int ascii = (int)(inputs[i].toLowerCase().charAt(0));
                if (inputs[i].length() > 1){
                    reinput = !(inputs[i].equals("10"));
                } else if ((ascii == 97) || (ascii == 106) || (ascii == 107) || (ascii == 113) || (ascii >= 50 && ascii <= 57)) {
                    reinput = false;
                } else {
                    reinput = true;
                }
                i++;
            }

        } while (reinput);

        parseInput(inputs);

    }

    public static void autoInput(){
        println("Auto Generate Cards");
        println("===================");
        println();

        String[] inputs = new String[4];
        String[] cards = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};  
        
        // randomizer
        SecureRandom rand = new SecureRandom();

        print("Your cards are : ");

        // random 4 cards
        for (int i = 0; i < 4; i++){
            inputs[i] = cards[rand.nextInt(13)];
            print(inputs[i] + ' ');
        }

        println();

        parseInput(inputs);
    }

    public static void parseInput(String[] inputs){
        int[] numbers = new int[4];

        // create dictionary
        Hashtable<String, Integer> dict = new Hashtable<String, Integer>();
        dict.put("A", 1);
        dict.put("a", 1);
        dict.put("2", 2);
        dict.put("3", 3);
        dict.put("4", 4);
        dict.put("5", 5);
        dict.put("6", 6);
        dict.put("7", 7);
        dict.put("8", 8);
        dict.put("9", 9);
        dict.put("10", 10);
        dict.put("J", 11);
        dict.put("j", 11);
        dict.put("Q", 12);
        dict.put("q", 12);
        dict.put("K", 13);
        dict.put("k", 13);

        // parse
        for (int i = 0; i < inputs.length; i++){
            numbers[i] = dict.get(inputs[i]);
        }

        // start timer
        startTime = System.currentTimeMillis();

        // call for solution
        solution.solution(numbers);

    }

    public static void output(Set<String> solutions){
        // end timer
        endTime = System.currentTimeMillis();

        println("\nOutput options : ");
        println("[1] Console");
        println("[2] Text File");
        println();

        int option;

        do { // input validation
            print("Enter option [1-2]: ");
            option = scan.nextInt();
            scan.nextLine();
        } while (option < 1 || option > 2);

        switch(option){
            case 1:
                outputConsole(solutions);
                break;
            case 2:
                try {
                    outputFile(solutions);
                } catch (IOException ex) {
                    println("Failed to create file. Please try again...");
                    output(solutions);
                }
                break;
        }
    }

    public static void outputConsole(Set<String> solutions){
        println((solutions.size() == 0) ? "There is no solution" : ("There are " + solutions.size() + " solutions\n"));
        int i = 1;

        // print all results
        for (String solution : solutions){
            print(i + ". ");
            println(solution);
            i++;
        }
    }

    public static void outputFile(Set<String> solutions) throws IOException{
        // user input filename
        print("Output file name [___.txt] : ");
        String filename = scan.nextLine();

        // initialize buffer
        String output = "";

        // header
        output += (solutions.size() == 0) ? "There is no solution" : ("There are " + solutions.size() + " solutions\n");

        // write contents
        int i = 1;
        for (String solution : solutions){
            output += (i + ". " + solution +'\n');
            i++;
        }

        // write to file
        FileWriter writer = new FileWriter("../test/" + filename);
        writer.write(output);
        writer.close();

        // success message
        print("Successfully added "+ filename +" to test folder.");
    }

}

class shortcut {
    
    public static void print(char item){
        System.out.print(item);
    }

    public static void print(int item){
        System.out.print(item);
    }
    
    public static void print(String item){
        System.out.print(item);
    }

    public static void println(){
        System.out.println();
    }

    public static void println(char item){
        System.out.println(item);
    }

    public static void println(int item){
        System.out.println(item);
    }

    public static void println(String item){
        System.out.println(item);
    }

    public static void printArr(int[] arr){
        Arrays.stream(arr).forEach(System.out::print);
        println();
    }
}
