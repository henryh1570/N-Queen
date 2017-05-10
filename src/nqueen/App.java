package nqueen;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class App {

	public static final int N = 21;

	// N x N board of N-non attacking queens for n = 21
	public static void main(String[] args) {
//		Integer[] queenIndices = getRandomIndices();
//		Chessboard cb = new Chessboard(queenIndices);
		Chessboard cb = new Chessboard("AFGAAA");
		System.out.println("STATE OF BOARD N = " + N);
		System.out.println(cb.stateString());
		System.out.println(cb.toString());
//		cb.steepestHillClimbing(cb);
	}

	// Generate random board of N queens. 1 on each column
	public static Integer[] getRandomIndices() {
		HashSet<Integer> set = new HashSet<Integer>();
		int i = 0;
		while (set.size() < N) {
			int x = Math.abs(ThreadLocalRandom.current().nextInt() % N);
			// x is the row offset, i*N is the column
			if (set.add(i + (x*N))) {
				++i;
			}
		}
		return set.toArray(new Integer[N]);
	}
}
