package toy.problems.codility;

import static org.junit.Assert.*;

import org.junit.Test;

import toy.problems.codility.BulbExercise.Bulb;

public class BulbExerciseTest {

	@Test
	public void givenOrder1_3_2_5_4_ThenLight_returnsResult3() {
		int[] order = { 1, 3, 2, 5, 4 };
		BulbExercise mySolution = new BulbExercise();
		assertEquals(3, mySolution.solution(order));
	}

	@Test
	public void givenBulb_whenNew_resultOffAndDim() {
		// given
		BulbExercise mySolution = new BulbExercise();
		Bulb bulb = mySolution.new Bulb(0);
		assertFalse(bulb.isOn());
		assertFalse(bulb.isShining());
	}

	@Test
	public void givenPredecessorDim_whenTurnedOn_resultOnAndDim() {
		// given
		BulbExercise mySolution = new BulbExercise();
		Bulb predecessor = mySolution.new Bulb(0);
		Bulb testBulb = mySolution.new Bulb(1, predecessor);

		// when
		testBulb.turnOn();

		// then
		assertTrue(testBulb.isOn());
		assertFalse(testBulb.isShining());
	}

	@Test
	public void givenPredecessorShining_whenTurnedOn_resultOnAndShining() {
		// given
		BulbExercise mySolution = new BulbExercise();
		Bulb predecessor = mySolution.new Bulb(0);
		Bulb testBulb = mySolution.new Bulb(1, predecessor);

		// when
		testBulb.turnOn();

		// then
		assertTrue(testBulb.isOn());
		assertFalse(testBulb.isShining());
	}
	// In these tests the method name indicates the state of the bulb
	// e.g. On101 will indicate the first bulb is on, second off, third on
	// e.g. Shine100 will indicate the first bulb shines and the following two are
	// off

	// tests the alert handler
	@Test
	public void givenOn101Shine100_whenMiddleTurnedOn_resultAllOnAndShining() {
		// given
		BulbExercise mySolution = new BulbExercise();
		Bulb predecessor = mySolution.new Bulb(1, true, true, null);
		Bulb testBulb = mySolution.new Bulb(3, false, false, predecessor);
		Bulb successor = mySolution.new Bulb(2, true, false, testBulb);

		// when
		testBulb.turnOn();

		// then
		assertTrue(testBulb.isOn());
		assertTrue(testBulb.isShining());
		assertTrue(successor.isShining());
	}
}
