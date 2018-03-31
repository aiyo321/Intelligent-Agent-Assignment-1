package objects;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author Too Jian Teng (U1621194C)
 */
public class GridWorld {

	private int numOfRow;
	private int numOfCol;
	State[][] gridsState = null;
	Grid[][] grids = null;

	public GridWorld(int numOfRow, int numOfCol) {
		this.setNumOfRow(numOfRow);
		this.setNumOfCol(numOfCol);
		grids = new Grid[numOfRow][numOfCol];
		gridsState = new State[numOfRow][numOfCol];
	}

	/**
	 * setting the maze environment as in assignment guide
	 */
	public void generateGridWorld() {
		for (int i = 0; i < getNumOfRow(); i++) {
			for (int j = 0; j < getNumOfCol(); j++) {
				grids[j][i] = new Grid();
				grids[j][i].setReward(-0.04);
				grids[j][i].setWall(false);
				gridsState[j][i] = new State();
				gridsState[j][i].setUtility(0);
			}
		}

		// setting green grid
		grids[0][0].setReward(1);
		grids[2][0].setReward(1);
		grids[5][0].setReward(1);
		grids[3][1].setReward(1);
		grids[4][2].setReward(1);
		grids[5][3].setReward(1);

		// setting brown grid
		grids[1][1].setReward(-1);
		grids[5][1].setReward(-1);
		grids[2][2].setReward(-1);
		grids[3][3].setReward(-1);
		grids[4][4].setReward(-1);

		// 1 0,4 1,1 4,2 4,3 4
		// setting walls
		grids[1][0].setWall(true);
		grids[4][1].setWall(true);
		grids[1][4].setWall(true);
		grids[2][4].setWall(true);
		grids[3][4].setWall(true);
	}

	/**
	 * for part 2 of the assignment increased number of grids various different
	 * rewards
	 */
	public void generateComplexGridWorld() {
		for (int i = 0; i < getNumOfRow(); i++) {
			for (int j = 0; j < getNumOfCol(); j++) {
				grids[j][i] = new Grid();
				grids[j][i].setReward(-0.04);
				grids[j][i].setWall(false);
				gridsState[j][i] = new State();
				gridsState[j][i].setUtility(0);
			}
		}

		// setting +1 grid
		grids[1][1].setReward(1);
		grids[7][3].setReward(1);

		// setting +2 grid
		grids[3][2].setReward(2);
		grids[4][7].setReward(2);

		// setting +3 grid
		grids[2][4].setReward(3);
		grids[7][7].setReward(3);

		// setting +4 grid
		grids[4][0].setReward(4);

		// setting -1 grid
		grids[5][3].setReward(-1);
		grids[3][5].setReward(-1);

		// setting -2 grid
		grids[2][3].setReward(-2);
		grids[5][6].setReward(-2);

		// setting -3 grid
		grids[6][1].setReward(-3);
		grids[0][8].setReward(-3);

		// setting -4 grid
		grids[4][4].setReward(-4);

		// setting walls
		grids[1][0].setWall(true);
		grids[7][1].setWall(true);
		grids[5][2].setWall(true);
		grids[6][2].setWall(true);
		grids[8][4].setWall(true);
		grids[1][6].setWall(true);
		grids[2][6].setWall(true);
		grids[3][6].setWall(true);
		grids[4][6].setWall(true);
		grids[6][8].setWall(true);

	}

	/**
	 * to print out the utility of all the states
	 */
	public void displayState() {
		DecimalFormat df = new DecimalFormat("#.######");
		df.setRoundingMode(RoundingMode.CEILING);
		for (int row = 0; row < getNumOfRow(); row++) {
			for (int col = 0; col < getNumOfCol(); col++) {
				if (gridsState[col][row].getUtility() == 0) {
					System.out.print("0.000000 | ");
				} else {
					System.out.print(df.format(gridsState[col][row].getUtility()) + " | ");
				}
			}
			System.out.println();
		}
	}

	public void displayStateLineByLine() {
		for (int row = 0; row < getNumOfRow(); row++) {
			for (int col = 0; col < getNumOfCol(); col++) {
				if (gridsState[col][row].getUtility() == 0) {
					System.out.println("(" + col + "," + row + "): " + "0");
				} else {
					System.out.println("(" + col + "," + row + "): " + gridsState[col][row].getUtility());
				}
			}
		}
	}
	public void displayStateLineByLine2() {
		for (int row = 0; row < getNumOfRow(); row++) {
			for (int col = 0; col < getNumOfCol(); col++) {
				if (gridsState[col][row].getUtility() == 0) {
					System.out.println("0");
				} else {
					System.out.println(gridsState[col][row].getUtility());
				}
			}
		}
	}

