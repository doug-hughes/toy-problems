package toy.problems.codility;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppleTreeExercise {

	public int solution(int[] A, int k, int l) {
		if (k + l > A.length) {
			return -1;
		}
		Worker alice = new Worker(3);
		Worker bob = new Worker(2);
		List<Worker> workers = List.of(alice, bob);
		Orchard orchard = new Orchard(A, workers);

		Optional<WorkOrder> wo = alice.getWorkAssignments()
		.flatMap( a -> bob.getWorkAssignments().map(b -> new WorkOrder(a.getApples() + b.getApples(), a, b))
				.filter(WorkOrder::isDisjoint))
				.max(new Comparator<WorkOrder>() {

					@Override
					public int compare(WorkOrder o1, WorkOrder o2) {
						return o1.getApples() - o2.getApples();
					}
					
				});
		System.out.println(wo.get());
		return wo.map(WorkOrder::getApples).orElse(Integer.valueOf(0));
	}

	class Orchard {
		private List<Worker> workers;
		private int[] trees;

		Orchard(int[] trees, List<Worker> workers) {
			this.workers = workers;
			this.trees = trees;
			Arrays.stream(this.trees).forEach(i -> this.workers.forEach(w -> w.addTree(i)));
		}

	}
	class WorkOrder {
		@Override
		public String toString() {
			return "WorkOrder [apples=" + apples + ", wa1=" + wa1 + ", wa2=" + wa2 + "]";
		}
		private final int apples;
		private final WorkAssignment wa1;
		private final WorkAssignment wa2;
		
		WorkOrder(int apples, WorkAssignment wa1, WorkAssignment wa2){
			this.apples = apples;
			this.wa1 = wa1;
			this.wa2 = wa2;
		}
		public boolean isDisjoint() {
			return wa1.isDisjoint(wa2);
		}
		public int getApples() {
			return this.apples;
		}
	}
	class Worker {
		private final int capacity;
		private int offset = 0;
		private int numApples = 0;
		private final Queue<Integer> work;
		private final SortedMap<Integer, List<Integer>> sequentialTreeTotals = new TreeMap<>(Collections.reverseOrder());

		public Worker(int capacity) {
			this.capacity = capacity;
			this.work = new LinkedBlockingQueue<Integer>(capacity);
		}

		public void addTree(int numApples) {
			if (this.capacity > 0 && this.numApples >= 0) {
				this.numApples += numApples;
				if (offset >= capacity) {
					this.numApples -= this.work.remove();
				}
				this.work.add(numApples);
				offset++;
				if (offset >= capacity) {
					// create or addTo list
					sequentialTreeTotals.merge(this.numApples, List.of(this.offset),
							(l1, l2) -> Stream.of(l1, l2)
							.flatMap(lists -> lists
									.stream()).collect(Collectors.toList()));
				}
			}
		}
		public Stream<WorkAssignment> getWorkAssignments(){
			return sequentialTreeTotals.entrySet()
					.stream()
					.flatMap(entry -> entry.getValue().stream()
							.map(p -> new WorkAssignment(entry.getKey(), p - this.capacity, p - 1)));
		}
	}
	/** Assignment for worker **/
	class WorkAssignment implements Comparable<WorkAssignment>{
		private final int apples;
		private final int lowerBoundInclusive;
		private final int upperBoundInclusive;
		
		public WorkAssignment(int apples, int lowerBound, int upperBound) {
			this.apples = apples;
			this.lowerBoundInclusive = lowerBound;
			this.upperBoundInclusive = upperBound;
		}

		public int getApples() {
			return apples;
		}

		public int getLowerBoundInclusive() {
			return lowerBoundInclusive;
		}

		public int getUpperBoundExclusive() {
			return upperBoundInclusive;
		}

		@Override
		public String toString() {
			return "WorkAssignment [apples=" + apples + ", lowerBoundInclusive=" + lowerBoundInclusive
					+ ", upperBoundInclusive=" + upperBoundInclusive + "]";
		}

		private AppleTreeExercise getEnclosingInstance() {
			return AppleTreeExercise.this;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + apples;
			result = prime * result + lowerBoundInclusive;
			result = prime * result + upperBoundInclusive;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			WorkAssignment other = (WorkAssignment) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (apples != other.apples)
				return false;
			if (lowerBoundInclusive != other.lowerBoundInclusive)
				return false;
			if (upperBoundInclusive != other.upperBoundInclusive)
				return false;
			return true;
		}

		@Override
		public int compareTo(WorkAssignment o) {
			if (this.apples == o.apples) {
				if (this.lowerBoundInclusive == o.lowerBoundInclusive) {
					return this.upperBoundInclusive - o.upperBoundInclusive;
				} else {
					return this.lowerBoundInclusive - o.lowerBoundInclusive;
				}
			}
			return this.apples - o.apples;
		}
		public boolean isDisjoint(WorkAssignment o) {
			return !isIntersect(o);
		}
		public boolean isIntersect(WorkAssignment o) {
			return IntBetween.test(this.lowerBoundInclusive, o.lowerBoundInclusive, this.upperBoundInclusive)
					|| IntBetween.test(this.lowerBoundInclusive, o.upperBoundInclusive , this.upperBoundInclusive);
		}
	}
	interface IntBetween {
		static boolean test (int lower, int candidate, int upper) {
			if (lower <= candidate && candidate <= upper) {
				return true;
			} else {
				return false;
			}
		}
	}
	public static void main(String[] args) {
//		ThreadLocalRandom.current().ints(12L, 1, 500).forEach(i -> System.out.print(i + ","));
		int[] apples = {83,40,400,169,258,358,163,141,47,177,368,112};
//		int[] apples = { 6, 1, 4, 6, 3, 2, 7, 4 };
		int alice = 3;
		int bob = 2;
		AppleTreeExercise ae = new AppleTreeExercise();
		int maxApples = ae.solution(apples, alice, bob);
		System.out.println("Maximum number of apples that can be picked: " + maxApples);
	}

}
