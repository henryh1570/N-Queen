package nqueen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Chessboard {

	private int N;
	private boolean[][] board;

	public Chessboard(int size, int[] queenIndices) {
		N = size;
		board = new boolean[N][N];
		// Mark the queens on the board
		for (Integer index : queenIndices) {
			int x = getXFromIndex(index);
			int y = getYFromIndex(index);
			board[x][y] = true;
		}
	}

	public Chessboard(int size, Integer[] queenIndices) {
		N = size;
		board = new boolean[N][N];
		// Mark the queens on the board
		for (Integer index : queenIndices) {
			int x = getXFromIndex(index);
			int y = getYFromIndex(index);
			board[x][y] = true;
		}
	}

	public Chessboard(List<Integer> queenIndices) {
		// Mark the queens on the board
		for (Integer index : queenIndices) {
			int x = getXFromIndex(index);
			int y = getYFromIndex(index);
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
					str += "[*]";
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
	
	public int getNumberOfQueensAttacked() {
		//Track the attacking pairs
		HashSet<Tuple> pairs = new HashSet<Tuple>();

		// For all tiles on the board
		for (int i = 0; i < N; i++) {
			for (int k = 0; k < N; k++) {
				// If there is a queen, check its enemies
				if (board[i][k] == true) {
					int queen = (i * N) + k;
					ArrayList<Integer> enemies = getEnemies(queen);
					for (Integer otherQueen: enemies) {
						pairs.add(new Tuple (queen, otherQueen));
					}
				}
			}
		}
		for (Tuple t: pairs) {
			System.out.println(t.queen1 +"|" + t.queen2);
		}
		return pairs.size();
	}
	
	public void steepestHillClimbing() {
		//TODO:
	}
	
	public void geneticAlgorithm() {
		//TODO:
	}
	
	private class Tuple {
		int queen1;
		int queen2;
		
		public Tuple (int index1, int index2) {
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
