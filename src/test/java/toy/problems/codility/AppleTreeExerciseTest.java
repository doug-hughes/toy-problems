package com.uhg;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import com.uhg.AppleTreeExercise.IntBetween;
import com.uhg.AppleTreeExercise.WorkAssignment;
import com.uhg.AppleTreeExercise.Worker;

public class AppleTreeExerciseTest {

	@Test
	public void givenWorkOne_whenOneTree_thenReturnOneAssignment() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		Worker worker = ae.new Worker(1);
		// when
		worker.addTree(14);
		// then
		Stream<WorkAssignment> work = worker.getWorkAssignments();
		assertEquals(ae.new WorkAssignment(14, 0 , 0), work.findFirst().get());
	}

	@Test
	public void givenWorkOne_whenTwoTrees_thenReturnTwoAssignments() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		int workCapacity = 1;
		int[] apples = {14,24};
		List<WorkAssignment> expected = new ArrayList<>(apples.length);
		for (int i = 0; i < apples.length; i++) {
			expected.add(ae.new WorkAssignment(apples[i],i,i + workCapacity -1));
		}
		expected.sort(Collections.reverseOrder());
		Worker worker = ae.new Worker(workCapacity);
		// when
		for (int a : apples) {
			worker.addTree(a);
		}
		// then
		Stream<WorkAssignment> work = worker.getWorkAssignments();
		assertArrayEquals(expected.toArray(), work.toArray());
	}
	@Test
	public void givenWorkTwo_whenThreeTrees_thenReturnTwoAssignments() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		int workCapacity = 2;
		int[] apples = {14,24,4};
		List<WorkAssignment> expected = new ArrayList<>(2);
		expected.add(ae.new WorkAssignment(38,0,1));
		expected.add(ae.new WorkAssignment(28,1,2));
		// when
		Worker worker = ae.new Worker(workCapacity);
		for (int a : apples) {
			worker.addTree(a);
		}
		// then
		Stream<WorkAssignment> work = worker.getWorkAssignments();
		assertArrayEquals(expected.toArray(), work.toArray());
	}
	@Test
	public void givenWorkTwo_whenEightTrees_thenReturnSevenAssignments() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		int workCapacity = 2;
		int[] apples = { 6, 1, 4, 6, 3, 2, 7, 4 };
		List<WorkAssignment> expected = new ArrayList<>(7);
		expected.add(ae.new WorkAssignment(11,6,7));
		expected.add(ae.new WorkAssignment(10,2,3));
		expected.add(ae.new WorkAssignment(9,3,4));
		expected.add(ae.new WorkAssignment(9,5,6));
		expected.add(ae.new WorkAssignment(7,0,1));
		expected.add(ae.new WorkAssignment(5,1,2));
		expected.add(ae.new WorkAssignment(5,4,5));
		// when
		Worker worker = ae.new Worker(workCapacity);
		for (int a : apples) {
			worker.addTree(a);
		}
		// then
		Stream<WorkAssignment> work = worker.getWorkAssignments();
		assertArrayEquals(expected.toArray(), work.toArray());
	}
	@Test
	public void givenWorkThree_whenEightTrees_thenReturnSevenAssignments() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		int workCapacity = 3;
		int[] apples = { 6, 1, 4, 6, 3, 2, 7, 4 };
		List<WorkAssignment> expected = new ArrayList<>(6);
		expected.add(ae.new WorkAssignment(13,2,4));
		expected.add(ae.new WorkAssignment(13,5,7));
		expected.add(ae.new WorkAssignment(12,4,6));
		expected.add(ae.new WorkAssignment(11,0,2));
		expected.add(ae.new WorkAssignment(11,1,3));
		expected.add(ae.new WorkAssignment(11,3,5));
		// when
		Worker worker = ae.new Worker(workCapacity);
		for (int a : apples) {
			worker.addTree(a);
		}
		// then
		Stream<WorkAssignment> work = worker.getWorkAssignments();
		assertArrayEquals(expected.toArray(), work.toArray());
	}

	@Test
	public void givenSameAsUpper_whenIntBetweenTest_returnTrue() throws Exception {
		int[] given = {0,1,1};
		
		// when
		boolean isBetween = IntBetween.test(given[0], given[1], given[2]);
		
		//then
		assertTrue(isBetween);
	}
	@Test
	public void givenSameAsLower_whenIntBetweenTest_returnTrue() throws Exception {
		int[] given = {0,0,1};
		
		// when
		boolean isBetween = IntBetween.test(given[0], given[1], given[2]);
		
		//then
		assertTrue(isBetween);
	}
	@Test
	public void givenInBetween_whenIntBetweenTest_returnTrue() throws Exception {
		int[] given = {1,2,3};
		
		// when
		boolean isBetween = IntBetween.test(given[0], given[1], given[2]);
		
		//then
		assertTrue(isBetween);
	}
	@Test
	public void givenNotBetween_whenIntBetweenTest_returnFalse() throws Exception {
		int[] given = {1,7,3};
		
		// when
		boolean isBetween = IntBetween.test(given[0], given[1], given[2]);
		
		//then
		assertFalse(isBetween);
	}
	@Test
	public void givenSamePositions_whenIsIntersect_thenReturnTrue() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		List<WorkAssignment> expected = new ArrayList<>(2);
		expected.add(ae.new WorkAssignment(13,2,5));
		expected.add(ae.new WorkAssignment(13,2,5));
		// when
		boolean isIntersect = expected.get(0).isIntersect(expected.get(1));
		// then
		assertTrue(isIntersect);
	}
	@Test
	public void givenAdjacentPositions1_whenIsIntersect_thenReturnFalse() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		List<WorkAssignment> expected = new ArrayList<>(2);
		expected.add(ae.new WorkAssignment(13,2,4));
		expected.add(ae.new WorkAssignment(13,5,8));
		// when
		boolean isIntersect = expected.get(0).isIntersect(expected.get(1));
		// then
		assertFalse(isIntersect);
	}
	@Test
	public void givenAdjacentPositions2_whenIsIntersect_thenReturnFalse() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		List<WorkAssignment> expected = new ArrayList<>(2);
		expected.add(ae.new WorkAssignment(13,2,5));
		expected.add(ae.new WorkAssignment(13,0,1));
		// when
		boolean isIntersect = expected.get(0).isIntersect(expected.get(1));
		// then
		assertFalse(isIntersect);
	}
	@Test
	public void givenOverlap1_whenIsIntersect_thenReturnTrue() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		List<WorkAssignment> expected = new ArrayList<>(2);
		expected.add(ae.new WorkAssignment(13,2,12));
		expected.add(ae.new WorkAssignment(13,6,18));
		// when
		boolean isIntersect = expected.get(0).isIntersect(expected.get(1));
		// then
		assertTrue(isIntersect);
	}
	@Test
	public void givenOverlap2_whenIsIntersect_thenReturnTrue() throws Exception {
		// given
		AppleTreeExercise ae = new AppleTreeExercise();
		List<WorkAssignment> expected = new ArrayList<>(2);
		expected.add(ae.new WorkAssignment(13,6,18));
		expected.add(ae.new WorkAssignment(13,2,12));
		// when
		boolean isIntersect = expected.get(0).isIntersect(expected.get(1));
		// then
		assertTrue(isIntersect);
	}
}
