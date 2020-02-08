package toy.problems.hackerrank;

import java.util.Arrays;
import java.util.Scanner;
//https://www.hackerrank.com/challenges/java-anagrams/problem
public class JavaAnagrams {



	    static boolean isAnagram(String a, String b) {
	        char[] aLower = a.toLowerCase().toCharArray();
	        Arrays.parallelSort(aLower);
	        char[] bLower = b.toLowerCase().toCharArray();
	        Arrays.parallelSort(bLower);
	        return Arrays.equals(aLower, bLower);
	        }

	  public static void main(String[] args) {
	    
	        Scanner scan = new Scanner(System.in);
	        String a = scan.next();
	        String b = scan.next();
	        scan.close();
	        boolean ret = isAnagram(a, b);
	        System.out.println( (ret) ? "Anagrams" : "Not Anagrams" );
	    }

}
