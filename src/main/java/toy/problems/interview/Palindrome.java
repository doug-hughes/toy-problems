package toy.problems.interview;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 
 * Given a string determine if it is a palindrome
 *
 */
public class Palindrome {
	
	public static boolean isPalindrome(CharSequence testString) {
		Deque<Character> reversedCandidate = new LinkedList<>();
		for (int i = 0; i < testString.length(); i++) {
			reversedCandidate.push(testString.charAt(i));
		}
		boolean isPalindrome = true;
		for (int i = 0; i < testString.length(); i++) {
			if (testString.charAt(i) != Character.valueOf(reversedCandidate.pop())) {
				isPalindrome = false;
				break;
			}
		}
		return isPalindrome;
	}
	public static boolean isStreamPalindrome(CharSequence testString) {
		Deque<Integer> reversed = testString.chars()
				.boxed()
				.collect(LinkedList<Integer>::new, LinkedList<Integer>::push, (a,r) -> r.stream().forEach(a::push));
		boolean isPalindrome = testString.chars().allMatch(i -> i == reversed.pop().intValue());
		return isPalindrome;
	}
	public static void main(String[] args) {
		// using stack
		// start with String then try to replace with CharSequence
		String candidate = "codec";
		boolean isPalindrome = isStreamPalindrome(candidate);
		System.out.printf("%s palindrome? %b%n", candidate, isPalindrome);

		
//		boolean pal = false;
////		Arrays.stream(candidate.getBytes().);
//		Deque<Integer> reversedChars = candidate.chars().boxed().collect(Collectors.toCollection(new LinkedList<Integer>()));
//		System.out.printf("Palindrome: %b%n", pal);
		
	}
	
	
}
