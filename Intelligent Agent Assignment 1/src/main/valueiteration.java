package main;

import java.util.ArrayList;
import java.util.Collections;

import objects.GridWorld;
import objects.State;


/**
 * @author Too Jian Teng (U1621194C)
 */
public class valueiteration {

	static final double DISCOUNT_FACTOR = 0.99;
	static final double EPISILON = 60;
	static final double CONVERGENCE_CRITERIA = EPISILON * (1.0 - DISCOUNT_FACTOR) / DISCOUNT_FACTOR;
	
	public static void main(String[] args) {
		GridWorld gridW = new GridWorld(6, 6);
		gridW.generateGridWorld();
		gridW.displayGridWorld();
		System.out.println();

		valueIteration(gridW);

		System.out.println();
		gridW.displayStateMovement();
		System.out.println();
		gridW.displayState();
		System.out.println();
		gridW.displayStateLineByLine();
	}
	
	public static void valueIteration(GridWorld gridW) {
		double u = 0;
		String bestMovement;
		boolean converge = false;
		double diff;
		ArrayList<Double> al = new ArrayList<Double>();

		State[][] newState = new State[gridW.getNumOfRow()][gridW.getNumOfCol()];
		for (int k = 0; k < gridW.getNumOfRow(); k++) {
			for (int l = 0; l < gridW.getNumOfCol(); l++) {
				newState[l][k] = new State();
			}
		}

		int count = 0;
		while (!converge) {
			for (int i = 0; i < gridW.getNumOfRow(); i++) {
				for (int j = 0; j < gridW.getNumOfCol(); j++) {
					if (!gridW.getGrids()[j][i].isWall()) {
						bestMovement = gridW.calculateBestMovementUtility(j, i);
						u = gridW.getGrids()[j][i].getReward()
								+ (DISCOUNT_FACTOR * gridW.chooseBestMovement(j, i, bestMovement));
						newState[j][i].setUtility(u);
						newState[j][i].setBestMovement(bestMovement);

						al.add(Math.abs(u - gridW.getGridsState()[j][i].getUtility()));
					}
				}
			}
			setGridStates(gridW, newState);
			count++;
			
			diff = Collections.max(al);
			al.clear();
			
			if (diff < CONVERGENCE_CRITERIA) {
				converge = true;
			}

		}
		System.out.println("Iterate: " + count);
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
