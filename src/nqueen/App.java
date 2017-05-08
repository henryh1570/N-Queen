package nqueen;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class App {

	public static final int N = 5;

	// N x N board of N-non attacking queens for n = 21
	public static void main(String[] args) {
		Integer[] queenIndices = getRandomIndices();
		Chessboard cb = new Chessboard(N, queenIndices);
		System.out.println("STATE OF BOARD N = " + N);
		System.out.println(cb.toString());
		// int q = 12;
		// System.out.println("ENEMIES OF " + q);
		// System.out.println(cb.getEnemiesString(q));
		System.out.println("Number of attacks: " + cb.getNumberOfQueensAttacked());
	}

	// Generate random board of N queens that can be placed within bounds (0, (N*N) -1)
	public static Integer[] getRandomIndices() {
		HashSet<Integer> set = new HashSet<Integer>();
		while (set.size() < N) {
			set.add(Math.abs(ThreadLocalRandom.current().nextInt() % ((N * N))));
		}
		return set.toArray(new Integer[N]);
	}
}