	/**
	 * to print out the movement of all the states
	 */
	public void displayStateMovement() {
		for (int row = 0; row < getNumOfRow(); row++) {
			for (int col = 0; col < getNumOfCol(); col++) {
				if (gridsState[col][row].getBestMovement() == null) {
					System.out.print("wall  | ");
				} else {
					System.out.print(gridsState[col][row].getBestMovement() + " | ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * print out how the maze looks like
	 */
	public void displayGridWorld() {
		for (int row = 0; row < getNumOfRow(); row++) {
			for (int col = 0; col < getNumOfCol(); col++) {
				if (grids[col][row].isWall())
					System.out.print("wwww | ");
				else
					System.out.print(grids[col][row].getReward() + " | ");
			}
			System.out.println();
		}
	}

	/**
	 * @param col
	 * @param row
	 * @param bm
	 * @return utility of the state if given the best move
	 */
	public double chooseBestMovement(int col, int row, String bm) {
		double u = 0;
		switch (bm) {
		case "up   ":
			u = moveUpUtility(col, row);
			break;
		case "down ":
			u = moveDownUtility(col, row);
			break;
		case "left ":
			u = moveLeftUtility(col, row);
			break;
		case "right":
			u = moveRightUtility(col, row);
			break;
		}
		return u;
	}

	/**
	 * @param col
	 * @param row
	 * @return best movement the current state and grid can make
	 */
	public String calculateBestMovementUtility(int col, int row) {
		String directionUp = "up   ";
		String directionDown = "down ";
		String directionLeft = "left ";
		String directionRight = "right";
		double up = moveUpUtility(col, row);
		double down = moveDownUtility(col, row);
		double right = moveRightUtility(col, row);
		double left = moveLeftUtility(col, row);

		String daBest = directionUp;
		double best = up;
		if (down > best) {
			daBest = directionDown;
			best = down;
		}
		if (right > best) {
			daBest = directionRight;
			best = right;
		}
		if (left > best) {
			daBest = directionLeft;
			best = left;
		}

		return daBest;
	}

	// return utility of moving up with probability of moving sideways (0.8,0.1,0.1)
	public double moveUpUtility(int col, int row) {
		double u = 0.8 * moveUp(col, row) + 0.1 * moveLeft(col, row) + 0.1 * moveRight(col, row);
		return u;
	}

	// return utility of moving down with probability of moving sideways
	// (0.8,0.1,0.1)
	public double moveDownUtility(int col, int row) {
		double u = 0.8 * moveDown(col, row) + 0.1 * moveLeft(col, row) + 0.1 * moveRight(col, row);
		return u;
	}

	// return utility of moving right with probability of moving sideways
	// (0.8,0.1,0.1)
	public double moveRightUtility(int col, int row) {
		double u = 0.8 * moveRight(col, row) + 0.1 * moveUp(col, row) + 0.1 * moveDown(col, row);
		return u;
	}

	// return utility of moving left with probability of moving sideways
	// (0.8,0.1,0.1)
	public double moveLeftUtility(int col, int row) {
		double u = 0.8 * moveLeft(col, row) + 0.1 * moveUp(col, row) + 0.1 * moveDown(col, row);
		return u;
	}

	// return utility of moving up
	public double moveUp(int col, int row) {
		double u = 0;
		int r = row - 1;
		State[][] s = this.getGridsState();
		if (r < 0) {
			// out of bound
			u = s[col][row].getUtility();
		} else if (this.grids[col][r].isWall) {
			// wall
			u = s[col][row].getUtility();
		} else {
			u = s[col][r].getUtility();
		}
		return u;
	}

	// return utility of moving down
	public double moveDown(int col, int row) {
		double u = 0;
		int r = row + 1;
		State[][] s = this.getGridsState();
		if (r >= this.getNumOfRow()) {
			// out of bound
			u = s[col][row].getUtility();
		} else if (this.grids[col][r].isWall) {
			// wall
			u = s[col][row].getUtility();
		} else {
			u = s[col][r].getUtility();
		}
		return u;
	}

	// return utility of moving right
	public double moveRight(int col, int row) {
		double u = 0;
		int c = col + 1;
		State[][] s = this.getGridsState();
		if (c >= this.getNumOfCol()) {
			// out of bound
			u = s[col][row].getUtility();
		} else if (this.grids[c][row].isWall) {
			// wall
			u = s[col][row].getUtility();
		} else {
			u = s[c][row].getUtility();
		}
		return u;
	}

	// return utility of moving left
	public double moveLeft(int col, int row) {
		double u = 0;
		int c = col - 1;
		State[][] s = this.getGridsState();
		if (c < 0) {
			// out of bound
			u = s[col][row].getUtility();
		} else if (this.grids[c][row].isWall) {
			// wall
			u = s[col][row].getUtility();
		} else {
			u = s[c][row].getUtility();
		}
		return u;
	}

	// getters and setters

	public State[][] getGridsState() {
		return gridsState;
	}

	public void setGridsState(State[][] gridsState) {
		this.gridsState = gridsState;
	}

	public Grid[][] getGrids() {
		return grids;
	}

	public void setGrids(Grid[][] grids) {
		this.grids = grids;
	}

	public int getNumOfRow() {
		return numOfRow;
	}

	public void setNumOfRow(int numOfRow) {
		this.numOfRow = numOfRow;
	}

	public int getNumOfCol() {
		return numOfCol;
	}

	public void setNumOfCol(int numOfCol) {
		this.numOfCol = numOfCol;
	}

}
