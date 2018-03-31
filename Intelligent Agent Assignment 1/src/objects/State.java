package objects;

/**
 * @author Too Jian Teng (U1621194C)
 * state class to determine each state in the maze
 */
public class State {

	double utility;
	String bestMovement;
	
	public double getUtility() {
		return utility;
	}

	public void setUtility(double utility) {
		this.utility = utility;
	}

	public String getBestMovement() {
		return bestMovement;
	}

	public void setBestMovement(String bestMovement) {
		this.bestMovement = bestMovement;
	}

}
