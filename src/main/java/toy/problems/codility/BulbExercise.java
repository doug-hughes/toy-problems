package toy.problems.codility;

import java.util.Arrays;
import java.util.List;

class Solution {
	public int solution(int[] A) {
		int allOnCount = 0;
		StringOfLights sol = new StringOfLights(A);
		sol.print();
		for (int bulbId : A) {
			sol.turnOnNext();
			sol.print();
			if (sol.getNumberBulbsOn() == sol.getNumberBulbsShining()) {
				allOnCount++;
			}
		}
		System.out.println("AllOnCount: " + allOnCount);
		return 0;
	}

	public static void main(String[] args) {
		int[] myInt = { 1, 3, 2, 5, 4 };
		Solution mySolution = new Solution();
		mySolution.solution(myInt);
		System.out.println("complete");
	}

}

class StringOfLights {
	private final int[] order;
	// number on indicates where we are on the order array
	private int numberBulbsOn = 0;
	// represents the next bulb to evaluate after on event
	private int numberBulbsShining = 0;
	private final List<Bulb> lightString;

	StringOfLights(int[] order) {
		this.order = order;
		lightString = initialize();
	}
	public int getNumberBulbsOn() {
		return numberBulbsOn;
	}

	public int getNumberBulbsShining() {
		return numberBulbsShining;
	}


	// create a chain of lights where each has reference to preceding neighbor
	private List<Bulb> initialize() {
		Bulb[] bulbs = new Bulb[this.order.length];
		for (int i = 0; i < bulbs.length; i++) {
				bulbs[i] = new Bulb(i + 1);
		}
		return Arrays.asList(bulbs);
	}

	public void turnOnNext() {
		int zeroBasedIndex = this.order[numberBulbsOn++] - 1;
		lightString.get(zeroBasedIndex).turnOn();
		while (numberBulbsShining <= zeroBasedIndex) {
			if (lightString.get(zeroBasedIndex).isOn()) {
				lightString.get(zeroBasedIndex).setShining(true);
				numberBulbsShining++;
			} else {
				break;
			}
		}
	}

	public void print() {
		this.lightString.stream().forEachOrdered(System.out::println);
	}
}

class Bulb {
	private boolean shining = false; // set by OnHandler
	private int id;
	private boolean on = false;

	Bulb(int id) {
		this.id = id;
	}


	void turnOn() {
		System.out.printf("%s turnedOn%n", getId());
		this.on = true;
	}

	int getId() {
		return this.id;
	}

	boolean isOn() {
		return this.on;
	}

	void setShining(boolean shining) {
		System.out.printf("%s setShining %s%n", getId(), shining);
		this.shining = shining;
	}

	boolean isShining() {
		return this.shining;
	}


	@Override
	public String toString() {
		return "Bulb [isShining=" + shining + ", id=" + id + ", on=" + on + "]";
	}

}