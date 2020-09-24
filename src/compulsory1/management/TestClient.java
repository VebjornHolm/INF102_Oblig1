package compulsory1.management;

import compulsory.system.Model;

/**
 * @author Olav Bakken og Martin Vatshelle
 *
 * Scripts which test your strategy against various input files, and prints out the score obtained for each test
 * If the script crashes with an InputMismatchException there is likely a mismatch between the files we generated
 * and your system locale, running Generate.java to refresh the tests should fix the problem
 * 
 */
public class TestClient {
	
	/**
	 * Returns a strategy to be tested
	 * @return IStragegy
	 */
	static IStrategy getStrategy() {
		//Enter the strategy you want to test here;
		//return new ClosestStrategy();
		return new RandomStrategy();
	}
	
	/**
	 *  Runs the simulation with the provided strategy for each of the
	 *  inputs input/01.in-...-06.in, and prints the score obtained,
	 *  lower is better
	 * @param args
	 */
	public static void main(String args[]) throws Exception{
		for (int i = 1; i <= 6; i++) {
			IStrategy strategy = getStrategy();
			Model model = new Model(String.format("input/%02d.in", i), strategy);
			strategy.registerRobots(model.listRobots());
			model.runSimulation();
			try { System.out.printf("Score input/%02d.in: %.0f\n", i, model.score()); }
			catch (IllegalStateException e) {System.out.printf("Score input /%02d.in: %s\n", i, e.getMessage());}
		}
	}
}
