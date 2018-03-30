package main;

import objects.GridWorld;

public class complexMaze {

	public static void main(String[] args) {
		GridWorld gridW = new GridWorld(9, 9);
		gridW.generateComplexGridWorld();
		gridW.displayGridWorld();
		System.out.println();
		
		valueiteration.valueIteration(gridW);
		System.out.println();
		gridW.displayStateMovement();
		System.out.println();
		gridW.displayState();
		System.out.println();
		
		policyiteration.policyIteration(gridW);
		System.out.println();
		gridW.displayStateMovement();
		System.out.println();
		gridW.displayState();
	}
}
