package toy.problems.hackerrank;
// https://www.hackerrank.com/challenges/java-string-tokens/problem
public class JavaStringTokens {

	public static void main(String[] args) {
		String s = "He is a very very good boy, isn't he?";
		String[] tokens = s.split("[ !,?._'@]+");
		System.out.println(tokens.length);
		for(String t: tokens) {
			System.out.println(t);
		}

	}

}
