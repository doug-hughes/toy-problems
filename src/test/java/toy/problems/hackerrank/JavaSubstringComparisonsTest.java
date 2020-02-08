package toy.problems.hackerrank;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

public class JavaSubstringComparisonsTest {

	@Test
	public void testBuildSubstrings() throws Exception {
		//given
		String inputString = "somethingaboutjava";
		int subSize = 2;
		Collection<CharSequence> expected = new ArrayList<>(17);
		expected.add("ab");
		expected.add("av");
		expected.add("bo");
		expected.add("et");
		expected.add("ga");
		expected.add("hi");
		expected.add("in");
		expected.add("ja");
		expected.add("me");
		expected.add("ng");
		expected.add("om");
		expected.add("ou");
		expected.add("so");
		expected.add("th");
		expected.add("tj");
		expected.add("ut");
		expected.add("va");
		
		//when
		Collection<CharSequence> actual = JavaSubstringComparisons.buildSubstrings(inputString, subSize); 
		
		//then
		assertIterableEquals(expected, actual);
	}

}
