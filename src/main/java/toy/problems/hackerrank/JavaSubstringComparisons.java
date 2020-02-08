package toy.problems.hackerrank;

import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
// https://www.hackerrank.com/challenges/java-string-compare/problem
public class JavaSubstringComparisons {
	private final SortedSet<CharSequence> substrings;
	
	JavaSubstringComparisons(String given, int length) {
		this.substrings = buildSubstrings(given, length); 
	}
	static SortedSet<CharSequence> buildSubstrings(String original, int length) {
		SortedSet<CharSequence> subs = new TreeSet<>();
		StringBuilder sb = new StringBuilder(original);
		for(int i = 0; i <= original.length() - length; i++) {
			subs.add(sb.subSequence(i, i + length));
		}
		return subs;
	}
	private CharSequence getSmallest() {
		return this.substrings.first();
	}
	private CharSequence getLargest() {
		return this.substrings.last();
	}
	
	public static void main(String[] args) {
		String providedText = "";
		int subSize = 0;
		try (Scanner scanner = new Scanner(System.in)) {
			providedText = scanner.next();
			subSize = scanner.nextInt();
		}
		JavaSubstringComparisons subs = new JavaSubstringComparisons(providedText, subSize);
		System.out.println(subs.getSmallest());
		System.out.println(subs.getLargest());
	}
}
