package toy.problems.codility;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import toy.problems.codility.BulbExercise.Bulb;
import toy.problems.codility.BulbExercise.ShineObserver;
import toy.problems.codility.BulbExercise.StringOfLights;

public class BulbExerciseTest {

	@Test
	public void givenOrder1_3_2_5_4_ThenLight_returnsResult3() {
		int[] order = { 1, 3, 2, 5, 4 };
		BulbExercise mySolution = new BulbExercise();
		assertEquals(3, mySolution.solution(order));
	}
	@Test
	public void givenOrder5_4_3_2_1_ThenLight_returnsResult1() {
		int[] order = { 5, 4, 3, 2, 1 };
		BulbExercise mySolution = new BulbExercise();
		assertEquals(1, mySolution.solution(order));
	}

	@Test
	public void givenOrder2_1_3_5_4_ThenLight_returnsResult3() {
		int[] order = { 2, 1, 3, 5, 4 };
		BulbExercise mySolution = new BulbExercise();
		assertEquals(3, mySolution.solution(order));
	}

	@Test
	public void givenBulb_whenNew_resultOffAndDim() {
		// given
		Bulb bulb = new Bulb(0);
		assertFalse(bulb.isOn());
		assertFalse(bulb.isShining());
	}

	@Test
	public void givenPredecessorDim_whenTurnedOn_resultOnAndDim() {
		// given
		Bulb predecessor = new Bulb(0);
		Bulb testBulb = new Bulb(1, predecessor);

		// when
		testBulb.turnOn();

		// then
		assertTrue(testBulb.isOn());
		assertFalse(testBulb.isShining());
	}

	@Test
	public void givenPredecessorShining_whenTurnedOn_resultOnAndShining() {
		// given
		Bulb predecessor = new Bulb(0);
		Bulb testBulb = new Bulb(1, predecessor);

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

	// tests the alert handler on bulbs
	@Test
	public void givenOn101Shine100_whenMiddleTurnedOn_resultAllOnAndShining() {
		// given
		Bulb predecessor = new Bulb(1, true, true, null);
		Bulb testBulb = new Bulb(3, false, false, predecessor);
		Bulb successor = new Bulb(2, true, false, testBulb);

		// when
		testBulb.turnOn();

		// then
		assertTrue(testBulb.isOn());
		assertTrue(testBulb.isShining());
		assertTrue(successor.isShining());
	}
	class ShineCounter implements ShineObserver {
		public int alerts = 0;
		@Override
		public void shineAlertHandler() {
			this.alerts++;
		}
		public int getNumAlerts() {
			return this.alerts;
		}
	}
	@Test
	public void givenOn011Shine000_whenFirstTurnedOn_resultAllOnAndShining() {
		// given
		ShineCounter counter = new ShineCounter();
		Bulb predecessor = new Bulb(1, false, false, null);
		predecessor.registerShineObserver(counter);
		Bulb testBulb = new Bulb(3, true, false, predecessor);
		testBulb.registerShineObserver(counter);
		Bulb successor = new Bulb(2, true, false, testBulb);
		successor.registerShineObserver(counter);

		// when
		predecessor.turnOn();

		// then
		assertTrue(testBulb.isOn());
		assertTrue(testBulb.isShining());
		assertTrue(successor.isShining());
		assertEquals(3, counter.getNumAlerts());
	}
	@Test
	public void givenOrder2_1_3_5_4_whenStepThroughOn_testEachResult() {
		// given
		int[] order = { 2, 1, 3, 5, 4 };

		// when
		StringOfLights sol = new StringOfLights(order);
		sol.turnOnNext();
		System.out.printf("%s ", sol.printOnState());
		System.out.println(sol.printShiningState());
		
		// then
		assertEquals("On: [0 1 0 0 0]", sol.printOnState());
		assertEquals("Shining: [0 0 0 0 0]", sol.printShiningState());
		
		//when
		sol.turnOnNext();
		System.out.printf("%s ", sol.printOnState());
		System.out.println(sol.printShiningState());
		
		// then
		assertEquals("On: [1 1 0 0 0]", sol.printOnState());
		assertEquals("Shining: [1 1 0 0 0]", sol.printShiningState());
		assertEquals(1, sol.getCountAllShining());
		
		//when
		sol.turnOnNext();
		System.out.printf("%s ", sol.printOnState());
		System.out.println(sol.printShiningState());
		
		// then
		assertEquals("On: [1 1 1 0 0]", sol.printOnState());
		assertEquals("Shining: [1 1 1 0 0]", sol.printShiningState());
		assertEquals(2, sol.getCountAllShining());
		
		//when
		sol.turnOnNext();
		System.out.printf("%s ", sol.printOnState());
		System.out.println(sol.printShiningState());
		
		// then
		assertEquals("On: [1 1 1 0 1]", sol.printOnState());
		assertEquals("Shining: [1 1 1 0 0]", sol.printShiningState());
		
		//when
		sol.turnOnNext();
		System.out.printf("%s ", sol.printOnState());
		System.out.println(sol.printShiningState());
		
		// then
		assertEquals("On: [1 1 1 1 1]", sol.printOnState());
		assertEquals("Shining: [1 1 1 1 1]", sol.printShiningState());
		assertEquals(3, sol.getCountAllShining());
	}
}
