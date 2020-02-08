package toy.problems.hackerrank;

import java.util.Deque;
import java.util.LinkedList;
// https://www.hackerrank.com/challenges/java-string-reverse/problem
public class JavaStringReverse {

	public static void main(String[] args) {
        Deque<Character> stack = new LinkedList<>();
        char[] chars = args[0].toCharArray();
        for(char c : chars) {
            stack.push(c);
        }
        boolean isPalindrome = true;
        for(int i = 0; i < chars.length; i++) {
            if (chars[i] != stack.pop()) {
                isPalindrome = false;
                break;
            }
        }
		System.out.println(isPalindrome?"Yes":"No");
	}
}
