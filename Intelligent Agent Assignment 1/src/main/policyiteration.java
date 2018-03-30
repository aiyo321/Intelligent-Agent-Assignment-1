package main;

import objects.GridWorld;
import objects.State;

public class policyiteration {

	static final double DISCOUNT_FACTOR = 0.99;
	static final int K = 10;

	public static void main(String[] args) {
		GridWorld gridW = new GridWorld(6, 6);
		gridW.generateGridWorld();
		gridW.displayGridWorld();
		System.out.println();
		
		policyIteration(gridW);
		
		System.out.println();
		gridW.displayStateMovement();
		System.out.println();
		gridW.displayState();
	}
	
	public static void policyIteration(GridWorld gridW) {
		setInitialPolicy(gridW);

		State[][] newState = new State[gridW.getNumOfRow()][gridW.getNumOfCol()];
		for (int k = 0; k < gridW.getNumOfRow(); k++) {
			for (int l = 0; l < gridW.getNumOfCol(); l++) {
				newState[l][k] = new State();
			}
		}

		int count = 0;
		boolean changed;
		do {
			simplifiedBellmanUpdate(gridW, newState);
			changed = policyImprove(gridW, newState);
			count++;
		} while (changed);

		System.out.println("Iterate: " + count);
	}

	public static boolean policyImprove(GridWorld gridW, State[][] newState) {
		String bestMovement;
		boolean changed = false;
		for (int i = 0; i < gridW.getNumOfRow(); i++) {
			for (int j = 0; j < gridW.getNumOfCol(); j++) {
				if (!gridW.getGrids()[j][i].isWall()) {
					bestMovement = gridW.calculateBestMovementUtility(j, i);
					newState[j][i].setUtility(gridW.getGridsState()[j][i].getUtility());
					newState[j][i].setBestMovement(bestMovement);
					if (!bestMovement.equals(gridW.getGridsState()[j][i].getBestMovement())) {
						changed = true;
					}
				}
			}
		}
		setGridStates(gridW, newState);
		return changed;
	}

	public static void simplifiedBellmanUpdate(GridWorld gridW, State[][] newState) {
		double u = 0;
		for (int a = 0; a < K; a++) {
			for (int i = 0; i < gridW.getNumOfRow(); i++) {
				for (int j = 0; j < gridW.getNumOfCol(); j++) {
					if (!gridW.getGrids()[j][i].isWall()) {
						u = gridW.getGrids()[j][i].getReward() + DISCOUNT_FACTOR
								* (gridW.chooseBestMovement(j, i, gridW.getGridsState()[j][i].getBestMovement()));
						newState[j][i].setUtility(u);
						newState[j][i].setBestMovement(gridW.getGridsState()[j][i].getBestMovement());
					}
				}
			}
			setGridStates(gridW, newState);
		}
	}

	public static void setInitialPolicy(GridWorld gw) {
		for (int i = 0; i < gw.getNumOfRow(); i++) {
			for (int j = 0; j < gw.getNumOfCol(); j++) {
				gw.getGridsState()[j][i].setBestMovement("up   ");
			}
		}
	}

	public static void setGridStates(GridWorld gw, State[][] state) {
		for (int i = 0; i < gw.getNumOfRow(); i++) {
			for (int j = 0; j < gw.getNumOfCol(); j++) {
				gw.getGridsState()[j][i].setUtility(state[j][i].getUtility());
				gw.getGridsState()[j][i].setBestMovement(state[j][i].getBestMovement());
			}
		}
	}

}
