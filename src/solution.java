import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import datatype.*;

public class solution extends shortcut {
    // global variable
    public static int indexPermute = 0;
    public static int[][] permutations = new int[24][];
    
    public static void solution(int[] nums) {
        // initialize set for solutions
        Set<StrDoub> solutions = new HashSet<>();

        // nums permutation
        permute(nums, 0);

        // loop through all possible combination
        for (int i = 0; i < permutations.length; i++){
            Double[] numTemp = new Double[4];
            int[] toProcess = permutations[i];

            // cast type to double
            for (int j=0; j < toProcess.length; j++){
                double temp = toProcess[j];
                numTemp[j] = temp;
            }

            // solve
            Set<StrDoub> ans = rec(numTemp);

            // append to solutions set
            solutions.addAll(ans);
        }

        // filter solutions to only if result equal to 24
        Set<StrDoub> filteredSet = solutions.stream()
                .filter(x -> x.res == 24)
                .collect(Collectors.toSet());

        // call for output processing
        main.output(filteredSet);
    }

    // permute through numbers
    public static void permute(int[] arr, int index) {
        if (index == arr.length) {
            // initialize temp array
            int[] tempArr = new int[arr.length];

            // copy to temp array
            System.arraycopy(arr, 0, tempArr, 0, arr.length);

            // assign array to permutations global variable
            permutations[indexPermute] = tempArr;

            // increment the index
            indexPermute++;

        } else {
            for (int i = index; i < arr.length; i++) {
                swap(arr, index, i);
                permute(arr, index + 1);
                swap(arr, index, i);
            }
        }
    }

    // swap items in array
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];    
        arr[j] = temp;
    }

    public static StrDoub operate(char operator, StrDoub a, StrDoub b, Boolean bracket){
        // variable declaration
        StrDoub result = new StrDoub(null, 0.0);

        // assign expr
        result.expr = (bracket ? '(' : "") + a.expr + operator + b.expr + (bracket ? ')' : "");

        // assign res
        switch(operator){
            case '-':
                result.res = a.res-b.res;
                break;
            case '*':
                result.res = a.res*b.res;
                break;
            case '/':
                result.res = a.res/b.res;
                break;
            case '+':
                result.res = a.res+b.res;
                break;
        }

        return result;
        
    }

    public static Set<StrDoub> rec(Double[] arr){
        // initialize variables
        Double[] head;
        Double[] tail;
        
        // initialize results set
        Set<StrDoub> results = new HashSet<>();

        // initialize operators
        char[] operators = {'+', '-', '*', '/'};

        if (arr.length > 1){
            for (int i = 1; i < arr.length; i++){
                // split array
                head = Arrays.copyOfRange(arr, 0, i);
                tail = Arrays.copyOfRange(arr, i, arr.length);

                // recursion
                Set<StrDoub> headRes = rec(head);
                Set<StrDoub> tailRes = rec(tail);

                // loop through operators, headRes, and tailRes
                for(char operator : operators){
                    for (StrDoub x : headRes){
                        for (StrDoub y : tailRes){
                            if (operator != '/' || (operator == '/' && y.res != 0)){
                                StrDoub ans = operate(operator, x, y, arr.length != 4);
                                results.add(ans);
                            } 
                        }
                    }                
                }
            }   

        } 
        else { // if arr.length == 0
            StrDoub res = new StrDoub(Integer.toString(arr[0].intValue()), arr[0]);
            results.add(res);
        }

        return results;

    }
}
