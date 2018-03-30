package main;

import objects.GridWorld;
import objects.State;

public class valueiteration {

	public static void main(String[] args) {
		GridWorld gridW = new GridWorld(6, 6);
		gridW.generateGridWorld();
		gridW.displayGridWorld();
		System.out.println();

		double u = 0;
		String bestMovement;
		boolean converge = false;
		
		State[][] newState = new State[gridW.getNumOfRow()][gridW.getNumOfCol()];
		for (int k = 0; k < gridW.getNumOfRow(); k++) {
			for (int l = 0; l < gridW.getNumOfCol(); l++) {
				newState[l][k] = new State();
			}
		}
		
		int count = 0;
		while (count < 51) {
			for (int i = 0; i < gridW.getNumOfRow(); i++) {
				for (int j = 0; j < gridW.getNumOfCol(); j++) {
					if (!gridW.getGrids()[j][i].isWall()) {
						bestMovement = GridWorld.calculateBestMovementUtility(j, i, gridW);
						u = gridW.getGrids()[j][i].getReward()
								+ (0.99 * GridWorld.chooseBestMovement(j, i, gridW, bestMovement));
						newState[j][i].setUtility(u);
						newState[j][i].setBestMovement(bestMovement);
					}
				}
			}
			setGridStates(gridW,newState);
			count++;
		}
		gridW.displayStateMovement();
		System.out.println();
		gridW.displayState();
	}
	
	public static void setGridStates(GridWorld gw ,State[][] state) {
		for (int i = 0; i < gw.getNumOfRow(); i++) {
			for (int j = 0; j < gw.getNumOfCol(); j++) {
				gw.getGridsState()[j][i].setUtility(state[j][i].getUtility());
				gw.getGridsState()[j][i].setBestMovement(state[j][i].getBestMovement());
			}
		}
	}
}
