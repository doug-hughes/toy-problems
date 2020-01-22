package toy.problems.codility;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BulbExercise {
	 
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
			return allOnCount;
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
			System.out.print(this.lightString.stream().
					map(l -> l.isShining() ? "1" : "0")
					.collect(Collectors.joining(" ", "Shining: [", "] " )));
			System.out.println(this.lightString.stream().
					map(l -> l.isOn() ? "1" : "0")
					.collect(Collectors.joining(" ", " On: [", "] " )));
		}
	}
	interface ShineObserver {
		void shineAlertHandler();
		void setShineObserver(ShineObserver neighbor);
	}
	
	// Consider using state pattern ShiningBulb DimBulb
	public abstract class Bulb {
		boolean shining = false; // set by evalShining()
		private int id;
		private boolean on = false;
		private Bulb predecessor;

		// only exposed for testing
		Bulb(int id, boolean on, boolean shine, Bulb predecessor) {
			this.id = id;
			this.shining = shine;
			this.on = on;
			this.predecessor = predecessor;
		}

		void turnOn() {
			System.out.printf("%s turnedOn%n", getId());
			this.on = true;
			evalShining();
		}

		int getId() {
			return this.id;
		}

		boolean isOn() {
			return this.on;
		}
		
		private boolean predecessorShines() {
			if (this.predecessor != null) {
				this.predecessor.evalShining();
				return this.predecessor.isShining();
			} else {
				// when no predecessor, shine when on
				return true;
			}
		}

		boolean isShining() {
			return this.shining;
		}

		@Override
		public String toString() {
			return "Bulb [isShining=" + shining + ", id=" + id + ", on=" + on + "]";
		}
	}
	
	public class DimBulb extends Bulb implements ShineObserver {
		private ShineObserver shineObserver;

		DimBulb(int id) {
			super(id, false, false, null);
		}
		DimBulb(int id, Bulb predecessor) {
			super(id, false, false, predecessor);
			if (predecessor != null && predecessor instanceof ShineObserver) {
				((ShineObserver)predecessor).setShineObserver(this);
			}
		}
		
		// once it shines it stays lit
		// alert observers when we change from dim to shine
		void setShining(boolean shining) {
			System.out.printf("%s setShining %s%n", getId(), shining);
			if (shining) {
				if (!this.shining && this.shineObserver != null) {
					this.shineObserver.shineAlertHandler();
				}
				this.shining = shining;
			}
		}
		void evalShining() {
			// don't check predecessor if I'm shining
			if (!isShining()) {
				setShining(isOn() && predecessorShines());
			}
		}

		@Override
		public void setShineObserver(ShineObserver neighbor) {
			this.shineObserver = neighbor;
		}
		@Override
		public void shineAlertHandler() {
			// neighbor is shining, so if we are on we should now be shining 
			setShining(isOn());
		}
	}
	public class ShiningBulb extends Bulb {
		ShiningBulb(int id) {
			super(id, true, true, null);
		}
		
	}
	public static void main(String[] args) {
		int[] myInt = { 1, 3, 2, 5, 4 };
		BulbExercise mySolution = new BulbExercise();
		mySolution.solution(myInt);
		System.out.println("complete");
	}
}