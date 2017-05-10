package nqueen;

import java.util.ArrayList;
import java.util.HashSet;

public class Chessboard {

	private int N;
	private boolean[][] board;

	// Init chessboard with indices
	public Chessboard(int[] queenIndices) {
		N = queenIndices.length;
		board = new boolean[N][N];
		// Mark the queens on the board
		for (Integer index : queenIndices) {
			int x = getXFromIndex(index);
			int y = getYFromIndex(index);
			board[x][y] = true;
		}
	}

	// Init chessboard via copying a 2d array
	public Chessboard(boolean[][] queenIndices) {
		N = queenIndices.length;
		board = new boolean[N][N];
		// Mark the queens on the board
		for (int i = 0; i < N; i++) {
			for (int k = 0; k < N; k++) {
				if (queenIndices[i][k] == true) {
					board[i][k] = true;
				}
			}
		}
	}

	public Chessboard(Integer[] queenIndices) {
		N = queenIndices.length;
		board = new boolean[N][N];
		// Mark the queens on the board
		for (Integer index : queenIndices) {
			int x = getXFromIndex(index);
			int y = getYFromIndex(index);
			board[x][y] = true;
		}
	}

	public Chessboard(ArrayList<Integer> queenIndices) {
		// Mark the queens on the board
		N = queenIndices.size();
		board = new boolean[N][N];
		for (Integer index : queenIndices) {
			int x = getXFromIndex(index);
			int y = getYFromIndex(index);
			board[x][y] = true;
		}
	}
	
	public Chessboard(String stateString) {
		// Mark the queens on the board
		char[] arr = stateString.toCharArray();
		N = arr.length;
		board = new boolean[N][N];
		for (int y = 0; y < arr.length; y++) {
			char value = arr[y];
			int x;
			// If string contains A or greater
			// Then N is >= 10, use conversion factor
			if (value >= 'A') {
				x = N - (value + 10 - 'A');
			} else{
				// Otherwise convert to a normal number < 9
				x = N - Integer.parseInt(""+value);
			}
			board[x][y] = true;
		}
	}


	public int getXFromIndex(int index) {
		try {
			return index / N;
		} catch (Exception e) {
			return 0;
		}
	}

	public int getYFromIndex(int index) {
		return index % N;
	}

	// State of the board
	public String toString() {
		String str = "";
		for (int i = 0; i < N; i++) {
			for (int k = 0; k < N; k++) {
				if (board[i][k] == true) {
					str += "[O]";
				} else {
					str += "[ ]";
				}
			}
			str += "\n";
		}
		return str;
	}

	// Get Queens on the same row as index
	public ArrayList<Integer> getQueensRow(int index) {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		int x = getXFromIndex(index);
		for (int i = 0; i < N; i++) {
			int next = ((x * N) + i);
			if (board[x][i] == true && next != index) {
				indices.add(next);
			}
		}
		return indices;
	}

	// Get Queens on the same col as index
	public ArrayList<Integer> getQueensCol(int index) {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		int y = getYFromIndex(index);
		for (int i = 0; i < N; i++) {
			int next = (y + (i * N));
			if (board[i][y] == true && next != index) {
				indices.add(next);
			}
		}
		return indices;
	}

	// Get Queens on the same diagonal down as index
	public ArrayList<Integer> getQueensDiagDown(int index) {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		// Upper Left
		int x = getXFromIndex(index) - 1;
		int y = getYFromIndex(index) - 1;

		while (x >= 0 && y >= 0) {
			int next = (x * N) + y;
			if (board[x][y] == true) {
				indices.add(next);
			}
			--x;
			--y;
		}

		// Bottom Right
		x = getXFromIndex(index) + 1;
		y = getYFromIndex(index) + 1;
		while (x < N && y < N) {
			int next = (x * N) + y;
			if (board[x][y] == true) {
				indices.add(next);
			}
			++x;
			++y;

		}
		return indices;
	}

	// Get Queens on the same diagonal up as index
	public ArrayList<Integer> getQueensDiagUp(int index) {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		// Bottom Left
		int x = getXFromIndex(index) - 1;
		int y = getYFromIndex(index) + 1;
		while (x >= 0 && y < N) {
			int next = (x * N) + y;
			if (board[x][y] == true) {
				indices.add(next);
			}
			--x;
			++y;
		}

		// Upper Right
		x = getXFromIndex(index) + 1;
		y = getYFromIndex(index) - 1;
		while (x < N && y >= 0) {
			int next = (x * N) + y;
			if (board[x][y] == true) {
				indices.add(next);
			}
			++x;
			--y;
		}
		return indices;
	}

	// List of all enemies in sight of a queen at index
	public ArrayList<Integer> getEnemies(int index) {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		indices.addAll(getQueensCol(index));
		indices.addAll(getQueensRow(index));
		indices.addAll(getQueensDiagDown(index));
		indices.addAll(getQueensDiagUp(index));
		return indices;
	}

