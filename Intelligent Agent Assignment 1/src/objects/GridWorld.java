package objects;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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

	public void displayGridWorld() {
		for (int row = 0; row < getNumOfRow(); row++) {
			for (int col = 0; col < getNumOfCol(); col++) {
				if (grids[col][row].isWall())
					System.out.print("wwww ");
				else
					System.out.print(grids[col][row].getReward() + " ");
			}
			System.out.println();
		}
	}

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

	public double moveUpUtility(int col, int row) {
		double u = 0.8 * moveUp(col, row) + 0.1 * moveLeft(col, row) + 0.1 * moveRight(col, row);
		return u;
	}

	public double moveDownUtility(int col, int row) {
		double u = 0.8 * moveDown(col, row) + 0.1 * moveLeft(col, row) + 0.1 * moveRight(col, row);
		return u;
	}

	public double moveRightUtility(int col, int row) {
		double u = 0.8 * moveRight(col, row) + 0.1 * moveUp(col, row) + 0.1 * moveDown(col, row);
		return u;
	}

	public double moveLeftUtility(int col, int row) {
		double u = 0.8 * moveLeft(col, row) + 0.1 * moveUp(col, row) + 0.1 * moveDown(col, row);
		return u;
	}

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
