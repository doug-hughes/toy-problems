package toy.problems.codility;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BulbExercise {
	 
		public int solution(int[] A) {
			StringOfLights sol = new StringOfLights(A);
			System.out.printf("%s ", sol.printOnState());
			System.out.println(sol.printShiningState());
			for (int i = 0; i < A.length; i++) {
				sol.turnOnNext();
				System.out.printf("%s ", sol.printOnState());
				System.out.println(sol.printShiningState());
			}
			System.out.println("AllOnCount: " + sol.getCountAllShining());
			return sol.getCountAllShining();
		}
		interface ShineObserver {
			void shineAlertHandler();
		}
		interface Shiner {
			void registerShineObserver(ShineObserver neighbor);
		}
		

	static class StringOfLights {
		private final int[] order;
		// number on indicates where we are on the order array
		private int numberBulbsOn = 0;
		// represents the next bulb to evaluate after on event
		private AtomicInteger numberBulbsShining = new AtomicInteger(0);
		// increment when all bulbs on are shining
		private AtomicInteger countAllShining = new AtomicInteger(0);
		private final Bulb[] lightString;

		StringOfLights(int[] order) {
			this.order = order;
			this.lightString = initializeLightString();
		}

		public int getNumberBulbsOn() {
			return this.numberBulbsOn;
		}

		public int getNumberBulbsShining() {
			return this.numberBulbsShining.intValue();
		}
		
		public int getCountAllShining() {
			return this.countAllShining.intValue();
		}
		// for testing
		Stream<Bulb> stream() {
			return Arrays.stream(this.lightString);
		}

		// create a chain of lights where each has reference to preceding neighbor
		// e.g. if the order is 1,3,2,5,4 then 1 <- 3 <- 2 <- 5 <- 4
		// the lightString will always be [1, 2, 3, 4, 5]
		// because we can always find the bulb based on its representative id number 
		// e.g. bulb with id=4 is located at lightString[4 - 1] 
		private Bulb[] initializeLightString() {
			Bulb[] bulbs = new Bulb[this.order.length];
			Bulb predecessor = null;
			for (int i = 0; i < order.length; i++) {
				int bulbId = i + 1;
//				int idx = bulbId - 1;
				bulbs[i] = new Bulb(bulbId, predecessor);
				bulbs[i].registerShineObserver(new ShineObserver() {
					@Override
					public void shineAlertHandler() {
						int currentNumShining = numberBulbsShining.incrementAndGet(); 
						if (currentNumShining == getNumberBulbsOn()) {
							countAllShining.incrementAndGet();
						}
					}
				});
				predecessor = bulbs[i];
			}
			return bulbs;
		}
		public void turnOnNext() {
			int idx = this.order[this.numberBulbsOn] - 1;
			this.numberBulbsOn++;
			this.lightString[idx].turnOn();
		}

		public String printShiningState() {
			return (Arrays.stream(this.lightString).
					map(l -> l.isShining() ? "1" : "0")
					.collect(Collectors.joining(" ", "Shining: [", "]" )));
		}
		public String printOnState() {
			return (Arrays.stream(this.lightString).
					map(l -> l.isOn() ? "1" : "0")
					.collect(Collectors.joining(" ", "On: [", "]" )));			
		}
		public String printPredecessors() {
			return (Arrays.stream(this.lightString).filter(l -> l.predecessor != null)
					.map(l -> String.valueOf(l.predecessor.getId()))
					.collect(Collectors.joining(" ", "Predecessors: [", "]" )));
		}
	}
	// Considered using state pattern ShiningBulb DimBulb, but the ShiningBulb has very few behaviors
	public static class Bulb implements Shiner, ShineObserver {
		private boolean shining = false; // set by evalShining()
		private int id;
		private boolean on = false;
		private Bulb predecessor;
		private Collection<ShineObserver> shineObserver = new HashSet<>(2);

		Bulb(int id) {
			this.id = id;
		}
		Bulb(int id, Bulb predecessor) {
			this.id = id;
			setPredecessorAndObserver(predecessor);
		}
		// only exposed for testing
		Bulb(int id, boolean on, boolean shine, Bulb predecessor) {
			this.id = id;
			this.shining = shine;
			this.on = on;
			setPredecessorAndObserver(predecessor);
		}
		private void setPredecessorAndObserver(Bulb predecessor) {
			this.predecessor = predecessor;
			if (predecessor != null) {
				predecessor.registerShineObserver(this);
			}
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
		
		void evalShining() {
			// don't check predecessor if I'm shining
			if (!isShining()) {
				setShining(isOn() && predecessorShines());
			}
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

		// once it shines it stays lit
		// alert observers when we change from dim to shine
		void setShining(boolean shining) {
			System.out.printf("%s setShining %s%n", getId(), shining);
			if (shining) {
				if (!this.shining) {
					this.shineObserver.stream().forEach(ShineObserver::shineAlertHandler);
					// alerting won't be used again so we can remove(to make sure)
					this.shineObserver = Collections.emptySet();
				}
				this.shining = shining;
			}
		}

		boolean isShining() {
			return this.shining;
		}

		@Override
		public String toString() {
			return "Bulb [isShining=" + shining + ", id=" + id + ", on=" + on + "]";
		}
		@Override
		public void shineAlertHandler() {
			// neighbor is shining, so if we are on we should now be shining 
			setShining(isOn());
		}
		@Override
		public void registerShineObserver(ShineObserver shineObserver) {
			this.shineObserver.add(shineObserver);
		}

	}
	public static void main(String[] args) {
		int[] myInt = { 1, 3, 2, 5, 4 };
		BulbExercise mySolution = new BulbExercise();
		mySolution.solution(myInt);
	}
}