	public String getEnemiesString(int index) {
		ArrayList<Integer> indices = getEnemies(index);
		String str = "";
		for (int i = 0; i < N; i++) {
			for (int k = 0; k < N; k++) {
				if (board[i][k] == true && indices.contains((i * N) + k)) {
					str += "[*]";
				} else {
					str += "[ ]";
				}
			}
			str += "\n";
		}
		return str;
	}

	// Retrieve number of queens one specific queen is attacking
	public int getMostAggressiveQueen() {
		int attacks = -1;
		int index = -1;

		// For all tiles on the board
		for (int i = 0; i < N; i++) {
			for (int k = 0; k < N; k++) {
				// If there is a queen, check its enemies
				if (board[i][k] == true) {
					int next = (i * N) + k;
					int targets = getEnemies(next).size();

					if (targets > attacks) {
						index = next;
						attacks = targets;
					}
				}
			}
		}
		return index;
	}

	public int getNumberOfPairsAttacked() {
		// Track the attacking pairs
		HashSet<Tuple> pairs = new HashSet<Tuple>();

		// For all tiles on the board
		for (int i = 0; i < N; i++) {
			for (int k = 0; k < N; k++) {
				// If there is a queen, check its enemies
				if (board[i][k] == true) {
					int queen = (i * N) + k;
					ArrayList<Integer> enemies = getEnemies(queen);
					for (Integer otherQueen : enemies) {
						pairs.add(new Tuple(queen, otherQueen));
					}
				}
			}
		}
		return pairs.size();
	}

	// Start with initial state of all queens randomly placed in 1 of each
	// column
	// Do n-1 iterations, do not allow sideways movements
	public void steepestHillClimbing(Chessboard cb) {
		System.out.println("STEEPEST HILL CLIMB");
		// Check entire board for its queens
		for (int i = 0; i < N; i++) {
			for (int k = 0; k < N; k++) {
				// After finding a queen, get its successor states
				if (board[i][k] == true) {
					ArrayList<Chessboard> list = getSuccessors(new Chessboard(cb.board), k);
					int bestValue = Integer.MAX_VALUE;
					Chessboard best = null;

					// Pick out the column's successor and remember the current
					// best for later
					for (Chessboard next : list) {
						int queensAttacked = next.getNumberOfPairsAttacked();
						if (queensAttacked < bestValue) {
							bestValue = queensAttacked;
							best = next;
						}
					}

					// Replace the current Chessboard's column
					for (int a = 0; a < N; a++) {
						board[a][k] = best.board[a][k];
					}

					System.out.println(toString());
					System.out.println("# of Queens Attacked: " + getNumberOfPairsAttacked());
					k = N;
				}
			}
		}
		System.out.println(toString());
		System.out.println("# of Queens Attacked: " + getNumberOfPairsAttacked());
	}

	// Given coordinates of a queen on a chessboard, get the new successors
	public ArrayList<Chessboard> getSuccessors(Chessboard cb, int y) {
		ArrayList<Chessboard> successors = new ArrayList<Chessboard>();
		boolean[][] arr = new boolean[cb.N][cb.N];

		// Copy over the input Chessboard's state. Erase the selected column.
		for (int i = 0; i < cb.N; i++) {
			for (int k = 0; k < cb.N; k++) {
				if (cb.board[i][k] == true && y != k) {
					arr[i][k] = true;
				}
			}
		}

		// Add all the boards with 1 queen moved
		for (int i = 0; i < cb.N; i++) {
			arr[i][y] = true;
			successors.add(new Chessboard(arr));
			arr[i][y] = false;
		}

		return successors;
	}

	// Begin with an initial population of unique boards
	public void geneticAlgorithm(HashSet<Chessboard> set) {
		// TODO:
		
	}
	
	// Turn queen positions of the board to a state string
	public String stateString() {
		String str = "";
		for (int i = 0; i < N; i++) {
			for (int k = 0; k < N; k++) {
				if (board[k][i] == true) {
					int value = (N-k);
					// For arrays of possible size > 10, use characters
					if (value >= 10) {
						str += (char)('A' + value - 10);
					} else {
						str += value;
					}
				}
			}
		}
		return str;
	}

	private class Tuple {
		int queen1;
		int queen2;

		public Tuple(int index1, int index2) {
			queen1 = index1;
			queen2 = index2;
			// Order the tuple from small to big
			if (queen1 > queen2) {
				int temp = queen1;
				queen1 = queen2;
				queen2 = temp;
			}
		}

		// Include tuple values into hash
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + queen1;
			result = prime * result + queen2;
			return result;
		}

		// If another tuple contains both numbers
		public boolean equals(Object secondQueen) {
			int i = 2;
			Tuple other = (Tuple) secondQueen;
			if (other.queen1 == queen1 || other.queen1 == queen2) {
				i--;
			}
			if (other.queen2 == queen1 || other.queen2 == queen2) {
				i--;
			}
			if (i == 0) {
				return true;
			} else {
				return false;
			}
		}
	}
}
